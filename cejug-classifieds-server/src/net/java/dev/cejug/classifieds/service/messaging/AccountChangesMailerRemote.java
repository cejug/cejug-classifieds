package net.java.dev.cejug.classifieds.service.messaging;

import java.rmi.RemoteException;

import javax.ejb.Remote;

@Remote
public interface AccountChangesMailerRemote {
	String sendAccountChangesSummary() throws RemoteException;
}
