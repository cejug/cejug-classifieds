/**
 * Generated by Gas3 v1.1.0 (Granite Data Services) on Sun Jul 06 16:43:00 CEST 2008.
 *
 * NOTE: this file is only generated if it does not exist. You may safely put
 * your custom code here.
 */

package net.java.dev.cejug.classifieds.server.contract {

    [Bindable]
    [RemoteClass(alias="net.java.dev.cejug.classifieds.server.contract.MonitorQuery")]
    public class MonitorQuery {

        private var _averageResponseLength:int;
        private var _query:String;

        public function set averageResponseLength(value:int):void {
            _averageResponseLength = value;
        }
        public function get averageResponseLength():int {
            return _averageResponseLength;
        }

        public function set query(value:String):void {
            _query = value;
        }
        public function get query():String {
            return _query;
        }

    }
}