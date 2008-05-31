package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import javax.ejb.Local;

import net.java.dev.cejug.classifieds.server.ejb3.entity.AdvertisementEntity;

@Local
public interface AdvertisementFacadeLocal extends
		DomainModelFacade<AdvertisementEntity> {

}
