package com.dto;

import java.io.Serializable;

import com.entities.UserAccessCode;

public class CodeRole implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3347953418185639328L;
	private UserAccessCode userAccessCode;
	private boolean admin;
	
	public UserAccessCode getUserAccessCode() {
		return userAccessCode;
	}
	public void setUserAccessCode(UserAccessCode userAccessCode) {
		this.userAccessCode = userAccessCode;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public CodeRole() {
		super();
	}
	public CodeRole(UserAccessCode userAccessCode, boolean admin) {
		super();
		this.userAccessCode = userAccessCode;
		this.admin = admin;
	}	
	
}
