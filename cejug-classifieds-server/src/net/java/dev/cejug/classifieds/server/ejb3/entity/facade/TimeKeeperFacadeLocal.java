package net.java.dev.cejug.classifieds.server.ejb3.entity.facade;

import javax.ejb.Local;

import net.java.dev.cejug.classifieds.server.ejb3.entity.OperationTimestampEntity;

@Local
public interface TimeKeeperFacadeLocal {
	void record(OperationTimestampEntity entity) throws Exception;
}