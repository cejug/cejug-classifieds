package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import javax.ejb.Local;

import net.java.dev.cejug.classifieds.server.ejb3.entity.CustomerEntity;

@Local
public interface CustomerFacadeLocal {

	void delete(CustomerEntity entity) throws Exception;

	CustomerEntity findOrCreate(String domain, String login) throws Exception;

	void update(CustomerEntity entity) throws Exception;

}