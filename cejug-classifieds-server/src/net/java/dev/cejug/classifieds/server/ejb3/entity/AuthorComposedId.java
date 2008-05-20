package net.java.dev.cejug.classifieds.server.ejb3.entity;

public class AuthorComposedId {
	private int partnerId;
	private String login;

	public AuthorComposedId() {
	}

	public AuthorComposedId(int partner, String login) {
		this.partnerId = partner;
		this.login = login;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof AuthorComposedId) {
			AuthorComposedId otherAuthor = (AuthorComposedId) obj;
			return login.equals(otherAuthor.getLogin())
					&& partnerId == otherAuthor.getPartnerId();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return login.hashCode() + partnerId;
	}

	public int getPartnerId() {
		return partnerId;
	}

	public String getLogin() {
		return login;
	}
}
