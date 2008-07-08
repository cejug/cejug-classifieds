package net.java.dev.cejug.classifieds.admin.view
{
    import mx.collections.*;
    import mx.controls.Alert;
    import mx.events.FlexEvent;
    import mx.events.MenuEvent;


	public class Main
	{
		private var mainReference:main;

        [Bindable]
        public var menuBarCollection:XMLListCollection;

        /*
         * the attribute state contains the state name in the main screen
         */
        private var menubarXML:XMLList =<>
            <menuitem label="CRUDs">
                <menuitem label="Advertisement Type" state="advtype" />
                <menuitem label="Domain" state="domain" />
                <menuitem label="Category" state="category" />
            </menuitem>
            <menuitem label="Management">
                <menuitem label="Check Monitor" state="monitor" />
            </menuitem>
            </>;

        public function Main() {
            
        }

        public function init(event:FlexEvent):void {
            mainReference = event.target as main;
            menuBarCollection = new XMLListCollection(menubarXML);
        }

        // Event handler for the MenuBar control's itemClick event.
        public function itemClickHandler(event:MenuEvent):void {
            var state:String = menubarXML.menuitem.(@label == event.label).@state;
            mainReference.currentState = state;
        }
	}
}