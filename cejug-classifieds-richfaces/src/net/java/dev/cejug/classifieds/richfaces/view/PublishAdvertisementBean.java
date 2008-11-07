package net.java.dev.cejug.classifieds.richfaces.view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;



import net.java.dev.cejug_classifieds.metadata.business.Advertisement;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller(value = "publishAdvertisementBean")
@Scope("request")
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
}
