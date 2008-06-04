package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import java.util.List;

import javax.ejb.Local;

import net.java.dev.cejug.classifieds.server.ejb3.entity.AdvertisementEntity;

@Local
public interface AdvertisementFacadeLocal {
	void update(AdvertisementEntity entity) throws Exception;

	AdvertisementEntity create(AdvertisementEntity entity) throws Exception;

	void delete(AdvertisementEntity entity) throws Exception;

	AdvertisementEntity create() throws Exception;

	List<AdvertisementEntity> getLatest(int limit) throws Exception;
}