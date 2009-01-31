package net.java.dev.cejug.classifieds.mom;

import java.rmi.RemoteException;

import javax.ejb.Remote;

@Remote
public interface AccountChangesMailerRemote {
	String sendAccountChangesSummary() throws RemoteException;
}
