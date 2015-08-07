package com.dto;

public class UserLoginContainer {
	private String login;

	public UserLoginContainer(String login) {
		super();
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String toString() {
		return "UserLoginContainer [login=" + login + "]";
	}
	
}
