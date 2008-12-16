package net.java.dev.cejug.classifieds.richfaces.view;

public enum AvatarType {
    IMAGE("I"), URL("U");

    private String type;

    AvatarType(String type) {

        this.type = type;
    }

    public String getType() {

        return type;
    }

    public static AvatarType getAvatarType(String type) {

        for (AvatarType avatarType : AvatarType.values()) {
            if (avatarType.getType().equals(type)) {
                return avatarType;
            }
        }

        return null;
    }

}
