package com.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.JourneysInfo;
import com.dto.StationsForSheduling;
import com.entities.Direction;
import com.entities.Journey;
import com.entities.Route;
import com.entities.Shedule;
import com.entities.Station;
@Service("getAppropriateJourneys")
public class GetAppropriateJourneys {
	private Dao dao;
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	private static final Logger LOG = Logger.getLogger(GetAppropriateJourneys.class);

	@Transactional
public  JourneysInfo getJourneys(StationsForSheduling dto) throws ParseException{
		LOG.debug("=====================================================================");
		LOG.debug(dto);
		LOG.debug("=====================================================================");
		String station = dto.getSingleStation();
		String stDep = dto.getStDep();
		String stArr = dto.getStArr();
		String date = dto.getDate();
		Station stationForSimpleShedule = null;
		if (station != null) {
			stationForSimpleShedule = dao.getStationByName(station);
			JourneysInfo info = simpleSheduling(stationForSimpleShedule);
			LOG.debug("=====================================================================");
			LOG.debug(info);
			LOG.debug("=====================================================================");
			return info;
		} else {
			JourneysInfo info = getAppropriateJourneys(dao.getStationByName(stDep), dao.getStationByName(stArr), date);
			LOG.debug("=====================================================================");
			LOG.debug(info);
			LOG.debug("=====================================================================");
			return info;
		}
}
public JourneysInfo simpleSheduling(Station station) {
	
	List<Route> appropriateRoutes = dao.getRoutesContainStation(station.getStationId());
	Iterator<Route> iterator = appropriateRoutes.iterator();
	while (iterator.hasNext()) {
		Route r = iterator.next();
		List<Shedule> steps = dao.getShedulesOfRoute(r.getRouteId());
		Station lastStationOnRoute = steps.get(steps.size()-1).getDirection().getStArr();
		if (station.equals(lastStationOnRoute)) {
			iterator.remove();
		}
	}
	JourneysInfo journeysInfo = new JourneysInfo(null, null);
	List<Journey> appropriateJourneys = dao.getJourneysByRoutes(appropriateRoutes);
	SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm  dd MMM", Locale.US);
	
	if (!appropriateJourneys.isEmpty()){
		
		List<String> journeyStringData = new ArrayList<String>();
		for (Journey j : appropriateJourneys){
			String id = String.valueOf(j.getJourneyId());
			Date departure = j.getTimeDep();
			Station routeBeginning = null;
			Station routeEnding = null;
			List<Shedule> steps = dao.getShedulesOfRoute(j.getRoute().getRouteId());
			int depStep = 0;
			int lastStep = 0;
			for (Shedule step : steps){
				Direction d = (step.getDirection());
				if (step.getStep()==0){
					routeBeginning = (d.getStDep());
				}
				lastStep = Math.max(lastStep, step.getStep());
				if (lastStep==step.getStep()){
					routeEnding = (d.getStArr());
				}
				if (d.getStDep().getStationId() == station.getStationId()){
					depStep=step.getStep();
				}
			}
			for (Shedule step : steps){
				Direction d = (step.getDirection());
				if (step.getStep()<depStep){
					departure = new Date(departure.getTime() + d.getTime());
				}
			}
			Date currentTime = new Date();
			if (currentTime.before(departure)){
				StringBuilder journeyData = new StringBuilder(id);
				journeyData.append(";");
				journeyData.append(sdf1.format(departure));
				journeyData.append(";");
				journeyData.append(routeBeginning.getStationName());
				journeyData.append(";");
				journeyData.append(routeEnding.getStationName());
				journeyStringData.add(journeyData.toString());
			}
		}
		
		if (journeyStringData.size()!=0)
			journeysInfo.setJourneyStringData(journeyStringData);
			
		return journeysInfo;
	}else{
		return journeysInfo;
	}
}

public  JourneysInfo getAppropriateJourneys(Station stDep, Station stArr, String date) throws ParseException{
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm dd MMM", Locale.US);
	JourneysInfo journeysInfo = new JourneysInfo(null, null);
	List<Route> appropriateRoutes = dao.getRoutesContainStations(stDep.getStationId(), stArr.getStationId());
	List<Journey> appropriateJourneys = dao.getJourneysByRoutes(appropriateRoutes);
	List<String> helpInfo = new ArrayList<String>();
	Iterator<Journey> iterator = appropriateJourneys.iterator();
	
	while (iterator.hasNext()) {
		Journey j = iterator.next();
		List<Shedule> steps = new ArrayList<Shedule>();
		steps.addAll(dao.getShedulesOfRoute(j.getRoute().getRouteId()));
		double departurePoint = 0;
		double arrivalPoint = 0;
		for (Shedule step : steps){
			Direction direction = (step.getDirection());
			if (direction.getStDep().equals(stDep)){
				departurePoint = step.getStep();
			}
			if (direction.getStArr().equals(stDep)){
				departurePoint = step.getStep() + 1;
			}
			
			if (direction.getStDep().equals(stArr)){
				arrivalPoint = step.getStep();
			}
			if (direction.getStArr().equals(stArr)){
				arrivalPoint = step.getStep() + 1;
			}
		}
		long tenMinutes = 10*60*1000;
		if (departurePoint >= arrivalPoint || (j.getTimeDep().getTime()-(new Date()).getTime()) <= tenMinutes){
			iterator.remove();
		}
	}
		
		if (!appropriateJourneys.isEmpty()){
			
			List<String> journeyStringData = new ArrayList<String>();
			for (Journey j : appropriateJourneys){
				String id = String.valueOf(j.getJourneyId());
				Date departure = j.getTimeDep();
				Date arrival = j.getTimeDep();
				
				List<Shedule> steps = dao.getShedulesOfRoute(j.getRoute().getRouteId());
				int depStep = 0;
				int arrStep = 0;
				double cost = 0;
				for (Shedule step : steps){
					Direction d = (step.getDirection());
					if (d.getStDep().equals(stDep)){
						depStep = step.getStep();
					}
					if (d.getStArr().equals(stArr)){
						arrStep = step.getStep();
					}
				}
				for (Shedule step : steps){
					Direction d = (step.getDirection());
					if (step.getStep()<depStep){
						departure = new Date(departure.getTime() + d.getTime());
						arrival = new Date(arrival.getTime() + d.getTime());
					}
					if (step.getStep() >= depStep && step.getStep() <= arrStep){
						arrival = new Date(arrival.getTime() + d.getTime());
						cost +=d.getCost();
					}
				}
				if (!date.equals("")){
					Date targetedDateBegin = sdf.parse(date);
					Date targetedDateEnd = new Date(targetedDateBegin.getTime() + 24*60*60*1000);
					
					if (departure.getTime() >= targetedDateBegin.getTime() &&
							departure.getTime() <= targetedDateEnd.getTime()){
						StringBuilder journeyData = new StringBuilder(id);
						journeyData.append(";");
						journeyData.append(sdf1.format(departure));
						journeyData.append(";");
						journeyData.append(sdf1.format(arrival));
						journeyData.append(";");
						journeyData.append(cost);
						journeyStringData.add(journeyData.toString());
						
						StringBuilder info = new StringBuilder(id);
						info.append(";");
						info.append(depStep);
						info.append(";");
						info.append(arrStep);
						info.append(";");
						info.append(stDep.getStationId());
						info.append(";");
						info.append(stArr.getStationId());
						helpInfo.add(info.toString());
					}
				} else {
					StringBuilder journeyData = new StringBuilder(id);
					journeyData.append(";");
					journeyData.append(sdf1.format(departure));
					journeyData.append(";");
					journeyData.append(sdf1.format(arrival));
					journeyData.append(";");
					journeyData.append(cost);
					journeyStringData.add(journeyData.toString());
					
					StringBuilder info = new StringBuilder(id);
					info.append(";");
					info.append(depStep);
					info.append(";");
					info.append(arrStep);
					info.append(";");
					info.append(stDep.getStationId());
					info.append(";");
					info.append(stArr.getStationId());
					helpInfo.add(info.toString());
				}
			}
			if (journeyStringData.size() != 0)
			journeysInfo.setJourneyStringData(journeyStringData);
			
			if (helpInfo.size() != 0)
			journeysInfo.setJourneyHelpInfo(helpInfo);
			return journeysInfo;
		} else {
			return journeysInfo;
		}
		
}
}
