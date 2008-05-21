package net.java.dev.cejug.classifieds.server.ejb3.entity;

public class AuthorComposedId {
	private String domain;
	private String login;

	public AuthorComposedId() {
	}

	public AuthorComposedId(String domain, String login) {
		this.domain = domain;
		this.login = login;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj instanceof AuthorComposedId) {
			AuthorComposedId otherAuthor = (AuthorComposedId) obj;
			return login.equals(otherAuthor.getLogin())
					&& domain == otherAuthor.getDomain();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return login.hashCode() + domain.hashCode();
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

}
