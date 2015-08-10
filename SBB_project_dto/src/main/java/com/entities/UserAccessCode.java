package com.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class UserAccessCode implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9141245869912317506L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;
	
	private String code;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public UserAccessCode() {
		super();
	}

	public UserAccessCode(int id, User user, String code) {
		super();
		this.id = id;
		this.user = user;
		this.code = code;
	}
	
	
}
