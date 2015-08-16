package com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.JourneyAndPassengers;
import com.entities.Journey;
import com.entities.Ticket;
@Service("passengerInformator")
public class PassengersInformator {
	private Dao dao;
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	private static final Logger LOG = Logger.getLogger(PassengersInformator.class);

	@Transactional
	public  JourneyAndPassengers getInfo(JourneyAndPassengers dto){

		LOG.debug("=====================================================================");
		LOG.debug(dto);
		LOG.debug("=====================================================================");
			String journeyInfo = dto.getJourneyInfo();
			String[] tokens = journeyInfo.split(";");
			Journey j = dao.getJourney(Integer.parseInt(tokens[0].substring(1)));
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
			List<String> passInfo = new ArrayList<String>();
			
			for (Ticket t : dao.getTicketsOfJourney(j)) {
					StringBuilder sb = new StringBuilder();
					sb.append(t.getTicketId()); separate(sb);
					sb.append(t.getStDep().getStationName()); separate(sb);
					sb.append(t.getStArr().getStationName()); separate(sb);
					sb.append(sdf1.format(t.getPurchaseDate())); separate(sb);
					sb.append(t.getPassenger().getPassengerName()); separate(sb);
					sb.append(t.getPassenger().getPassengerSurname()); separate(sb);
					sb.append(sdf.format(t.getPassenger().getPassengerBirthday()));
					passInfo.add(sb.toString());
			}
			
				dto.setPassInfo(passInfo);
				LOG.debug("=====================================================================");
				LOG.debug(dto);
				LOG.debug("=====================================================================");
				return dto;
	}
	public void separate(StringBuilder sb) {
		sb.append(";");
	}
}
