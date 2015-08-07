package com.cdi.bean;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;




@SessionScoped
@Named
public class LoginBean implements Serializable{
	/**
	 * 
	 */
	/*@Inject
	private UserValidator userValidator;
	@Inject
	private FacesContext facesContext;*/
	
	private static final long serialVersionUID = 4023604318591076758L;
	private String email;
	private String password;
	private boolean admin;
	private String status;
	
	public String getStatus() {
		if (status == null) {
			return "";
		}
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public String logOut() {
		email = null;
		password = null;
		status = null;
		admin = false;
		return "Login";
	}
	
	
}
