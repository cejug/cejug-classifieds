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
package net.java.dev.cejug.classifieds.richfaces.view;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.faces.model.SelectItem;

import net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness;
import net.java.dev.cejug_classifieds.business.CejugClassifiedsServiceBusiness;
import net.java.dev.cejug_classifieds.metadata.attachments.AtavarImage;
import net.java.dev.cejug_classifieds.metadata.attachments.AvatarImageOrUrl;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.business.Period;
import net.java.dev.cejug_classifieds.metadata.business.PublishingHeader;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementCategory;
import net.java.dev.cejug_classifieds.metadata.common.AdvertisementType;
import net.java.dev.cejug_classifieds.metadata.common.BundleRequest;
import net.java.dev.cejug_classifieds.metadata.common.Customer;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * TODO: to comment.
 * 
 * @author $Author$
 * @version $Rev$ ($Date$)
 */
@Controller(value = "publishAdvertisementBean")
@Scope("session")
public class PublishAdvertisementBean {
	private transient final CejugClassifiedsBusiness SERVICE;

	private Advertisement advertisement = new Advertisement();

	//TODO Enum or constant?
	private String avatarImageOrUrl = "I";

	public PublishAdvertisementBean() {
		SERVICE = new CejugClassifiedsServiceBusiness()
				.getCejugClassifiedsBusiness();
		getAdvertisement().setAvatarImageOrUrl(new AvatarImageOrUrl());
	}

	public Advertisement getAdvertisement() {
		return advertisement;
	}

	public void setAdvertisement(Advertisement advertisement) {
		this.advertisement = advertisement;
	}

	public String getAvatarImageOrUrl() {
		return avatarImageOrUrl;
	}

	public void setAvatarImageOrUrl(String avatarImageOrUrl) {
		this.avatarImageOrUrl = avatarImageOrUrl;
	}

	private SelectItem advType = null;

	public SelectItem getAdvType() {
		return advType;
	}

	public void setAdvType(SelectItem advType) {
		this.advType = advType;
	}

	/**
	 * The list of the types of advertisements (simple, html, with image, etc..)
	 * 
	 * @return a list of select items containing the advertisement types.
	 */
	public List<SelectItem> getAdvertisementTypes() {
		// The highlander combo raised from hell again :)
		// TODO: to use a cache instead of loading every time...
		List<AdvertisementType> availableTypes = SERVICE
				.readAdvertisementTypeBundleOperation(new BundleRequest())
				.getAdvertisementType();
		List<SelectItem> list = new ArrayList<SelectItem>(availableTypes.size());
		for (AdvertisementType type : availableTypes) {
			list.add(new SelectItem(type, type.getName()));
		}
		if (!list.isEmpty()) {
			advType = list.get(0);
		}
		return list;
	}

	public List<SelectItem> getAvatarTypes() {
		List<SelectItem> list = new ArrayList<SelectItem>();
		list.add(new SelectItem("I", "Image"));
		list.add(new SelectItem("U", "Url"));
		return list;
	}

	public List<SelectItem> getLocales() {
		List<SelectItem> locales = new ArrayList<SelectItem>();

		Locale list[] = SimpleDateFormat.getAvailableLocales();

		for (int i = 0; i < list.length; i++) {
			locales.add(new SelectItem(list[i].toString(), list[i]
					.getDisplayName(Locale.US)));
		}

		return locales;
	}

	public void publish() {
		try {
			long domainId = 2L;

			// TODO: remove this list from here as soon we learn how to
			// show combo boxes with custom objects in JSF :)
			List<AdvertisementType> availableTypes = SERVICE
					.readAdvertisementTypeBundleOperation(new BundleRequest())
					.getAdvertisementType();

			// TODO: remove this list from here-.. the category should come from
			// the
			// page where the user is publishing.. (that combo on the from
			// page...)
			List<AdvertisementCategory> registeredCategories = SERVICE
					.readCategoryBundleOperation(new BundleRequest())
					.getAdvCategory();
			advertisement.setCategoryId(registeredCategories.get(0)
					.getEntityId());

			AdvertisementType type = availableTypes.get(0);

			Customer customer = new Customer();
			customer.setLogin("arisson");
			customer.setDomainId(domainId);
			advertisement.setCustomer(customer);
			advertisement.setTypeId(type.getEntityId());

			// TODO: include a text field in the publish page (auto-detect??)
			AvatarImageOrUrl avatar = advertisement.getAvatarImageOrUrl();
			if (avatar != null && avatar.getImage() != null) {
				avatar.getImage().setContentType("image/png");
			}

			// AvatarImageOrUrl avatar = new AvatarImageOrUrl();
			// URL imgUrl =
			// getClass().getClassLoader().getResource("img/car.png");
			// Image image = ImageIO.read(imgUrl);
			// ObjectFactory attachmentFactory = new ObjectFactory();

			/*
			 * AtavarImage avimage = attachmentFactory.createAtavarImage();
			 * avimage.setContentType("image/png"); avatar.setImage(avimage);
			 * avatar.setName("car.jpg"); avatar.setDescription("PUBLISH BEAN");
			 * avatar.setUrl(
			 * "http://upload.wikimedia.org/wikipedia/commons/e/e7/Avatar_ItsMine.png"
			 * ); advertisement.setAvatarImageOrUrl(avatar);
			 */

			// advertisement.setText("from PublishAdvertisementBean .. just a test..........");
			// Publishing period
			Calendar today = GregorianCalendar.getInstance();
			Period period = new Period();
			period.setStart(today);
			Calendar fiveDaysLater = GregorianCalendar.getInstance();
			fiveDaysLater.roll(Calendar.DAY_OF_YEAR, 5);
			period.setFinish(fiveDaysLater);
			// Advertisement contents
			advertisement.setPublishingPeriod(period);

			// keywords
			advertisement.setKeywords("Java,book,jsf");
			advertisement.setStatus(1);

			PublishingHeader header = new PublishingHeader();
			header.setCustomerDomainId(domainId);
			header.setCustomerLogin("arisson");

			SERVICE.publishOperation(advertisement, header);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* File Upload */
	private List<AtavarImage> files = new ArrayList<AtavarImage>();
	private int uploadsAvailable = 1;
	private boolean autoUpload = true;
	private boolean useFlash = false;

	public int getSize() {
		return getFiles().size();
	}

	public void paint(OutputStream stream, Object object) throws IOException {
		synchronized (object) {
			stream.write(getFiles().get((Integer) object).getValue());
		}

	}

	public void listener(UploadEvent event) {
		synchronized (event) {
			UploadItem item = event.getUploadItem();
			try {
				getAdvertisement().setAvatarImageOrUrl(new AvatarImageOrUrl());
				getAdvertisement().getAvatarImageOrUrl().setImage(
						new AtavarImage());
				getAdvertisement().getAvatarImageOrUrl().getImage().setValue(
						item.getData());

				files.add(getAdvertisement().getAvatarImageOrUrl().getImage());
				setUploadsAvailable(getUploadsAvailable() - 1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String clearUploadData() {
		files.clear();
		setUploadsAvailable(5);
		return null;
	}

	public long getTimeStamp() {
		return System.currentTimeMillis();
	}

	public List<AtavarImage> getFiles() {
		return files;
	}

	public void setFiles(List<AtavarImage> files) {
		this.files = files;
	}

	public int getUploadsAvailable() {
		return uploadsAvailable;
	}

	public void setUploadsAvailable(int uploadsAvailable) {
		this.uploadsAvailable = uploadsAvailable;
	}

	public boolean isAutoUpload() {
		return autoUpload;
	}

	public void setAutoUpload(boolean autoUpload) {
		this.autoUpload = autoUpload;
	}

	public boolean isUseFlash() {
		return useFlash;
	}

	public void setUseFlash(boolean useFlash) {
		this.useFlash = useFlash;
	}

}
