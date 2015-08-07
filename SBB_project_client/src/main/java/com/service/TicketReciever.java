package com.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;



import com.cdi.bean.TicketContainerBean;
import com.cdi.bean.TicketInformationFilter;
import com.dto.TicketContainer;

@Named
@RequestScoped
public class TicketReciever {
	@Inject
	private TicketInformationFilter filter;
	@Inject
	private TicketContainerBean tickets;
	@Inject
	private PdfGenerator pdfGenerator;
	
	public void getTickets() {
		Date start = filter.getStartDate();
		Date finish = filter.getFinishDate();
		TicketContainer container = getTickets(start, finish);
		tickets.setTickets(container);
		pdfGenerator.createPdf();
	}
	
	public TicketContainer getTickets(Date start, Date finish) {
		String url = getUrl(start, finish);
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(url);
		Response response = target.request().get();
		return response.readEntity(TicketContainer.class);
	}
	
	public String getUrl(Date start, Date finish) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String url = null;
		
		if (start != null && finish != null) {
			url = Url.TICK_BETW.getUrl() + 
	    	    	sdf.format(start) + "/" + sdf.format(finish);
		} else 
		if (start != null) {
			url = Url.TICK_AF.getUrl() + 
	    	    	sdf.format(start);
		} else 
		if (finish != null) {
			url = Url.TICK_BEF.getUrl() + 
					sdf.format(finish);
		} else {
			url = Url.TICK_ALL.getUrl();
		}
		return url;
	}
}
