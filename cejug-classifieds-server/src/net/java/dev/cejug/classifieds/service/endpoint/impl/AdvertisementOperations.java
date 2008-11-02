/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
 Copyright (C) 2008 CEJUG - Ceará Java Users Group
 
 This library is free software; you can redistribute it and/or
 modify it under the terms of the GNU Lesser General Public
 License as published by the Free Software Foundation; either
 version 2.1 of the License, or (at your option) any later version.
 
 This library is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 Lesser General Public License for more details.
 
 You should have received a copy of the GNU Lesser General Public
 License along with this library; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 
 This file is part of the CEJUG-CLASSIFIEDS Project - an  open source classifieds system
 originally used by CEJUG - Ceará Java Users Group.
 The project is hosted https://cejug-classifieds.dev.java.net/
 
 You can contact us through the mail dev@cejug-classifieds.dev.java.net
 - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
package net.java.dev.cejug.classifieds.service.endpoint.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.business.interfaces.AdvertisementAdapterLocal;
import net.java.dev.cejug.classifieds.business.interfaces.AdvertisementOperationsLocal;
import net.java.dev.cejug.classifieds.entity.AdvertisementEntity;
import net.java.dev.cejug.classifieds.entity.facade.AdvertisementFacadeLocal;
import net.java.dev.cejug.classifieds.exception.RepositoryAccessException;
import net.java.dev.cejug_classifieds.metadata.attachments.AvatarImageOrUrl;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.business.AdvertisementCollection;
import net.java.dev.cejug_classifieds.metadata.business.AdvertisementCollectionFilter;
import net.java.dev.cejug_classifieds.metadata.business.PublishingHeader;
import net.java.dev.cejug_classifieds.metadata.common.BundleRequest;
import net.java.dev.cejug_classifieds.metadata.common.Customer;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

/**
 * TODO: to comment.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
@Stateless
public class AdvertisementOperations implements AdvertisementOperationsLocal {

	/**
	 * Persistence façade of Advertisement entities.
	 */
	@EJB
	private transient AdvertisementFacadeLocal advFacade;

	@EJB
	private transient AdvertisementAdapterLocal advAdapter;

	/**
	 * the global log manager, used to allow third party services to override
	 * the default logger.
	 */
	private static final Logger logger = Logger.getLogger(
			AdvertisementOperations.class.getName(), "i18n/log");

	public AdvertisementCollection loadAdvertisementOperation(
			final AdvertisementCollectionFilter filter) {
		// TODO: load advertisements from timetable.... filtering with periods,
		// etc..

		try {
			AdvertisementCollection collection = new AdvertisementCollection();
			List<AdvertisementEntity> entities = advFacade.readAll();
			for (AdvertisementEntity entity : entities) {
				collection.getAdvertisement().add(advAdapter.toSoap(entity));
			}
			return collection;
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}
	}

	public Advertisement publishOperation(final Advertisement advertisement,
			final PublishingHeader header) {

		// TODO: to implement the real code.
		try {
			// TODO: re-think a factory to reuse adapters...
			Customer customer = new Customer();
			customer.setLogin(header.getCustomerLogin());
			customer.setDomainId(header.getCustomerDomainId());
			advertisement.setCustomer(customer);

			// Copy resources to the content repository - the file system.
			try {
				copyResourcesToRepository(advertisement);
			} catch (Exception e) {
				e.printStackTrace();
			}

			AdvertisementEntity entity = advAdapter.toEntity(advertisement);
			advFacade.create(entity);
			return advAdapter.toSoap(entity);
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new WebServiceException(e);
		}

	}

	/**
	 * A customer can submit a resource URL or a resource content, more common
	 * in case of images (PNG/JPG). In this case, the server first store the
	 * image contents in the file system and refer its location in the database.
	 * This method receives an Avatar object, check it size and store it
	 * contents in the file system.
	 * 
	 * @param advertisement
	 *            the advertisement containing attachments.
	 * @return the resource address.
	 * @throws RepositoryAccessException
	 *             problems accessing the content repository.
	 */
	private String copyResourcesToRepository(Advertisement advertisement)
			throws RepositoryAccessException {
		AvatarImageOrUrl avatar = advertisement.getAvatarImageOrUrl();
		if (avatar.getUrl() == null && avatar.getImage() != null) {
			Customer customer = advertisement.getCustomer();

			String path = "/home/fgaucho/dev/glassfish/domains/domain1/applications/j2ee-apps/cejug-classifieds-server/cejug-classifieds-server_war/resource/"
					+ customer.getDomainId() + "/" + customer.getEntityId();
			String file = path + "/" + avatar.getName();
			File pathF = new File(path);
			File fileF = new File(file);

			try {
				if (pathF.mkdirs() && fileF.createNewFile()) {
					FileOutputStream out;
					out = new FileOutputStream(file);
					out.write(avatar.getImage().getValue());
					out.close();
					avatar
							.setUrl("http://fgaucho.dyndns.org:8080/cejug-classifieds-server/resource/"
									+ customer.getDomainId()
									+ "/"
									+ customer.getEntityId()
									+ "/"
									+ avatar.getName());
				} else {
					throw new RepositoryAccessException(
							"error trying tocreate the resource path '" + file
									+ "'");
				}
			} catch (IOException e) {
				logger.severe(e.getMessage());
				throw new RepositoryAccessException(e);
			}
		}

		/*
		 * TODO: copy the attachments to the content repository.
		 * List<AttachmentOrUrl> attachments =
		 * advertisement.getAttachmentOrUrl(); for (AttachmentOrUrl attachment :
		 * attachments) {
		 * 
		 * }
		 */
		return null;
	}

	@Override
	public Advertisement create(Advertisement type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceStatus delete(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Advertisement read(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Advertisement> readBundleOperation(BundleRequest bundleRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceStatus update(Advertisement type) {
		// TODO Auto-generated method stub
		return null;
	}
}
