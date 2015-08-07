package com.dto;

public class TicketInfo {
	private String ticketInfo;
	private boolean isExist;
	public TicketInfo(String ticketInfo, boolean isExist) {
		super();
		this.ticketInfo = ticketInfo;
		this.isExist = isExist;
	}
	public String getTicketInfo() {
		return ticketInfo;
	}
	public void setTicketInfo(String ticketInfo) {
		this.ticketInfo = ticketInfo;
	}
	public boolean isExist() {
		return isExist;
	}
	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}
	@Override
	public String toString() {
		return "TicketInfo [ticketInfo=" + ticketInfo + ", isExist=" + isExist
				+ "]";
	}
	

}
