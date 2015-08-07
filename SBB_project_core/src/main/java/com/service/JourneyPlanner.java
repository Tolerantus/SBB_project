package com.service;

import java.text.ParseException;
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
import com.dto.NewJourneyInfo;
import com.entities.Direction;
import com.entities.Journey;
import com.entities.Route;
import com.entities.Shedule;
import com.entities.Train;
@Service("journeyPlanner")
public class JourneyPlanner {
	private Dao dao;
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	private static final Logger LOG = Logger.getLogger(JourneyPlanner.class);

	@Transactional
	public  NewJourneyInfo plan(NewJourneyInfo dto) throws ParseException{
		LOG.debug("=====================================================================");
		LOG.debug(dto);
		LOG.debug("=====================================================================");
			String routeInfo = dto.getRouteInfo();
			String date = dto.getDate();
			String time = dto.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			if (!date.equals("") && !time.equals("")) {
				
				String[] hoursAndMinutes = time.split(":");
				Integer h = Integer.parseInt(hoursAndMinutes[0]);
				Integer m = Integer.parseInt(hoursAndMinutes[1]);
				Date choosedDate = sdf.parse(date);

				Date departureDate = new Date(choosedDate.getTime()
						+ (h * 60 + m) * 60 * 1000);
				Date currentDate = new Date();
				long tenMinutes = 10 * 60 * 1000;
				if (departureDate.getTime() - currentDate.getTime() > tenMinutes) {
					Date destinationDate = departureDate;
					Route route = dao.getRoute(Integer.parseInt(routeInfo));
					for (Shedule step : dao.getShedulesOfRoute(route.getRouteId())) {
						Direction d = (step.getDirection());
						destinationDate = new Date(destinationDate.getTime()
								+ d.getTime());
					}
					Train train = getUnusedTrainForJourney(
							dao.getRoute(Integer.parseInt(routeInfo)), departureDate);
					
					if (train == null) {
						dto.setTrainsLack(true);
						LOG.debug("=====================================================================");
						LOG.debug(dto);
						LOG.debug("=====================================================================");
						return dto;
					} else {
						Journey j = dao.createJourney(train, route, departureDate);
						List<Shedule> steps = dao.getShedulesOfRoute(route
								.getRouteId());
						for (Shedule step : steps) {
							dao.createSeats(j, step.getStep(), train.getTrainSeats());
						}
						SimpleDateFormat sdf2 = new SimpleDateFormat(
								"HH:mm   dd MMM", Locale.US);
						dto.setJourneyId(String.valueOf(j.getJourneyId()));
						dto.setRouteName(route.getRouteName());
						dto.setDate(sdf2.format(departureDate));
						dto.setTrain(String.valueOf(train.getTrainId()));
						LOG.debug("=====================================================================");
						LOG.debug(dto);
						LOG.debug("=====================================================================");
						return dto;
					}
				} else {
					LOG.debug("=====================================================================");
					LOG.debug(dto);
					LOG.debug("=====================================================================");
					return dto;
				}
			} else {
				LOG.debug("=====================================================================");
				LOG.debug(dto);
				LOG.debug("=====================================================================");
				return dto;
			}
	}
	public Train getUnusedTrainForJourney(Route route, Date departureTime) {
		long journeyDuration = 0;
		List<Shedule> steps = dao.getShedulesOfRoute(route.getRouteId());
		for (Shedule s : steps) {
			journeyDuration+=s.getDirection().getTime();
		}
		Date arrivalTime = new Date(departureTime.getTime() + journeyDuration);
		List<Journey> activeJourneys = dao.getJourneysInTimeInterval(departureTime, arrivalTime);
		List<Train> allTrains = dao.getAllTrains();
		List<Train> activeTrains = getActiveTrains(activeJourneys);
		allTrains.removeAll(activeTrains);
		List<Train> inactiveTrains = new ArrayList<Train>(allTrains);
		if (!inactiveTrains.isEmpty()) {
			return inactiveTrains.get(0);
		} else {
			return null;
		}
	}
	
	public List<Train> getActiveTrains(List<Journey> activeJourneys) {
		List<Train> activeTrains = new ArrayList<Train>();
		for (Journey j : activeJourneys) {
			Train t = j.getTrain();
			if (!activeTrains.contains(t)) {
				activeTrains.add(t);
			}
		}
		return activeTrains;
	}
}
