package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import javax.ejb.Local;

import net.java.dev.cejug.classifieds.server.ejb3.entity.DomainEntity;

@Local
public interface DomainFacadeLocal extends DomainModelFacade<DomainEntity> {
}
