package net.java.dev.cejug.classifieds.server.ejb3.bean;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.java.dev.cejug.classifieds.server.ejb3.entity.AdvertisementEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.CustomerEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.PublishingPeriodEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.PublishingPeriodEntity.PeriodState;
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
		CustomerEntity publisher = manager.find(CustomerEntity.class, source
				.getAuthorId());

		publisher = new CustomerEntity();
		publisher.setLogin("teste");
		manager.persist(publisher);

		for (Advertisement adv : source.getAdvertisements()) {
			AdvertisementEntity entity = new AdvertisementEntity();
			entity.setKeywords(adv.getKeywords());
			entity.setText(adv.getFullText());
			entity.setPublisher(publisher);
			entity.setSummary(adv.getShortDescription());
			entity.setTitle(adv.getHeadline());

			PublishingPeriodEntity period = new PublishingPeriodEntity();
			period.setDay(new Date(adv.getPublishingStart().getMillisecond()));
			period.setState(PeriodState.NEW);
			entity.getPublishingPeriod().add(period);
			manager.persist(entity);
		}
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