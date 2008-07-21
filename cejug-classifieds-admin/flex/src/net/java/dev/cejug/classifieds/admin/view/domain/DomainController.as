package net.java.dev.cejug.classifieds.admin.view.domain
{
    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    import mx.events.CloseEvent;
    import mx.events.FlexEvent;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    import mx.rpc.remoting.mxml.RemoteObject;
    
    import net.java.dev.cejug.classifieds.admin.view.message.MessageUtils;
    import net.java.dev.cejug.classifieds.server.contract.CreateDomainParam;
    import net.java.dev.cejug.classifieds.server.contract.DeleteDomainParam;
    import net.java.dev.cejug.classifieds.server.contract.Domain;
    import net.java.dev.cejug.classifieds.server.contract.ReadCategoryBundleParam;
    import net.java.dev.cejug.classifieds.server.contract.ServiceStatus;
    import net.java.dev.cejug.classifieds.server.contract.UpdateDomainParam;
    
	public class DomainController
	{
	    private var domainReference:domain;
        private var adminService:RemoteObject;

        [Bindable]
        public var domainEntity:Domain;

        [Bindable]
        [ArrayElementType("net.java.dev.cejug.classifieds.server.contract.Domain")]
        public var domainDataProvider:ArrayCollection = new ArrayCollection();

        [Bindable]
        [ArrayElementType("net.java.dev.cejug.classifieds.server.contract.AdvertisementCategory")]
        public var categoryDataProvider:ArrayCollection = new ArrayCollection();
        
        private var serviceStatus:ServiceStatus;

		public function DomainController()
		{
			adminService = new RemoteObject("CejugClassifiedsAdminService");
			adminService.readDomainBundleOperation.addEventListener("result", getAllDomainsResult);
			adminService.createDomainOperation.addEventListener("result", saveDomainResult);
			adminService.updateDomainOperation.addEventListener("result", saveDomainResult);
			adminService.deleteDomainOperation.addEventListener("result", saveDomainResult);
			adminService.readCategoryBundleOperation.addEventListener("result", loadCategoriesResult);
			adminService.addEventListener("fault", onRemoteFault);
		}

        public function init(event:FlexEvent):void {
            domainReference = event.target as domain;
            readAllDomain();
        }

        public function resetState():void {
            cleanFields();
            domainReference.currentState = "";
        }

        private function cleanFields():void {
            domainReference.fNewDomainName.text = "";
            domainReference.fNewDomainBrand.text = "";
            domainReference.fNewDomainSharedQuota.selected = false;
            // TODO timezone and categories
        }

        /**
         * Handles errors when calling the remote operations.
         */
        private function onRemoteFault(event:FaultEvent):void {
            MessageUtils.showError(event.fault.faultString);
        }

        /**
         * Loads all the domains.
         */
        public function readAllDomain():void {
            adminService.readDomainBundleOperation();
        }
        
        /**
         * Handles the result of loading all the domains.
         */
        private function getAllDomainsResult(event:ResultEvent):void {
            domainDataProvider = event.result as ArrayCollection; 
        }

        /**
         * Create a new domain.
         */
        public function createDomain():void {
            var domain:Domain = new Domain();
            domain.domain = domainReference.fNewDomainName.text;
            domain.brand = domainReference.fNewDomainBrand.text;
            domain.sharedQuota = domainReference.fNewDomainSharedQuota.selected;
            //TODO timezone and categories

            var param:CreateDomainParam = new CreateDomainParam();
            param.domain = domain;
            adminService.createDomainOperation(param);
        }

        /**
         * Updates a domain.
         */
        public function updateDomain():void {
            var domain:Domain = new Domain();
            domain.id = domainEntity.id;
            domain.domain = domainReference.fUpdateDomainName.text;
            domain.brand = domainReference.fUpdateDomainBrand.text;
            domain.sharedQuota = domainReference.fUpdateDomainSharedQuota.selected;
            //TODO timezone and categories
            
            var param:UpdateDomainParam = new UpdateDomainParam();
            param.domain = domain;
            adminService.updateDomainOperation(param);
        }

        /**
         * Confirms to delete a domain.
         */
        public function confirmDeleteDomain():void {
            var row:int = domainReference.dgDomains.selectedIndex;

            if (row >= 0) {
                domainEntity = domainDataProvider.getItemAt(row) as Domain;
                MessageUtils.showQuestion("Delete selected domain ["+ domainEntity.domain + "] ?", deleteDomain);
            }
        }
        /**
         * Delete a domain.
         */
        public function deleteDomain(event:CloseEvent):void {
            if (event.detail == Alert.YES) {
                var row:int = domainReference.dgDomains.selectedIndex;
    
                if (row >= 0) {
                    domainEntity = domainDataProvider.getItemAt(row) as Domain;
                    var param:DeleteDomainParam = new DeleteDomainParam();
                    param.primaryKey = domainEntity.id;
                    adminService.deleteDomainOperation(param);
                }
            }
        }

        /**
         * Handles the result of saving a domain (Create, Update or Delete).
         */
        private function saveDomainResult(event:ResultEvent):void {
            serviceStatus = event.result as ServiceStatus;
            handleServiceStatus(serviceStatus);
        }

        /**
         * Loads the screen to create a new domain.
         */
        public function loadNewDomain():void {
            domainReference.currentState = "createDomain";
            //TODO load categories

            domainEntity = new Domain();
            cleanFields();
        }

        /**
         * Loads the screen to update a domain.
         */
        public function loadUpdateDomain():void {
            domainReference.currentState = "updateDomain";
            var row:int = domainReference.dgDomains.selectedIndex;

            if (row >= 0) {
                domainEntity = domainDataProvider.getItemAt(row) as Domain;
            }
        }

        private function loadCategories():void {
            var params:ReadCategoryBundleParam = new ReadCategoryBundleParam();
            adminService.readCategoryBundleOperation(params);
        }

        private function loadCategoriesResult(event:ResultEvent):void {
            categoryDataProvider = event.result as ArrayCollection;
        }

        private function handleServiceStatus(serviceStatus:ServiceStatus):void {
            switch(serviceStatus.statusCode) {
                case 200: //MessageUtils.showInfo(serviceStatus.description);
                          domainReference.currentState = "";
                          readAllDomain();
                          break;
                case 500: MessageUtils.showError(serviceStatus.description); break;
                default: MessageUtils.showInfo(serviceStatus.description); break;
            }
        }
	}
}