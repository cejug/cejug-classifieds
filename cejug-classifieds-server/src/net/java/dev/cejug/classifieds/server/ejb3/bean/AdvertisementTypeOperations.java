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
package net.java.dev.cejug.classifieds.server.ejb3.bean;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceException;

import net.java.dev.cejug.classifieds.server.ejb3.bean.interfaces.AdvertisementTypeOperationsLocal;
import net.java.dev.cejug.classifieds.server.ejb3.entity.AdvertisementTypeEntity;
import net.java.dev.cejug.classifieds.server.ejb3.entity.facade.AdvertisementTypeFacadeLocal;
import net.java.dev.cejug_classifieds.metadata.admin.CreateAdvertisementTypeParam;
import net.java.dev.cejug_classifieds.metadata.admin.ReadAdvertisementTypeBundleParam;
import net.java.dev.cejug_classifieds.metadata.admin.UpdateAdvertisementTypeParam;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementType;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementTypeCollection;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

/**
 * TODO: to comment.
 * 
 * @author $Author: felipegaucho $
 * @version $Rev: 504 $ ($Date: 2008-08-24 11:22:52 +0200 (So, 24 Aug 2008) $)
 */
@Stateless
public class AdvertisementTypeOperations implements AdvertisementTypeOperationsLocal {

  @EJB
  private transient AdvertisementTypeFacadeLocal advTypeFacade;
  /**
   * the global log manager, used to allow third party services to override the
   * default logger.
   */
  private static final Logger logger = Logger.getLogger(
      AdvertisementTypeOperations.class.getName(), "i18n/log");

  @Override
  public ServiceStatus createAdvertisementTypeOperation(final CreateAdvertisementTypeParam newAdvType) {

    ServiceStatus status = new ServiceStatus();
    try {
      AdvertisementTypeEntity advTypeEntity = new AdvertisementTypeEntity();
      AdvertisementType advertisementType = newAdvType.getAdvertisementType();
      advTypeEntity.setDescription(advertisementType.getDescription());
      advTypeEntity.setMaxAttachmentSize(advertisementType.getMaxAttachmentSize());
      advTypeEntity.setName(advertisementType.getName());
      advTypeEntity.setTextLength(advertisementType.getMaxTextLength());

      advTypeFacade.create(advTypeEntity);

      status.setStatusCode(200);
      status.setDescription("1 advertisement type added");
    } catch (Exception e) {
      logger.severe(e.getMessage());
      status.setStatusCode(500);
      status.setDescription(e.getMessage());
    }
    return status;
  }

  @Override
  public AdvertisementTypeCollection readAdvertisementTypeBundleOperation(final ReadAdvertisementTypeBundleParam getAdvertisementTypes) {

    // TODO: use the bundle request parameters as query filter.

    AdvertisementTypeCollection advTypeCollection = new AdvertisementTypeCollection();
    try {
      List<AdvertisementTypeEntity> advTypes = advTypeFacade.readAll(AdvertisementTypeEntity.class);
      if (advTypes != null) {
        for (AdvertisementTypeEntity advTypeEntity : advTypes) {
          AdvertisementType advType = new AdvertisementType();
          advType.setId(advTypeEntity.getId());
          advType.setDescription(advTypeEntity.getDescription());
          advType.setName(advTypeEntity.getName());
          advType.setMaxAttachmentSize(advTypeEntity.getMaxAttachmentSize());
          advType.setMaxTextLength(advTypeEntity.getTextLength());
          advTypeCollection.getAdvertisementType().add(advType);
        }
      }
    } catch (Exception e) {
      logger.severe(e.getMessage());
      throw new WebServiceException(e);
    }
    return advTypeCollection;
  }

  @Override
  public ServiceStatus updateAdvertisementTypeOperation(final UpdateAdvertisementTypeParam partialAdvType) {

    ServiceStatus status = new ServiceStatus();
    try {
      AdvertisementTypeEntity advTypeEntity = new AdvertisementTypeEntity();
      AdvertisementType advertisementType = partialAdvType.getAdvertisementType();
      advTypeEntity.setId(advertisementType.getId());
      advTypeEntity.setDescription(advertisementType.getDescription());
      advTypeEntity.setName(advertisementType.getName());
      advTypeEntity.setMaxAttachmentSize(advertisementType.getMaxAttachmentSize());
      advTypeEntity.setTextLength(advertisementType.getMaxTextLength());

      advTypeFacade.update(advTypeEntity);

      status.setStatusCode(200);
      status.setDescription("1 advertisement type updated");

    } catch (Exception e) {
      logger.severe(e.getMessage());
      status.setStatusCode(500);
      status.setDescription(e.getMessage());
    }
    return status;
  }

  @Override
  public ServiceStatus deleteAdvertisementTypeOperation(final int id) {

    ServiceStatus status = new ServiceStatus();
    try {
      // TODO Check if the advertisement type is being used, before
      // deleting it
      advTypeFacade.delete(AdvertisementTypeEntity.class, Integer.valueOf(id));
      status.setStatusCode(200);
      status.setDescription("1 advertisement type deleted");
    } catch (Exception e) {
      logger.severe(e.getMessage());
      status.setStatusCode(500);
      status.setDescription(e.getMessage());
    }
    return status;
  }
}
