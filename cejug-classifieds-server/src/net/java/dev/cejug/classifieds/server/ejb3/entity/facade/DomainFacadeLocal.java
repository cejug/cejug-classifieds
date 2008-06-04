package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import java.util.List;

import javax.ejb.Local;

import net.java.dev.cejug.classifieds.server.ejb3.entity.DomainEntity;

@Local
public interface DomainFacadeLocal {
	void delete(DomainEntity entity) throws Exception;

	void update(DomainEntity entity) throws Exception;

	DomainEntity create() throws Exception;

	List<DomainEntity> get(String query, int limit) throws Exception;

	DomainEntity get(String domain) throws Exception;

	public DomainEntity create(DomainEntity entity) throws Exception;

}