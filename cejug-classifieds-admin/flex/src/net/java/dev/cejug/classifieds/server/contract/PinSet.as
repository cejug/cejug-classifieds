/**
 * Generated by Gas3 v1.1.0 (Granite Data Services) on Sun Jul 06 16:42:57 CEST 2008.
 *
 * NOTE: this file is only generated if it does not exist. You may safely put
 * your custom code here.
 */

package net.java.dev.cejug.classifieds.server.contract {

    [Bindable]
    [RemoteClass(alias="net.java.dev.cejug.classifieds.server.contract.PinSet")]
    public class PinSet {

        private var _pin:String;
        private var _type:int;

        public function set pin(value:String):void {
            _pin = value;
        }
        public function get pin():String {
            return _pin;
        }

        public function set type(value:int):void {
            _type = value;
        }
        public function get type():int {
            return _type;
        }

    }
}