package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import javax.ejb.Local;

import net.java.dev.cejug.classifieds.server.generated.contract.AdvertisementBundle;

@Local
public interface AdvertisementPublisherLocal extends
		DomainModelFacade<AdvertisementBundle> {

}
