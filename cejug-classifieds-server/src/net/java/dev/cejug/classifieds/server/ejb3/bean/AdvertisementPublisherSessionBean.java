package net.java.dev.cejug.classifieds.server.ejb3.bean;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.java.dev.cejug.classifieds.server.ejb3.entity.AdvertisementEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.PublisherEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.PublishingPeriodEntity;
import net.java.dev.cejug.classifieds.server.generated.contract.Advertisement;
import net.java.dev.cejug.classifieds.server.generated.contract.AdvertisementBundle;

@Stateless
public class AdvertisementPublisherSessionBean implements
		AdvertisementPublisherRemote {
	// this injects the default entity manager factory

	@PersistenceContext(unitName = "classifieds")
	private EntityManager manager;

	@Override
	public void update(AdvertisementBundle source) throws Exception {
		PublisherEntity publisher = manager.find(PublisherEntity.class, source
				.getAuthorId());

		for (Advertisement adv : source.getAdvertisements()) {
			AdvertisementEntity entity = new AdvertisementEntity();
			entity.setKeywords(adv.getKeywords());
			entity.setText(adv.getFullText());
			entity.setPublisher(publisher);
			PublishingPeriodEntity period = new PublishingPeriodEntity();
			period.setFinish(new Date(adv.getPublishingFinish()
					.getMillisecond()));
			period
					.setStart(new Date(adv.getPublishingStart()
							.getMillisecond()));
			entity.setPublishingPeriod(period);
			manager.persist(entity);
		}

		System.out.println("COMITOU !");

	}

	@Override
	public AdvertisementBundle create() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(AdvertisementBundle entity) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<AdvertisementBundle> get(String query, int limit)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AdvertisementBundle> getAll(int limit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}