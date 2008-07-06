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

        private var menubarXML:XMLList =<>
            <menuitem label="CRUDs">
                <menuitem label="Advertisement Type"  item="advType" />
                <menuitem label="Domain" item="domain" />
                <menuitem label="Category" item="category" />
            </menuitem>
            <menuitem label="Management">
                <menuitem label="Check Monitor" item="monitor" />
            </menuitem>
            </>

		public function Main()
		{
		}

        public function init(event:FlexEvent):void {
            mainReference = event.target as main;
            menuBarCollection = new XMLListCollection(menubarXML);
        }

        // Event handler for the MenuBar control's itemClick event.
        public function itemClickHandler(event:MenuEvent):void {
            var menuLabel:String = event.label;
            if (menuLabel == "Category") {
                mainReference.currentState = "category";
            }
        }
	}
}