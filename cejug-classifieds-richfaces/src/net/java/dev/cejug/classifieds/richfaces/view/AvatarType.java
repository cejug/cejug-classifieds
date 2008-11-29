package net.java.dev.cejug.classifieds.richfaces.view;

public enum AvatarType {
	IMAGE("I"),
	URL("U");
	
	private String type;
	
	AvatarType(String type){
		this.type = type;
	}

	public String getType() {
		return type;
	}
	
	
}
