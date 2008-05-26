package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import javax.ejb.Remote;

import net.java.dev.cejug.classifieds.server.generated.contract.AdvertisementBundle;

@Remote
public interface AdvertisementPublisherRemote extends
		DomainModelFacade<AdvertisementBundle> {

}
