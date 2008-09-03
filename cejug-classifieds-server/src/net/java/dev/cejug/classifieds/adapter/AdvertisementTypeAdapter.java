package net.java.dev.cejug.classifieds.adapter;

import net.java.dev.cejug.classifieds.entity.AdvertisementTypeEntity;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementType;

public class AdvertisementTypeAdapter extends SoapOrmAdapter<AdvertisementType, AdvertisementTypeEntity> {

    @Override
    public AdvertisementTypeEntity toEntity(AdvertisementType type) throws IllegalStateException, IllegalArgumentException {

        AdvertisementTypeEntity advTypeEntity = new AdvertisementTypeEntity();

        advTypeEntity.setDescription(type.getDescription());
        advTypeEntity.setMaxAttachmentSize(type.getMaxAttachmentSize());
        advTypeEntity.setName(type.getName());
        advTypeEntity.setTextLength(type.getMaxTextLength());
        return advTypeEntity;
    }

    @Override
    public AdvertisementType toSoap(AdvertisementTypeEntity advTypeEntity) throws IllegalStateException, IllegalArgumentException {

        AdvertisementType advType = new AdvertisementType();
        advType.setId((int) advTypeEntity.getId());
        advType.setDescription(advTypeEntity.getDescription());
        advType.setName(advTypeEntity.getName());
        advType.setMaxAttachmentSize(advTypeEntity.getMaxAttachmentSize());
        advType.setMaxTextLength(advTypeEntity.getTextLength());
        return advType;
    }
}
