package com.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.RouteStationList;
import com.dto.StationForInsertInRoute;
import com.entities.Station;
@Service("routeStationsInjector")
public class RouteStationInjector {
	private Dao dao;
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	private static final Logger LOG = Logger.getLogger(RouteStationInjector.class);

	@Transactional
	public   RouteStationList inject(StationForInsertInRoute dto){
		LOG.debug("=====================================================================");
		LOG.debug(dto);
		LOG.debug("=====================================================================");
			String newStation = dto.getNewStation();
			String selectedStation = dto.getSelectedStation();
			int step = Integer.parseInt(dto.getStep());
			RouteStationList route = dto.getRoute();
			boolean isNewStationNew = true;
			if (newStation.equals("")) {
				Station s = dao.getStationByName(newStation);
				if (s != null) {
					isNewStationNew = false;
				}
			}
			Station stationForInsert = null;
			if (newStation.equals("")) {
				stationForInsert = dao.getStationByName(selectedStation);
			} else {
				if (isNewStationNew) {
					stationForInsert = dao.createStation(newStation);
				} else {
					stationForInsert = dao.getStationByName(newStation);
				}
			}
			List<String> newRoute = route.getRoute();
			if (!newRoute.contains(stationForInsert.getStationName())) {
				newRoute.add(step + 1, stationForInsert.getStationName());
			}
			route.setRoute(newRoute);
			LOG.debug("=====================================================================");
			LOG.debug(route);
			LOG.debug("=====================================================================");
			return route;
	}
}
