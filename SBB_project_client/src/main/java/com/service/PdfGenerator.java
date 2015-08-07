package com.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;








import com.cdi.bean.LoginBean;
import com.cdi.bean.TicketContainerBean;
import com.cdi.bean.TicketInformationFilter;
import com.dto.TicketArrivalTimeAndCost;
import com.dto.TicketContainer;
import com.entities.Ticket;
import com.entities.User;
import com.entities.UserAndTicket;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.ticket.filter.item.JourneyFilterItem;
import com.ticket.filter.item.PassengerFilterItem;
import com.ticket.filter.item.TicketFilterItem;

@Named
@RequestScoped
public class PdfGenerator implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7749167368774165197L;

	@Inject
	private TicketContainerBean ticketContainerBean;
	@Inject
	private LoginBean loginBean;
	@Inject
	private TicketInformationFilter filter;
	
	public void createPdf() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
	    Document doc = new Document();
	    response.reset();
	    response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition","C://Documents//Tsys//E_prjs//Tickets.pdf");
	    TicketContainer container = ticketContainerBean.getTicketContainer();
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm  dd-MM-yyyy");
	    sdf.setTimeZone(TimeZone.getTimeZone("Etc/GMT-3"));
	    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
	    try {
			PdfWriter.getInstance(doc, response.getOutputStream());
			doc.open();
			String timeConstraints = "";
			Date start = filter.getStartDate();
			Date finish = filter.getFinishDate();
			PdfPTable header = new PdfPTable(1);
		    header.setWidthPercentage(100);
		    Image img = Image.getInstance(
		    		"C:/Documents/Tsys/E_prjs/SBB_project/SBB_project_client/WebContent/resources/theme/img/tickets-header.jpg");
		    header.addCell(new PdfPCell(img, true));
		    doc.add(header);
			if (start != null && finish != null) {
				timeConstraints = "Tickets sold in period from " + sdf1.format(filter.getStartDate())
						+ " to " + sdf1.format(finish);
			} else 
			if (start != null) {
				timeConstraints = "Tickets sold from " + sdf1.format(filter.getStartDate());
			} else 
			if (finish != null) {
				timeConstraints = "Tickets sold until " + sdf1.format(finish);
			} else {
				timeConstraints = "Tickets list";
			}
		    doc.add(new Paragraph(timeConstraints));
		    doc.add(new Paragraph("Printed " + sdf.format(new Date()) + " for " + loginBean.getEmail()));
		    doc.add(new Paragraph(" "));
		    
		    PdfPTable table = new PdfPTable(1);
		    table.setWidthPercentage(100);
		    List<UserAndTicket> userAndTickets = container.getTickets();
		    
		    for (int i=0; i<userAndTickets.size(); i++) {
		    	TicketArrivalTimeAndCost timeAndCost = 
		    			container.getTicketArrivalTimeAndCost().get(i);
		    	String cellText = getTicketInfo(userAndTickets.get(i), timeAndCost);
		    	cellText += "\n" + getPassengerInfo(userAndTickets.get(i));
		    	cellText += "\n" + getJourneyInfo(userAndTickets.get(i), timeAndCost);
		    	PdfPCell cell = new PdfPCell(new Phrase(cellText));
		    	table.addCell(cell);
		    }
		    doc.add(table);
		    doc.close();
		    
		} catch (Exception e) {
			e.printStackTrace();
		} 
	    
	    
	    
	    facesContext.responseComplete();
	}
	public String getPassengerInfo(UserAndTicket userAndTicket) {
		String result = "";
		Ticket ticket = userAndTicket.getTicket();
		PassengerFilterItem[] pasFilter = filter.getPassengerInfo();
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.US);
		if (pasFilter == null || pasFilter.length == 0) {
			return result;
		} 
		result += "passenger: ";
		for (PassengerFilterItem filterItem : pasFilter) {
			switch (filterItem) {
				case NAME:
					result += " " + ticket.getPassenger().getPassengerName();break;
				case SURNAME:
					result += " " + ticket.getPassenger().getPassengerSurname();break;
				case BIRTHDAY:
					result += " " + sdf.format(ticket.getPassenger().getPassengerBirthday());break;
				default:
					return result;
			}
		}
		return result;
	}
	public String getJourneyInfo(UserAndTicket userAndTicket, TicketArrivalTimeAndCost timeAndCost) {
		String result = "";
		Ticket ticket = userAndTicket.getTicket();
		JourneyFilterItem[] jourFilter = filter.getJourneyInfo();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy", Locale.US);
		if (jourFilter == null || jourFilter.length == 0) {
			return result;
		} 
		
		for (JourneyFilterItem filterItem : jourFilter) {
			switch (filterItem) {
				case TRAIN:
					result += "train #" + ticket.getJourney().getTrain().getTrainId();break;
				case ROUTE: 
					result += " route: " + ticket.getJourney().getRoute().getRouteName() + "\n";break;
				case DEP_ST:
					result += "department: " + ticket.getStDep().getStationName();break;
				case DEP_TIME:
					result += " " + sdf.format(ticket.getJourney().getTimeDep()) + "\n";break;
				case ARR_ST:
					result += "destination: " + ticket.getStArr().getStationName();break;
				case ARR_TIME:
					result += " " + sdf.format(timeAndCost.getArrivalTime());break;
				
				default:
					return result;
			}
		}
		return result;
	}
	public String getTicketInfo(UserAndTicket userAndTicket, TicketArrivalTimeAndCost timeAndCost) {
		String result = "";
		Ticket ticket = userAndTicket.getTicket();
		User user = userAndTicket.getUser();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm  dd-MM-yyyy");
		TicketFilterItem[] ticFilter = filter.getTicketInfo();
		if (ticFilter == null || ticFilter.length == 0) {
			return result;
		}
		for (TicketFilterItem filterItem : ticFilter) {
			switch(filterItem) {
				case NUMBER:
					result += "Ticket #" + ticket.getTicketId();break;
				case USER:
					result += " registered on e-mail: " + user.getUserLogin() + "\n";break;
				case COST:
					result += "cost: " + timeAndCost.getCost() + ";";break;
				case PURCH_DATE:
					result += " purchase time: " + sdf.format(ticket.getPurchaseDate());break;
				default:
					return result;
			}
			
		}
		return result;
	}
}
