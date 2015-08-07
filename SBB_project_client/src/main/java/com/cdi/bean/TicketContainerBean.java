package com.cdi.bean;


import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.dto.TicketContainer;


@Named
@SessionScoped
public class TicketContainerBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 833723733000524121L;
	private TicketContainer ticketContainer;

	public TicketContainer getTicketContainer() {
		return ticketContainer;
	}

	public void setTickets(TicketContainer ticketContainer) {
		this.ticketContainer = ticketContainer;
	}

		
	
}
