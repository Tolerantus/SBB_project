package com.web.service;


import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.dto.TicketContainer;
import com.dto.TicketListConstraints;
import com.dto.UserInfo;
import com.service.Dispatcher;

@Path("/Service")
public class RestEasyServiceImpl {
	private Dispatcher dispatcher;
	
	public void init(@Context ServletContext servletContext) {
		if (dispatcher == null) {
			ApplicationContext ctx = 
	                WebApplicationContextUtils.getWebApplicationContext(servletContext);
			dispatcher= ctx.getBean("dispatcher", Dispatcher.class);
		}
	}
	@GET
	@Produces("application/Json")
	@Path("/ticketsBetween/{startDate}/{stopDate}")
	public Response getTicketsBetween(@PathParam("startDate") String startDate, 
			@PathParam("stopDate") String stopDate, @Context ServletContext servletContext) {
		init(servletContext);
		TicketContainer container = new TicketContainer();
		container.setConstraints(TicketListConstraints.BETWEEN);
		container.setStartDate(startDate);
		container.setStopDate(stopDate);
		return buildResponse(container);
	}
	@GET
	@Produces("application/Json")
	@Path("/ticketsBefore/{stopDate}")
	public Response getTicketsBefore(@PathParam("stopDate") String stopDate, @Context ServletContext servletContext) {
		init(servletContext);
		TicketContainer container = new TicketContainer();
		container.setConstraints(TicketListConstraints.BEFORE);
		container.setStopDate(stopDate);
		return buildResponse(container);
	}
	
	@GET
	@Produces("application/Json")
	@Path("/ticketsAfter/{startDate}")
	public Response getTicketsAfter(@PathParam("startDate") String startDate, @Context ServletContext servletContext) {
		init(servletContext);
		TicketContainer container = new TicketContainer();
		container.setConstraints(TicketListConstraints.AFTER);
		container.setStartDate(startDate);
		return buildResponse(container);
	}
	
	@GET
	@Produces("application/Json")
	@Path("/ticketsAll")
	public Response getAllTicketsBefore(@Context ServletContext servletContext) {
		init(servletContext);
		TicketContainer container = new TicketContainer();
		container.setConstraints(TicketListConstraints.NONE);
		return buildResponse(container);
	}
	@GET
	@Produces("application/Json")
	@Path("/user/{email}/{password}")
	public Response validateUser(@PathParam("email") String email,
			@PathParam("password") String password, @Context ServletContext servletContext) {
		init(servletContext);
		UserInfo info = new UserInfo(email, password);
		return buildResponse(info);
	}
	
	

	public Response buildResponse(Object dto) {
		Object result = null;
		try {
			result = dispatcher.service(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != null) {
			GenericEntity<Object> entity = new GenericEntity<Object>(result){};
			return Response.ok(entity).build();
		} else {
			return Response.status(200).entity("tickets not found").build();
		}
	}
}
