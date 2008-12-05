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
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import net.java.dev.cejug_classifieds.business.CejugClassifiedsBusiness;
import net.java.dev.cejug_classifieds.business.CejugClassifiedsServiceBusiness;
import net.java.dev.cejug_classifieds.metadata.attachments.AtavarImage;
import net.java.dev.cejug_classifieds.metadata.attachments.AvatarImageOrUrl;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.business.Period;
import net.java.dev.cejug_classifieds.metadata.business.PublishingHeader;
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

	private String avatarImageOrUrl = AvatarType.IMAGE.getType();
	private String selectedTab;
	
	private Date start;
	private Date finish;
	
	protected Date getStart() {
		return start;
	}

	protected void setStart(Date start) {
		this.start = start;
	}

	protected Date getFinish() {
		return finish;
	}

	protected void setFinish(Date finish) {
		this.finish = finish;
	}
	
	public Date getStartDate(){
		return this.getStart();
	}

	public void setStartDate(Date date){
		this.setStart(date);
	}
	
	public Date getFinishDate(){
		return this.getFinish();
	}

	public void setFinishDate(Date date){
		this.setFinish(date);
	}
	
	public String getSelectedTab() {
		return selectedTab;
	}

	public void setSelectedTab(String selectedTab) {
		this.selectedTab = selectedTab;
	}
	
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

	private AdvertisementCategoryWrapper selectedCategory;

	public AdvertisementCategoryWrapper getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(
			AdvertisementCategoryWrapper selectedCategory) {
		this.selectedCategory = selectedCategory;
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

		for (AvatarType item : AvatarType.values()) {
			list.add(new SelectItem(item.getType(), item.toString()));
		}

		return list;
	}

	public void publish() {
		
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
			// List<AdvertisementCategory> registeredCategories = SERVICE
			// .readCategoryBundleOperation(new BundleRequest())
			// .getAdvCategory();
			advertisement.setCategoryId(getSelectedCategory().getId());

			AdvertisementType type = availableTypes.get(0);

			Customer customer = new Customer();
			customer.setLogin("arisson");
			customer.setDomainId(domainId);
			advertisement.setCustomer(customer);
			advertisement.setTypeId(type.getEntityId());

			// TODO: include a text field in the publish page (auto-detect??)
			// AvatarImageOrUrl avatar = advertisement.getAvatarImageOrUrl();
			// if (avatar != null && avatar.getImage() != null) {
			// avatar.getImage().setContentType("image/png");
			// }

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

			// Publishing period
			Calendar startDate = GregorianCalendar.getInstance();
			startDate.setTime(getStart());
			
			Calendar finishDate = GregorianCalendar.getInstance();
			finishDate.setTime(getFinish());
			
			Period period = new Period();
			period.setStart(startDate);
			period.setFinish(finishDate);
			advertisement.setPublishingPeriod(period);

			// keywords
			// advertisement.setKeywords("Java,book,jsf");
			advertisement.setStatus(1);

			PublishingHeader header = new PublishingHeader();
			header.setCustomerDomainId(domainId);
			header.setCustomerLogin("arisson");

			if(avatarImageOrUrl.equals(AvatarType.URL.getType())){
				try{
					URL url = new URL(getAdvertisement().getAvatarImageOrUrl().getUrl());
					URLConnection cn = url.openConnection();
					getAdvertisement().getAvatarImageOrUrl().setImage(new AtavarImage());
					getAdvertisement().getAvatarImageOrUrl().getImage().setContentType(cn.getContentType());
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		try {
			SERVICE.publishOperation(advertisement, header);
			clearPublishData();
			addMessage("Publish with success!", FacesMessage.SEVERITY_INFO, null);
		} catch (Exception e) {
			e.printStackTrace();
			addMessage("Ops, something wrong happened :(  ", FacesMessage.SEVERITY_ERROR, null);
		}
	}
	
	private void clearPublishData() {
		setAdvertisement(new Advertisement());
		setStartDate(null);
		setFinishDate(null);
		setSelectedTab("tab1");
		clearUploadData();
	}
	
	public static void addMessage(String messageText, Severity typeMessage,
			String messageException) {
		FacesContext context = FacesContext.getCurrentInstance();
		StringBuffer summary = new StringBuffer();
		summary.append(messageText);
		if (messageException != null) {
			summary.append(messageException);
		}
		FacesMessage message = new FacesMessage(typeMessage, summary.toString(), null);
		context.addMessage(null, message);
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
			if (object instanceof String) {
				stream.write(getFiles()
						.get(Integer.parseInt(object.toString())).getValue());
			} else {
				stream.write(getFiles().get((Integer) object).getValue());
			}
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
				
				//TODO: I thing need change this for run fine in LINUX SO.
				String fileName = item.getFileName();
				fileName = fileName.substring(fileName.lastIndexOf('\\')+1);
				
				getAdvertisement().getAvatarImageOrUrl().setName(fileName);
				getAdvertisement().getAvatarImageOrUrl().setDescription(fileName);
				getAdvertisement().getAvatarImageOrUrl().getImage()
						.setContentType(item.getContentType());

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
