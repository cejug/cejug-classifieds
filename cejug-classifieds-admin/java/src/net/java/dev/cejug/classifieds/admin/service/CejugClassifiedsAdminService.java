/**
 * 
 */
package net.java.dev.cejug.classifieds.admin.service;

import java.util.List;
import net.java.dev.cejug.classifieds.server.contract.AdvertisementCategory;
import net.java.dev.cejug.classifieds.server.contract.AdvertisementType;
import net.java.dev.cejug.classifieds.server.contract.CejugClassifiedsAdmin;
import net.java.dev.cejug.classifieds.server.contract.CejugClassifiedsServiceAdmin;
import net.java.dev.cejug.classifieds.server.contract.CreateAdvertisementTypeParam;
import net.java.dev.cejug.classifieds.server.contract.CreateCategoryParam;
import net.java.dev.cejug.classifieds.server.contract.DeleteAdvertisementTypeParam;
import net.java.dev.cejug.classifieds.server.contract.DeleteCategoryParam;
import net.java.dev.cejug.classifieds.server.contract.ReadAdvertisementTypeBundleParam;
import net.java.dev.cejug.classifieds.server.contract.ReadCategoryBundleParam;
import net.java.dev.cejug.classifieds.server.contract.ServiceStatus;
import net.java.dev.cejug.classifieds.server.contract.UpdateAdvertisementTypeParam;
import net.java.dev.cejug.classifieds.server.contract.UpdateCategoryParam;

/**
 * @author rodrigo
 */
public class CejugClassifiedsAdminService {

    /*
     * =================================================================================
     * CATEGORY
     * ==================================================================================
     */
    /**
     * Get all advertisement categories.
     * @param param Params to search the categories
     * @return List of categories
     */
    public List<AdvertisementCategory> readAllCategories(ReadCategoryBundleParam param) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        return admin.readCategoryBundleOperation(param).getAdvertisementCategory();
    }

    /**
     * Creates a new category.
     * @param param Category to be created.
     * @return Status about the create operation.
     */
    public ServiceStatus createCategory(CreateCategoryParam param) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        return admin.createCategoryOperation(param);
    }

    /**
     * Updates a category.
     * @param param Category to be updated.
     * @return Status about the update operation.
     */
    public ServiceStatus updateCategory(UpdateCategoryParam param) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        return admin.updateCategoryOperation(param);
    }

    /**
     * delete one category.
     * @param param Information to delete a category
     * @return Status about the update operation.
     */
    public ServiceStatus deleteCategory(DeleteCategoryParam param) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        return admin.deleteCategoryOperation(param);
    }

    /*
     * =================================================================================
     * ADVERTISEMENT TYPE
     * ==================================================================================
     */
    /**
     * Get all advertisement types.
     * @param param Params to search the advertisement types
     * @return List of advertisement types
     */
    public List<AdvertisementType> readAllAdvTypes(ReadAdvertisementTypeBundleParam param) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        return admin.readAdvertisementTypeBundleOperation(param).getAdvertisementType();
    }

    /**
     * Creates a new advertisement type.
     * @param param Advertisement type to be created.
     * @return Status about the create operation.
     */
    public ServiceStatus createAdvType(CreateAdvertisementTypeParam param) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        return admin.createAdvertisementTypeOperation(param);
    }

    /**
     * Updates an advertisement type.
     * @param param Advertisement type to be updated.
     * @return Status about the update operation.
     */
    public ServiceStatus updateAdvType(UpdateAdvertisementTypeParam param) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        return admin.updateAdvertisementTypeOperation(param);
    }

    /**
     * delete one advertisement type.
     * @param param Information to delete an advertisement type
     * @return Status about the delete operation.
     */
    public ServiceStatus deleteAdvType(DeleteAdvertisementTypeParam param) {

        CejugClassifiedsAdmin admin = new CejugClassifiedsServiceAdmin().getCejugClassifiedsAdmin();
        return admin.deleteAdvertisementTypeOperation(param.getPrimaryKey());
    }
}
