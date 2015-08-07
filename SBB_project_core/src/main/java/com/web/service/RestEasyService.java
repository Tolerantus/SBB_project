package com.web.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.RequestParam;


public interface RestEasyService {
	@GET
	@Produces("application/xml")
	@Path("/{startDate}/{stopDate}")
	public Response getTicketsBetween(@RequestParam("startDate") String startDate, 
			@RequestParam("stopDate") String stopDate);
}
