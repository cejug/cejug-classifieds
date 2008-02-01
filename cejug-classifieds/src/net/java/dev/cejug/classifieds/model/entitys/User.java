package net.java.dev.cejug.classifieds.model.entitys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity for User
 * 
 * @author Rafael Carneiro [rafaelcarneirob@gmail.com]
 * @since 02/01/2008
 */

@Entity
@Table(name="USER")
public class User {

	
	@Id
	@Column(name="USER_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="USER_NAME",nullable=false)
	private String name;
	
	@Column(name="USER_EMAIL",nullable=false)
	private String email;
	
	@Column(name="USER_IS_MEMBER",nullable=false)
	private Boolean isMember;
	
	/*
	 * Future attributes: security information, special member, etc.
	 */ 
	
	public User() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsMember() {
		return isMember;
	}

	public void setIsMember(Boolean isMember) {
		this.isMember = isMember;
	}

}
