package com.web.service;

import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dto.TicketContainer;
import com.dto.TicketListConstraints;
import com.entities.Ticket;
import com.service.Dispatcher;

@RestController
public class WebServiceRestController {
	private static final Logger LOG = Logger.getLogger(WebServiceRestController.class);
	@Autowired
	private Dispatcher dispatcher;
	
	@RequestMapping("/ticketsBetween")
	public Response getTicketsBetween(@RequestParam("startDate") String startDate, 
			@RequestParam("stopDate") String stopDate) {
		
		TicketContainer container = new TicketContainer();
		container.setConstraints(TicketListConstraints.BETWEEN);
		container.setStartDate(startDate);
		container.setStopDate(stopDate);
		return buildResponse(container);
	}
	@RequestMapping("/ticketsBefore")
	public Response getTicketsBefore(@RequestParam("stopDate") String stopDate) {
		TicketContainer container = new TicketContainer();
		container.setConstraints(TicketListConstraints.BEFORE);
		container.setStopDate(stopDate);
		return buildResponse(container);
	}
	@RequestMapping("/ticketsAfter")
	public Response getTicketsAfter(@RequestParam("startDate") String startDate) {
		TicketContainer container = new TicketContainer();
		container.setConstraints(TicketListConstraints.AFTER);
		container.setStartDate(startDate);
		return buildResponse(container);
	}
	@RequestMapping("/test")
	public Response getTicketsAfter(@RequestParam("startDate") String startDate, 
			@RequestParam("stopDate") String stopDate, @RequestParam("train") String train) {
		LOG.error(train.equals("undefined"));
		
		TicketContainer container = new TicketContainer();
		container.setConstraints(TicketListConstraints.BETWEEN);
		container.setStartDate(startDate);
		container.setStopDate(stopDate);
		return buildResponse(container);
		
	}
	
	@SuppressWarnings("unchecked")
	public Response buildResponse(TicketContainer container) {
		List<Ticket> tickets = null;
		try {
			tickets = (List<Ticket>) dispatcher.service(container);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (tickets != null) {
			GenericEntity<List<Ticket>> entity = new GenericEntity<List<Ticket>>(tickets){};
			return Response.ok(entity).build();
		} else {
			return Response.status(404).build();
		}
	}
}
