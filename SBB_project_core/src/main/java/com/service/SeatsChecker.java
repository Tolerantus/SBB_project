package com.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.ChoosedJourney;
import com.entities.Journey;
import com.entities.Seats;
@Service("seatsChecker")
public class SeatsChecker {
	private Dao dao;
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
private static final Logger LOG = Logger.getLogger(SeatsChecker.class);

	@Transactional
	public   ChoosedJourney check(ChoosedJourney dto){
		LOG.debug("=====================================================================");
		LOG.debug(dto);
		LOG.debug("=====================================================================");
			String journeyId = dto.getJourneyId();
			Journey j = dao.getJourney(Integer.parseInt(journeyId));
			List<String> journeys = dto.getJourneys();
			String journeyData = "";
			for (String js : journeys) {
				if (js.startsWith(journeyId)) {
					journeyData = js;
					break;
				}
			}
			String[] tokens = journeyData.split(";");
			int depStep = Integer.parseInt(tokens[1]);
			int arrStep = Integer.parseInt(tokens[2]);
			List<Seats> seats = dao.getSeatsOnJourney(j.getJourneyId());
			if (!seats.isEmpty()) {
				int emptySeats = j.getTrain().getTrainSeats();
				for (Seats s : seats) {
					if (s.getRouteStep() >= depStep
							&& s.getRouteStep() <= arrStep) {
						emptySeats = Math.min(emptySeats, s.getEmptySeats());
					}
				}
				dto.setJourneyId(journeyData);
				dto.setEmptySeats(emptySeats);
			}
			LOG.debug("=====================================================================");
			LOG.debug(dto);
			LOG.debug("=====================================================================");
			return dto;
	}
}
