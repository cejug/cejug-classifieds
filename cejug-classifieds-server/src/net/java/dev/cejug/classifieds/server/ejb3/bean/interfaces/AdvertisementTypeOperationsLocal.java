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
package net.java.dev.cejug.classifieds.server.ejb3.bean.interfaces;

import javax.ejb.Local;

import net.java.dev.cejug_classifieds.metadata.admin.CreateAdvertisementTypeParam;
import net.java.dev.cejug_classifieds.metadata.admin.ReadAdvertisementTypeBundleParam;
import net.java.dev.cejug_classifieds.metadata.admin.UpdateAdvertisementTypeParam;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementTypeCollection;
import net.java.dev.cejug_classifieds.metadata.common.ServiceStatus;

/**
 * @author $Author: felipegaucho $
 * @version $Rev: 504 $ ($Date: 2008-08-24 11:22:52 +0200 (So, 24 Aug 2008) $)
 */
@Local
public interface AdvertisementTypeOperationsLocal {

  /**
   * TODO: to comment.
   */
  ServiceStatus createAdvertisementTypeOperation(final CreateAdvertisementTypeParam newAdvType);

  /**
   * TODO: to comment.
   */
  ServiceStatus updateAdvertisementTypeOperation(final UpdateAdvertisementTypeParam partialAdvType);

  /**
   * TODO: to comment.
   */
  ServiceStatus deleteAdvertisementTypeOperation(final int id);

  /**
   * TODO: to comment.
   */
  AdvertisementTypeCollection readAdvertisementTypeBundleOperation(final ReadAdvertisementTypeBundleParam getAdvertisementTypes);
}
