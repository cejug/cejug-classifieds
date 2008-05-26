package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import javax.ejb.Remote;

import net.java.dev.cejug.classifieds.server.ejb3.entity.OperationTimestampEntity;

@Remote
public interface OperationTimeKeeperRemote extends
		DomainModelFacade<OperationTimestampEntity> {

}
