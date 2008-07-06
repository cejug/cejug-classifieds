package net.java.dev.cejug.classifieds.admin.view.category
{
    import net.java.dev.cejug.classifieds.server.contract.AdvertisementCategory;
    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    import mx.events.FlexEvent;
    import mx.rpc.events.FaultEvent;
    import mx.rpc.events.ResultEvent;
    import mx.rpc.remoting.mxml.RemoteObject;
    
    import net.java.dev.cejug.classifieds.server.contract.ReadCategoryBundleParam;
    
	public class Category
	{
	    private var categoryReference:category;
        private var adminService:RemoteObject;

        [Bindable]
        public var categoryEntity:AdvertisementCategory;

        [Bindable]
        [ArrayElementType("net.java.dev.cejug.classifieds.server.contract.AdvertisementCategory")]
        public var categoryDataProvider:ArrayCollection = new ArrayCollection();

		public function Category()
		{
			adminService = new RemoteObject("CejugClassifiedsAdminService");
			adminService.getAllCategories.addEventListener("result", getAllCategoriesResult);
			adminService.addEventListener("fault", onRemoteFault);
		}

        public function init(event:FlexEvent):void {
            categoryReference = event.target as category;
            loadAllCategory();
        }

        private function onRemoteFault(event:FaultEvent):void {
            Alert.show(event.fault.faultString);
        }

        public function loadAllCategory():void {
            var params:ReadCategoryBundleParam = new ReadCategoryBundleParam();
            adminService.getAllCategories(params);
        }
        private function getAllCategoriesResult(event:ResultEvent):void {
            categoryDataProvider = event.result as ArrayCollection; 
        }

        public function loadNewCategory():void {
            categoryReference.currentState = "createCategory";
        }
        public function loadUpdateCategory():void {
            categoryReference.currentState = "updateCategory";
        }
	}
}