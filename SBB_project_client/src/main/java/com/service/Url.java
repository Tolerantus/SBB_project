package com.service;

public enum Url {
	
	USER("user"),
	TICK_BETW("ticketsBetween"),
	TICK_BEF("ticketsBefore"),
	TICK_AF("ticketsAfter"),
	TICK_ALL("ticketsAll");
	
	private String url;
	private Url(String url) {
		this.url = "http://localhost:8080/SBB_project_core/rest/Service/" + url +"/";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
}
