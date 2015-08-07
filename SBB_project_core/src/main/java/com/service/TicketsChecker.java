package com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.ListOfTickets;
import com.dto.UserLoginContainer;
import com.entities.Direction;
import com.entities.Journey;
import com.entities.Route;
import com.entities.Shedule;
import com.entities.Station;
import com.entities.Ticket;
import com.entities.User;
@Service("ticketsChecker")
public class TicketsChecker {
	private Dao dao;
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	private static final Logger LOG = Logger.getLogger(TicketsChecker.class);

	@Transactional
	public ListOfTickets check(UserLoginContainer dto){
		LOG.debug("=====================================================================");
		LOG.debug(dto);
		LOG.debug("=====================================================================");
		String username = dto.getLogin();
		User u = dao.getUserByName(username);
		List<Ticket> tickets = dao.getTicketOfUser(u.getUserId());
		List<String> ticketsData = getTicketData(tickets);
		ListOfTickets list = new ListOfTickets(ticketsData);
		LOG.debug("=====================================================================");
		LOG.debug(list);
		LOG.debug("=====================================================================");
		return list;
	}
	private  List<String> getTicketData(List<Ticket> tickets){
		if (tickets==null){
			return null;
		}else if(tickets.isEmpty()){
			return null;
		}else{
			List<String> ticketData = new ArrayList<String>();
			for (Ticket ticket:tickets){
				String passengerName = (ticket.getPassenger()).getPassengerName();
				String passenger_surname = (ticket.getPassenger()).getPassengerSurname();

				Station stDep = (ticket.getStDep());
				Station stArr = (ticket.getStArr());
				Journey journey = (ticket.getJourney()); 
				Date trainDeparture = journey.getTimeDep();
				Date passengerDep = trainDeparture;
				Date passengerArr = trainDeparture;
				Route route = (journey.getRoute());
				List<Shedule> steps = dao.getShedulesOfRoute(route.getRouteId());
				int jour_begin=0;
				int jour_end=0;
				double cost=0;
				for (Shedule s: steps){
					Direction d = (s.getDirection());
					
					Station sDep= (d.getStDep());
					Station sArr= (d.getStArr());
					if (sDep.getStationId()==stDep.getStationId()){
						jour_begin=s.getStep();
					}
					if (sArr.getStationId()==stArr.getStationId()){
						jour_end=s.getStep();
					}
				}
				for(Shedule s : steps){
					Direction d = (s.getDirection());
					if (s.getStep()<jour_begin){
						passengerDep=new Date(passengerDep.getTime()+d.getTime());
						passengerArr=new Date(passengerArr.getTime()+d.getTime());
					}
					if (s.getStep()>=jour_begin&&s.getStep()<=jour_end){
						passengerArr=new Date(passengerArr.getTime()+d.getTime());
						cost+=d.getCost();
					}
				}
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.US);
				StringBuilder sb = new StringBuilder();
				sb.append(ticket.getTicketId());
				sb.append(",");
				sb.append(passengerName+" "+passenger_surname);
				sb.append(",");
				sb.append(stDep.getStationName());
				sb.append(",");
				sb.append(sdf.format(passengerDep));
				sb.append(",");
				sb.append(stArr.getStationName());
				sb.append(",");
				sb.append(sdf.format(passengerArr));
				sb.append(",");
				sb.append(cost);
				ticketData.add(sb.toString());
			}
			return ticketData;
		}
	}
}
