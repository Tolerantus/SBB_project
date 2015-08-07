package com.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.AllJourneysInfo;
import com.entities.Journey;
@Service("journeyBriefer")
public class JourneyBriefer {
	private Dao dao;
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	private static final Logger LOG = Logger.getLogger(JourneyBriefer.class);

	@Transactional
	public  AllJourneysInfo getInfo(AllJourneysInfo dto){
		LOG.debug("=====================================================================");
		LOG.debug(dto);
		LOG.debug("=====================================================================");
		List<Journey> journeys = dao.getAllJourneys();
		List<String> journeyNames = new ArrayList<String>();
		if (!journeys.isEmpty()) {
			for (Journey j : journeys) {
				journeyNames.add(j.getJourneyId() + " "
						+ (j.getRoute()).getRouteName());
			}
			dto.setJourneys(journeyNames);
		}
		LOG.debug("=====================================================================");
		LOG.debug(dto);
		LOG.debug("=====================================================================");
		return dto;
	}
}
