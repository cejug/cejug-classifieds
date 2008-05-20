package net.java.dev.cejug.classifieds.server.ejb3.bean;

import javax.ejb.Remote;

import net.java.dev.cejug.classifieds.server.dao.ClassifiedsServerDao;
import net.java.dev.cejug.classifieds.server.generated.contract.AdvertisementBundle;

@Remote
public interface AdvertisementPublisherRemote extends
		ClassifiedsServerDao<AdvertisementBundle> {

}