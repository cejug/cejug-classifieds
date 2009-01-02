package net.java.dev.cejug.classifieds.richfaces.view;

public class StarRatingBean {

	private String vote;

	public String getVote() {
		return vote;
	}

	public void setVote(String vote) {
		System.out.println("-------------------------------");
		System.out.println("VOTE: " + vote + "!!!!!!!!!!!!!");
		System.out.println("-------------------------------");
		this.vote = vote;
	}

}
