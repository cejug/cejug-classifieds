package net.java.dev.cejug.classifieds.server.ejb3.bean;

import java.util.List;

import javax.ejb.Stateless;

import net.java.dev.cejug.classifieds.server.dao.ClassifiedsServerDao;
import net.java.dev.cejug.classifieds.server.generated.contract.FeedType;

@Stateless
public class AtomDao implements ClassifiedsServerDao<FeedType> {

	@Override
	public FeedType create() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(FeedType entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<FeedType> get(String query, int limit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FeedType> getAll(int limit) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(FeedType entity) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
