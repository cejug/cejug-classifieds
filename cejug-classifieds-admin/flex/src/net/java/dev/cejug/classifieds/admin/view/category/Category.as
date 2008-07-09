package net.java.dev.cejug.classifieds.admin.view.category
{
    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    import mx.events.CloseEvent;
    import mx.events.FlexEvent;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    import mx.rpc.remoting.mxml.RemoteObject;
    
    import net.java.dev.cejug.classifieds.admin.view.message.MessageUtils;
    import net.java.dev.cejug.classifieds.server.contract.AdvertisementCategory;
    import net.java.dev.cejug.classifieds.server.contract.CreateCategoryParam;
    import net.java.dev.cejug.classifieds.server.contract.DeleteCategoryParam;
    import net.java.dev.cejug.classifieds.server.contract.ReadCategoryBundleParam;
    import net.java.dev.cejug.classifieds.server.contract.ServiceStatus;
    import net.java.dev.cejug.classifieds.server.contract.UpdateCategoryParam;
    
	public class Category
	{
	    private var categoryReference:category;
        private var adminService:RemoteObject;

        [Bindable]
        public var categoryEntity:AdvertisementCategory;

        [Bindable]
        [ArrayElementType("net.java.dev.cejug.classifieds.server.contract.AdvertisementCategory")]
        public var categoryDataProvider:ArrayCollection = new ArrayCollection();

        private var serviceStatus:ServiceStatus;

		public function Category()
		{
			adminService = new RemoteObject("CejugClassifiedsAdminService");
			adminService.readAllCategories.addEventListener("result", getAllCategoriesResult);
			adminService.createCategory.addEventListener("result", saveCategoryResult);
			adminService.updateCategory.addEventListener("result", saveCategoryResult);
			adminService.deleteCategory.addEventListener("result", saveCategoryResult);
			adminService.addEventListener("fault", onRemoteFault);
		}

        public function init(event:FlexEvent):void {
            categoryReference = event.target as category;
            readAllCategory();
        }

        public function resetState():void {
            cleanFields();
            categoryReference.currentState = "";
        }

        private function cleanFields():void {
            categoryReference.fNewCategoryName.text = "";
            categoryReference.fNewCategoryDescription.text = "";
            categoryReference.fNewCategoryAvailable.selected = false;
        }

        /**
         * Handles errors when calling the remote operations.
         */
        private function onRemoteFault(event:FaultEvent):void {
            MessageUtils.showError(event.fault.faultString);
        }

        /**
         * Loads all the categories.
         */
        public function readAllCategory():void {
            var params:ReadCategoryBundleParam = new ReadCategoryBundleParam();
            adminService.readAllCategories(params);
        }
        
        /**
         * Handles the result of loading all the categories.
         */
        private function getAllCategoriesResult(event:ResultEvent):void {
            categoryDataProvider = event.result as ArrayCollection; 
        }

        /**
         * Create a new category.
         */
        public function createCategory():void {
            var advertisementCategory:AdvertisementCategory = new AdvertisementCategory();
            advertisementCategory.name = categoryReference.fNewCategoryName.text;
            advertisementCategory.description = categoryReference.fNewCategoryDescription.text;
            advertisementCategory.available = categoryReference.fNewCategoryAvailable.selected ? 1 : 0;
            
            var param:CreateCategoryParam = new CreateCategoryParam();
            param.advertisementCategory = advertisementCategory;
            adminService.createCategory(param);
        }

        /**
         * Updates a category.
         */
        public function updateCategory():void {
            var advertisementCategory:AdvertisementCategory = new AdvertisementCategory();
            advertisementCategory.id = categoryEntity.id;
            advertisementCategory.name = categoryReference.fUpdateCategoryName.text;
            advertisementCategory.description = categoryReference.fUpdateCategoryDescription.text;
            advertisementCategory.available = categoryReference.fUpdateCategoryAvailable.selected ? 1 : 0;
            
            var param:UpdateCategoryParam = new UpdateCategoryParam();
            param.advertisementCategory = advertisementCategory;
            adminService.updateCategory(param);
        }

        /**
         * Confirms to delete a category.
         */
        public function confirmDeleteCategory():void {
            var row:int = categoryReference.dgCategories.selectedIndex;

            if (row >= 0) {
                categoryEntity = categoryDataProvider.getItemAt(row) as AdvertisementCategory;
                MessageUtils.showQuestion("Delete selected category ["+ categoryEntity.name + "] ?", deleteCategory);
            }
        }
        /**
         * Delete a category.
         */
        public function deleteCategory(event:CloseEvent):void {
            if (event.detail == Alert.YES) {
                var row:int = categoryReference.dgCategories.selectedIndex;
    
                if (row >= 0) {
                    categoryEntity = categoryDataProvider.getItemAt(row) as AdvertisementCategory;
                    var param:DeleteCategoryParam = new DeleteCategoryParam();
                    param.primaryKey = categoryEntity.id;
                    adminService.deleteCategory(param);
                }
            }
        }

        /**
         * Handles the result of saving a category (Create, Update or Delete).
         */
        private function saveCategoryResult(event:ResultEvent):void {
            serviceStatus = event.result as ServiceStatus;
            handleServiceStatus(serviceStatus);
        }

        /**
         * Loads the screen to create a new category.
         */
        public function loadNewCategory():void {
            categoryReference.currentState = "createCategory";
            categoryEntity = new AdvertisementCategory();
            cleanFields();
        }

        /**
         * Loads the screen to update a category.
         */
        public function loadUpdateCategory():void {
            categoryReference.currentState = "updateCategory";
            var row:int = categoryReference.dgCategories.selectedIndex;

            if (row >= 0) {
                categoryEntity = categoryDataProvider.getItemAt(row) as AdvertisementCategory;
            }
        }

        private function handleServiceStatus(serviceStatus:ServiceStatus):void {
            switch(serviceStatus.statusCode) {
                case 200: MessageUtils.showInfo(serviceStatus.description);
                          categoryReference.currentState = "";
                          readAllCategory();
                          break;
                case 500: MessageUtils.showError(serviceStatus.description); break;
                default: MessageUtils.showInfo(serviceStatus.description); break;
            }
        }
	}
}