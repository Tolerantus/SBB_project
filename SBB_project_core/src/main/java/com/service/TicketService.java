package com.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.TicketArrivalTimeAndCost;
import com.dto.TicketContainer;
import com.dto.TicketListConstraints;
import com.entities.Shedule;
import com.entities.Ticket;
import com.entities.UserAndTicket;

@Service("ticketService")
public class TicketService {
	private Dao dao;
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	
	@Transactional
	public TicketContainer dispatch(TicketContainer container) {
		TicketListConstraints constraint = container.getConstraints();
		String startDate = container.getStartDate();
		String stopDate = container.getStopDate();
		switch (constraint) {
			case BETWEEN:
				container.setTickets(getTicketsBetween(startDate, stopDate));
				break;
			
			case AFTER:
				container.setTickets(getTicketsAfter(startDate));
				break;
				
			case BEFORE:
				container.setTickets(getTicketsBefore(stopDate));
				break;
				
			case NONE:
				container.setTickets(getAllTickets());
				break;
				
			default:
				return null;
		}
		container.setTicketArrivalTimeAndCost(getArrivalTimeAndCosts(container.getTickets()));
		return container;
		
	}
	
	public List<UserAndTicket> getTicketsBetween(String startDate, String stopDate) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		List<UserAndTicket> tickets = null;
		try {
			tickets = dao.getTicketsBetweenDates(sdf.parse(startDate), sdf.parse(stopDate));
			
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return tickets;
	}
	
	public List<UserAndTicket> getTicketsBefore(String stopDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	
		List<UserAndTicket> tickets = null;
		try {
			tickets = dao.getTicketsBefore(sdf.parse(stopDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return tickets;
	}

	public List<UserAndTicket> getTicketsAfter(String startDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		List<UserAndTicket> tickets = null;
		try {
			tickets = dao.getTicketsAfter(sdf.parse(startDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return tickets;
	}
	public List<UserAndTicket> getAllTickets() {
		return dao.getAllTickets();
	}
	public List<TicketArrivalTimeAndCost> getArrivalTimeAndCosts(List<UserAndTicket> userAndTickets) {
		List<TicketArrivalTimeAndCost> timeAndCosts = new ArrayList<TicketArrivalTimeAndCost>();
		for (UserAndTicket ut : userAndTickets) {
			Ticket t = ut.getTicket();
			Date departureTime = t.getJourney().getTimeDep();
			long duration = 0;
			double cost = 0;
			List<Shedule> shedules = dao.getShedulesOfTicket(t);
			for (Shedule s : shedules) {
				duration += s.getDirection().getTime();
				cost += s.getDirection().getCost();
			}
			timeAndCosts.add(new TicketArrivalTimeAndCost(t, new Date(departureTime.getTime() + duration), cost));
		}
		return timeAndCosts;
	}
}
