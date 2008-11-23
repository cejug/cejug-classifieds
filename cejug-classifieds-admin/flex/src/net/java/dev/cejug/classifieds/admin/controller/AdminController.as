package net.java.dev.cejug.classifieds.admin.controller
{
	import net.java.dev.cejug.classifieds.admin.view.admin;
	import mx.events.FlexEvent;

	public class AdminController
	{
		public static var adminReference:admin;
		
        [Bindable]
        public static var loginInfoVisible:Boolean = false;

		public function AdminController()
		{
		}
        public function init(event:FlexEvent):void{
            adminReference = event.target as admin;
        }

        public function logout():void {
            adminReference.mainScreen.currentState = "";
        	adminReference.currentState = "login";
        	loginInfoVisible = false;
        }
	}
}