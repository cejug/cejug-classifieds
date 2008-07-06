package net.java.dev.cejug.classifieds.admin.view.login
{
	import net.java.dev.cejug.classifieds.admin.view.Admin;
	import mx.events.FlexEvent;
	import flash.events.Event;
	import flash.events.EventDispatcher;

	public class Login extends EventDispatcher
	{
		private var loginReference:login;

		public function doLogin():void {
			Admin.adminReference.currentState = "main";
			Admin.loginInfoVisible = true;
		}

	    public function init(event:FlexEvent):void{
            loginReference = event.target as login;
        }
	}
}