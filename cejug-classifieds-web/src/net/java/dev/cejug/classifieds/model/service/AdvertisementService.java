package net.java.dev.cejug.classifieds.model.service;

import java.util.List;

import net.java.dev.cejug.classifieds.model.entity.Advertisement;

/**
 * Defines a contract for the Advertisements operations.
 * @author Tarso Bessa
 *
 */
public interface AdvertisementService {
	/**
	 * Returns a Advertisement object ready to be shown. Send the advertisement argument
	 * to be stored and returns it populated with additional informations, if necessary.
	 * 
	 * Note: Always use the returned object because there's no garantee between the
	 *  argument and the returned object be the same.
	 * 
	 * @param advertisement
	 * @return A advertisement ready to be shown
	 */
	public Advertisement publish(Advertisement advertisement);
	
	/**
	 * Returns a list containing all the advertisements.
	 * 
	 * Note: this method is temporary. 
	 * @return
	 */
	public List<Advertisement> getAll();
}
