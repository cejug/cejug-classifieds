package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import net.java.dev.cejug.classifieds.server.ejb3.entity.AdvertisementEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.CustomerEntity;
import net.java.dev.cejug.classifieds.server.generated.contract.Channel;
import net.java.dev.cejug.classifieds.server.generated.contract.Item;

@Stateless
public class RssChannelFacade implements RssChannelFacadeLocal {

	@PersistenceContext(unitName = "classifieds")
	private EntityManager manager;

	@Override
	public Channel create() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Channel type) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Channel> get(String query, int limit) throws Exception {
		// TODO: DB access to retrieve channel data.

		Channel channel = new Channel();
		Item item = new Item();
		item.setAuthor(new CustomerEntity());
		item.setTitle("RSS Example");
		item.setDescription("This is an example of an Item");

		channel.getItem().add(item);
		channel.setTitle("kk");
		channel.setDescription("this is a channel description");
		channel.setLink("https://cejug-classifieds.dev.java.net/");
		channel.setLastBuildDate(new Date().toString());
		channel.setPubDate(new Date().toString());

		List<Channel> response = new ArrayList<Channel>();
		// response.add(channel);
		return response;
	}

	@Override
	public void update(Channel entity) throws Exception {
		manager.persist(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Channel get(Map<String, String> params) throws Exception {
		Query query = manager.createNamedQuery("selectAdvertisementByFilter");
		if (params != null) {
			for (String key : params.keySet()) {
				query.setParameter(key, params.get(key));
			}
		}
		List<AdvertisementEntity> result = query.getResultList();
		Channel channel = new Channel();
		for (AdvertisementEntity adv : result) {
			Item item = new Item();
			item.setAuthor(adv.getPublisher().getLogin());
			item.setTitle(adv.getTitle());
			item.setDescription(adv.getSummary());
			item.setPubDate(adv.getPublishingPeriod().iterator().next()
					.getDay());
			channel.getItem().add(item);
		}

		return channel;

	}

	@Override
	public Channel create(Channel entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Channel> get(int limit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Channel> get(Map<String, String> params, int limit)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
