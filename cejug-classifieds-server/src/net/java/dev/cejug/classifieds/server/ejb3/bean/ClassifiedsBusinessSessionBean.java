package net.java.dev.cejug.classifieds.server.ejb3.bean;

import javax.ejb.Stateless;

import net.java.dev.cejug.classifieds.server.generated.contract.AdvertisementBundle;
import net.java.dev.cejug.classifieds.server.generated.contract.AtomCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.AtomFilterCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.RssCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.RssFilterCollection;
import net.java.dev.cejug.classifieds.server.generated.contract.ServiceStatus;
import net.java.dev.cejug.classifieds.server.generated.contract.SpamReport;

@Stateless
public class ClassifiedsBusinessSessionBean implements
		ClassifiedsBusinessRemote {
	// this injects the default entity manager factory

	/*
	 * @Override public Domain create() throws Exception { DomainEntity
	 * newDomain = new DomainEntity(); newDomain.setName(Math.random() * 1000 +
	 * "." + System.currentTimeMillis()); manager.persist(newDomain); Domain
	 * domain = new Domain(); domain.setName(newDomain.getName()); return
	 * domain; }
	 */

	@Override
	public AtomCollection loadAtomOperation(AtomFilterCollection filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RssCollection loadRssOperation(RssFilterCollection filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceStatus publishOperation(AdvertisementBundle advertisements) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceStatus reportSpamOperation(SpamReport spam) {
		// TODO Auto-generated method stub
		return null;
	}

}