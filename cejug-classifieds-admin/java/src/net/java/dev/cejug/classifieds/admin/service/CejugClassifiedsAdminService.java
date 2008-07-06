/**
 * 
 */
package net.java.dev.cejug.classifieds.admin.service;

import java.util.List;
import net.java.dev.cejug.classifieds.server.contract.AdvertisementCategory;
import net.java.dev.cejug.classifieds.server.contract.CejugClassifiedsAdmin;
import net.java.dev.cejug.classifieds.server.contract.CejugClassifiedsServiceAdmin;
import net.java.dev.cejug.classifieds.server.contract.ReadCategoryBundleParam;

/**
 * @author rodrigo
 * 
 */
public class CejugClassifiedsAdminService {

    public List<AdvertisementCategory> getAllCategories(ReadCategoryBundleParam params) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        return admin.readCategoryBundleOperation(params).getAdvertisementCategory();
    }
}
