package com.entities;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: User
 *
 */
@Entity

public class User implements Serializable {
@Id	
@GeneratedValue (strategy=GenerationType.AUTO)
@Column(name = "user_id")
private int userId;

@Column(name = "user_login")
private String userLogin;

@Column(name = "user_password")
private String userPassword;

@Column(name = "account_type")
private boolean accountType;

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + userId;
	return result;
}


@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	User other = (User) obj;
	if (userId != other.userId)
		return false;
	return true;
}



public User(int userId, String userLogin, String userPassword,
		boolean accountType) {
	super();
	this.userId = userId;
	this.userLogin = userLogin;
	this.userPassword = userPassword;
	this.accountType = accountType;
}


public int getUserId() {
	return userId;
}


public void setUserId(int userId) {
	this.userId = userId;
}


public String getUserLogin() {
	return userLogin;
}


public void setUserLogin(String userLogin) {
	this.userLogin = userLogin;
}


public String getUserPassword() {
	return userPassword;
}


public void setUserPassword(String userPassword) {
	this.userPassword = userPassword;
}


public boolean isAccountType() {
	return accountType;
}


public void setAccountType(boolean accountType) {
	this.accountType = accountType;
}



	private static final long serialVersionUID = 1L;

	public User() {
		super();
	}
   
}
