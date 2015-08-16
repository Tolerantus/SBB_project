package com.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.FilteredJourneysInfo;
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
	public  FilteredJourneysInfo getInfo(FilteredJourneysInfo dto) throws ParseException{
		LOG.debug("=====================================================================");
		LOG.debug(dto);
		LOG.debug("=====================================================================");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		Date stopDate = null;
		String start = dto.getStart(); if (start != null && !start.equals("")){
			startDate = sdf1.parse(start);
		} 
		String stop = dto.getStop(); if (stop != null && !stop.equals("")){
			stopDate = sdf1.parse(stop);
		} 
		List<Journey> journeys = dao.getJourneysBetween(startDate, stopDate);
		List<String> journeyNames = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
		if (!journeys.isEmpty()) {
			for (Journey j : journeys) {
				journeyNames.add("#" + j.getJourneyId() + "; route "
						+ (j.getRoute()).getRouteName() + "; department " + sdf.format(j.getTimeDep()) + "; train #" + j.getTrain().getTrainId());
			}
			dto.setJourneys(journeyNames);
		}
		LOG.debug("=====================================================================");
		LOG.debug(dto);
		LOG.debug("=====================================================================");
		return dto;
	}
}
