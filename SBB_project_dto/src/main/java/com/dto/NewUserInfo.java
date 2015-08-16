package com.dto;

public class NewUserInfo {
private String login;
private String password;
private boolean admin;

public NewUserInfo(String login, String password, boolean admin) {
	super();
	this.login = login;
	this.password = password;
	this.admin = admin;
}

public boolean isAdmin() {
	return admin;
}

public void setAdmin(boolean admin) {
	this.admin = admin;
}

public String getLogin() {
	return login;
}
public void setLogin(String login) {
	this.login = login;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
@Override
public String toString() {
	return "NewUserInfo [login=" + login + ", password=" + password + "]";
}

}
