package com.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.PassengerInfo;
import com.dto.TicketInfo;
import com.entities.Passenger;
import com.entities.Station;
import com.entities.Ticket;
import com.entities.User;
@Service("passengerRegistrator")
public class PassengerRegistrator {
	private Dao dao;
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	private static final Logger LOG = Logger.getLogger(PassengerRegistrator.class);

/*input data like:
 * currentUser:"root",
 * passengerDepAndDestStations:"journeyId;step_dep;step_arr;st_dep;st_arr"
 * day:"1"
 * month:"1"
 * name:"name"
 * surname:"surname"
 * year:"2015"
 * allJourneysData:"journeyId;HH:mm dd MMM;HH:mm dd MMM;cost"
 * */
	@Transactional
	public  TicketInfo register(PassengerInfo dto) throws ParseException{

		LOG.debug("=====================================================================");
		LOG.debug(dto);
		LOG.debug("=====================================================================");
			TicketInfo info = new TicketInfo(null, false);
			String strBirthday = dto.getBirthday();
			String name = dto.getName();
			String surname = dto.getSurname();
			String passengerDepAndDestStations = dto.getPassengerDepAndDestStations();
			String user = dto.getCurrentUser(); User currentUser = dao.getUserByName(user);
			double userCash = currentUser.getCash();
			
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String[] passengerDepAndDestStations_tokens = passengerDepAndDestStations.split(";");
			
			
			
			Date birthday = sdf.parse(strBirthday);
			if (birthday.getTime()>new Date().getTime()){
				info.setTicketInfo("birthday");
				return info;
			}
			Passenger passenger = dao.getPassenger(name, surname, birthday);
			if (passenger == null) {
				passenger = dao.createPassenger(name, surname, birthday);
			}
			
			int journeyId = Integer.parseInt(passengerDepAndDestStations_tokens[0]);
			Station stDep = dao.getStation(Integer.parseInt(passengerDepAndDestStations_tokens[3]));
			Station stArr = dao.getStation(Integer.parseInt(passengerDepAndDestStations_tokens[4]));
			List<Ticket> ticketsOfThisPassenger = dao.getTicketsOfPassenger(passenger);
			for (Ticket t : ticketsOfThisPassenger) {
				if (t.getJourney().getJourneyId() == journeyId) {
					info.setExist(true);
				}
			}
			if (!info.isExist()) {
				
				List<String> allJourneysData = dto.getAllJourneysData();
				String passengerDepAndDestTime = "";
				for (String s : allJourneysData) {
					if (s.startsWith(passengerDepAndDestStations_tokens[0])) {
						passengerDepAndDestTime = s;
					}
				}
				String[] passengerDepAndDestTime_tokens = passengerDepAndDestTime.split(";");
				String cost = passengerDepAndDestTime_tokens[3];
				double ticketCost = Double.parseDouble(cost);
				
				if (ticketCost <= userCash) {
					
					Ticket newTicket = dao.createTicket(passenger, dao.getJourney(journeyId), stDep, stArr, dao.getDate());
					dao.createUserAndTicket(newTicket, currentUser);
					dao.decrementEmptySeats(journeyId, stDep.getStationId(), stArr.getStationId());
					dao.debitFundsFromTheAccount(currentUser, ticketCost);
					
					String passengerDepTime = passengerDepAndDestTime_tokens[1];
					String passengerDestTime = passengerDepAndDestTime_tokens[2];
					
					
					StringBuilder ticketInfo = new StringBuilder(String.valueOf(newTicket.getTicketId()));
					ticketInfo.append(";");
					ticketInfo.append(passenger.getPassengerName());
					ticketInfo.append(";");
					ticketInfo.append(passenger.getPassengerSurname());
					ticketInfo.append(";");
					ticketInfo.append(stDep.getStationName());
					ticketInfo.append(";");
					ticketInfo.append(passengerDepTime);
					ticketInfo.append(";");
					ticketInfo.append(stArr.getStationName());
					ticketInfo.append(";");
					ticketInfo.append(passengerDestTime);
					ticketInfo.append(";");
					ticketInfo.append(cost);
					info.setTicketInfo(ticketInfo.toString());
				}
				
				
				
			}
			LOG.debug("=====================================================================");
			LOG.debug(info);
			LOG.debug("=====================================================================");
			return info;
	}
}
