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

import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller(value = "publishAdvertisementBean")
@Scope("session")
public class PublishAdvertisementBean {
	private Advertisement advertisement = new Advertisement();

	private String avatarImageOrUrl = "I";

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

	/* File Upload */
	private ArrayList<AtavarImage> files = new ArrayList<AtavarImage>();
	private int uploadsAvailable = 1;
	private boolean autoUpload = true;
	private boolean useFlash = false;

	public int getSize() {
		if (getFiles().size() > 0) {
			return getFiles().size();
		} else {
			return 0;
		}
	}

	public synchronized void paint(OutputStream stream, Object object)
			throws IOException {
		stream.write(getFiles().get((Integer) object).getValue());
	}

	public synchronized void listener(UploadEvent event) throws Exception {
		UploadItem item = event.getUploadItem();

		getAdvertisement().setAvatarImageOrUrl(new AvatarImageOrUrl());
		getAdvertisement().getAvatarImageOrUrl().setImage(new AtavarImage());
		getAdvertisement().getAvatarImageOrUrl().getImage().setValue(
				item.getData());

		files.add(getAdvertisement().getAvatarImageOrUrl().getImage());
		setUploadsAvailable(getUploadsAvailable() - 1);
	}

	public String clearUploadData() {
		files.clear();
		setUploadsAvailable(5);
		return null;
	}

	public long getTimeStamp() {
		return System.currentTimeMillis();
	}

	public ArrayList<AtavarImage> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<AtavarImage> files) {
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
