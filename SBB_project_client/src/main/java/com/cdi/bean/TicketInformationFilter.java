package com.cdi.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.ticket.filter.item.JourneyFilterItem;
import com.ticket.filter.item.PassengerFilterItem;
import com.ticket.filter.item.TicketFilterItem;


@Named
@SessionScoped
public class TicketInformationFilter implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5853825101358335490L;
	private PassengerFilterItem passengerInfo[];
	private JourneyFilterItem journeyInfo[];
	private TicketFilterItem ticketInfo[];
	private Date startDate;
	private Date finishDate;
	private int appliedTopics;
	private String errorDates;
	
	public PassengerFilterItem[] getPassengerInfoValue()	{	
		passengerInfo = new PassengerFilterItem[3];
		passengerInfo[0] = PassengerFilterItem.NAME;
		passengerInfo[1] = PassengerFilterItem.SURNAME;
		passengerInfo[2] = PassengerFilterItem.BIRTHDAY;
		return passengerInfo;
	}
	public JourneyFilterItem[] getJourneyInfoValue() {
		journeyInfo = new JourneyFilterItem[6];
		journeyInfo[0] = JourneyFilterItem.TRAIN;
		journeyInfo[1] = JourneyFilterItem.ROUTE;
		journeyInfo[2] = JourneyFilterItem.DEP_ST;
		journeyInfo[3] = JourneyFilterItem.DEP_TIME;
		journeyInfo[4] = JourneyFilterItem.ARR_ST;
		journeyInfo[5] = JourneyFilterItem.ARR_TIME;
		return journeyInfo;
	}
	public TicketFilterItem[] getTicketInfoValue() {
		ticketInfo = new TicketFilterItem[4];
		ticketInfo[0] = TicketFilterItem.NUMBER;
		ticketInfo[1] = TicketFilterItem.USER;
		ticketInfo[2] = TicketFilterItem.COST;
		ticketInfo[3] = TicketFilterItem.PURCH_DATE;
		return ticketInfo;
	}
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
		checkDate();
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
		checkDate();
	}
	
	public PassengerFilterItem[] getPassengerInfo() {
		return passengerInfo;
	}
	public void setPassengerInfo(PassengerFilterItem[] passengerInfo) {
		this.passengerInfo = passengerInfo;
		resetFilterNumber();
	}
	public JourneyFilterItem[] getJourneyInfo() {
		return journeyInfo;
	}
	public void resetFilterNumber() {
		appliedTopics = (passengerInfo == null?0:passengerInfo.length) + 
				(journeyInfo == null?0:journeyInfo.length) + 
				(ticketInfo == null?0:ticketInfo.length);
	}
	public void setJourneyInfo(JourneyFilterItem[] journeyInfo) {
		this.journeyInfo = journeyInfo;
		resetFilterNumber();
	}
	public TicketFilterItem[] getTicketInfo() {
		return ticketInfo;
	}
	public void setTicketInfo(TicketFilterItem[] ticketInfo) {
		this.ticketInfo = ticketInfo;
		resetFilterNumber();
	}
	public String getPassengerInfoInString() {
		return Arrays.toString(passengerInfo);
	}
	public String getJourneyInfoInString() {
		return Arrays.toString(journeyInfo);
	}
	public String getTicketInfoInString() {
		return Arrays.toString(ticketInfo);
	}
	public int getAppliedTopics() {
		return appliedTopics;
	}
	public void setAppliedTopics(int appliedTopics) {
		this.appliedTopics = appliedTopics;
	}
	
	public String getErrorDates() {
		return errorDates;
	}
	public void setErrorDates(String errorDates) {
		this.errorDates = errorDates;
	}
	public String checkDate() {
		errorDates = null;
		if (startDate != null && finishDate != null && finishDate.getTime() <= startDate.getTime()) {
			errorDates = "Finish date must be later than start date or be empty!";
			return "TicketWindow";
		} else {
			return "ChosenOptions";
		}
	}
	
	
	
}
