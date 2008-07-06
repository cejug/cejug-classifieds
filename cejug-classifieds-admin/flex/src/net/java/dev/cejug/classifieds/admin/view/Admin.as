package net.java.dev.cejug.classifieds.admin.view
{
	import net.java.dev.cejug.classifieds.admin.view.admin;
	import mx.events.FlexEvent;

	public class Admin
	{
		public static var adminReference:admin;
		
        [Bindable]
        public static var loginInfoVisible:Boolean = false;

		public function Admin()
		{
		}
        public function init(event:FlexEvent):void{
            adminReference = event.target as admin;
        }

        public function logout():void {
        	adminReference.currentState = "login";
        	loginInfoVisible = false;
        }
	}
}