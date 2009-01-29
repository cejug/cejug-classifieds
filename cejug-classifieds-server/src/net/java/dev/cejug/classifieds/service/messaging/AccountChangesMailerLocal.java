package net.java.dev.cejug.classifieds.service.messaging;

import java.rmi.RemoteException;

import javax.ejb.Local;

@Local
public interface AccountChangesMailerLocal {
	String sendAccountChangesSummary() throws RemoteException;

}