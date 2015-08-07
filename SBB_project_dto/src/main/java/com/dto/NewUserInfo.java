package com.dto;

public class NewUserInfo {
private String login;
private String password;
public NewUserInfo(String login, String password) {
	super();
	this.login = login;
	this.password = password;
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
