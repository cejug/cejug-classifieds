/**
 * Generated by Gas3 v1.1.0 (Granite Data Services) on Sun Jul 06 16:42:54 CEST 2008.
 *
 * NOTE: this file is only generated if it does not exist. You may safely put
 * your custom code here.
 */

package net.java.dev.cejug.classifieds.server.contract {
    import mx.collections.ListCollectionView;
    

    [Bindable]
    [RemoteClass(alias="net.java.dev.cejug.classifieds.server.contract.AdvertisementRefBundle")]
    public class AdvertisementRefBundle {

        private var _advertisementRef:ListCollectionView;

        public function get advertisementRef():ListCollectionView {
            return _advertisementRef;
        }
        public function set advertisementRef(value:ListCollectionView):void {
            _advertisementRef = value;
        }
    }
}