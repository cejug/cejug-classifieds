package net.java.dev.cejug.classifieds.richfaces.view;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.faces.model.SelectItem;

import net.java.dev.cejug_classifieds.metadata.attachments.AtavarImage;
import net.java.dev.cejug_classifieds.metadata.attachments.AvatarImageOrUrl;
import net.java.dev.cejug_classifieds.metadata.business.Advertisement;
import net.java.dev.cejug_classifieds.metadata.business.PublishingHeader;

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller(value = "publishAdvertisementBean")
@Scope("session")
public class PublishAdvertisementBean {
//	private transient final CejugClassifiedsBusiness SERVICE;
	
	private Advertisement advertisement = new Advertisement();

	private String avatarImageOrUrl = "I";

	public PublishAdvertisementBean(){
//		SERVICE = new CejugClassifiedsServiceBusiness()
//		.getCejugClassifiedsBusiness();

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
	
	public void publish(){
		PublishingHeader header = new PublishingHeader();
		header.setCustomerDomainId(3L);
		header.setCustomerLogin("3");
		
		try{
			System.out.println("Publishing....");
			System.out.println(getAdvertisement().getText());
		//	SERVICE.publishOperation(advertisement, header);
		
		}catch (Exception e) {
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
			try{
				getAdvertisement().setAvatarImageOrUrl(new AvatarImageOrUrl());
				getAdvertisement().getAvatarImageOrUrl()
						.setImage(new AtavarImage());
				getAdvertisement().getAvatarImageOrUrl().getImage().setValue(
						item.getData());
	
				files.add(getAdvertisement().getAvatarImageOrUrl().getImage());
				setUploadsAvailable(getUploadsAvailable() - 1);
			}catch (Exception e) {
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
