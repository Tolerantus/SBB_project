package com.dto;

public class MoneyPutRequest {
	private String userName;
	private String cash;
	public MoneyPutRequest(String userName, String cash) {
		super();
		this.userName = userName;
		this.cash = cash;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCash() {
		return cash;
	}
	public void setCash(String cash) {
		this.cash = cash;
	}
	
}
