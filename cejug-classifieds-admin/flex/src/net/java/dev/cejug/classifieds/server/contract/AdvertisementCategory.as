/**
 * Generated by Gas3 v1.1.0 (Granite Data Services) on Sun Jul 06 16:42:58 CEST 2008.
 *
 * NOTE: this file is only generated if it does not exist. You may safely put
 * your custom code here.
 */

package net.java.dev.cejug.classifieds.server.contract {

    [Bindable]
    [RemoteClass(alias="net.java.dev.cejug.classifieds.server.contract.AdvertisementCategory")]
    public class AdvertisementCategory {
        private var _available:int;
        private var _description:String;
        private var _entityId:Number;
        private var _name:String;
        private var _advertisementCategory:AdvertisementCategory;

        public function toString():String {
            return name;
        }

        public function set available(value:int):void {
            _available = value;
        }
        public function get available():int {
            return _available;
        }

        public function set description(value:String):void {
            _description = value;
        }
        public function get description():String {
            return _description;
        }

        public function set entityId(value:Number):void {
            _entityId = value;
        }
        public function get entityId():Number {
            return _entityId;
        }

        public function set name(value:String):void {
            _name = value;
        }
        public function get name():String {
            return _name;
        }
        public function set advertisementCategory(value:AdvertisementCategory):void {
            _advertisementCategory = value;
        }
        public function get advertisementCategory():AdvertisementCategory {
            return _advertisementCategory;
        }
    }
}