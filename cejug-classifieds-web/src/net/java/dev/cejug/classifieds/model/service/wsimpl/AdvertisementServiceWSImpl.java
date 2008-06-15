package net.java.dev.cejug.classifieds.model.service.wsimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.java.dev.cejug.classifieds.model.service.AdvertisementService;
import net.java.dev.cejug.classifieds.server.generated.contract.Advertisement;

/**
 * This implementation is responsible for the communication with the
 * web-services stubs of the classifieds server.
 * 
 * @author Tarso Bessa
 * 
 */
public class AdvertisementServiceWSImpl implements AdvertisementService {
	/**
	 * This field is for test purpose only.
	 */
	private List<Advertisement> ads = new ArrayList<Advertisement>();

	@Override
	public Advertisement publish(Advertisement advertisement) {
		// TODO: ID should be included in the Advertisement element.
		// advertisement.setId((int) (Math.random() * 1000000));
		ads.add(advertisement);
		return advertisement;
	}

	@Override
	public List<Advertisement> getAll() {
		return Collections.unmodifiableList(ads);
	}

}
