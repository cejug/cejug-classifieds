package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import java.util.List;

import javax.ejb.Stateless;

import net.java.dev.cejug.classifieds.server.generated.contract.FeedType;

@Stateless
public class AtomDao implements DomainModelFacade<FeedType> {

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
