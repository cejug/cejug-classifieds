package net.java.dev.cejug.classifieds.server.ejb3.bean;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CustomerDomainSessionBean implements CustomerDomainRemote {
	// this injects the default entity manager factory

	@SuppressWarnings("unused")
	@PersistenceContext(unitName = "classifieds")
	private EntityManager manager;

}