package com.service;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.NewRouteStartAndFinish;
import com.dto.RouteStationList;
import com.entities.Station;
@Service("routeCreator")
public class RouteCreator {
	private Dao dao;
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	private static final Logger LOG = Logger.getLogger(RouteCreator.class);

	@Transactional
	public  RouteStationList append(NewRouteStartAndFinish dto){

		LOG.debug("=====================================================================");
		LOG.debug(dto);
		LOG.debug("=====================================================================");
			RouteStationList routeStationList = new RouteStationList(null);
			String typedDep = dto.getTypedDep();
			String typedArr = dto.getTypedArr();
			String selectDep = dto.getSelectDep();
			String selectArr = dto.getSelectArr();
			List<Station> allStations = dao.getAllStations();
			if (allStations == null && typedDep.equals("") && typedArr.equals("")) {
				return routeStationList;
			} else {

				boolean isStDepExist = false;
				boolean isStArrExist = false;

				if (!typedDep.equals("")) {
					Station station = dao.getStationByName(typedDep);
					if (station != null)
						isStDepExist = true;
				}
				if (!typedArr.equals("")) {
					Station station = dao.getStationByName(typedArr);
					if (station != null)
						isStArrExist = true;
				}

				Station start = null;
				Station finish = null;
				if (typedArr.equals("")) {
					finish = dao.getStationByName(selectArr);
				} else {
					if (!isStArrExist) {
						finish = dao.createStation(typedArr);
					} else {
						finish = dao.getStationByName(typedArr);
					}
				}

				if (typedDep.equals("")) {
					start = dao.getStationByName(selectDep);
				} else {
					if (!isStDepExist) {
						start = dao.createStation(typedDep);
					} else {
						start = dao.getStationByName(typedDep);
					}
				}

				List<String> route = new LinkedList<String>();
				route.add(start.getStationName());
				route.add(finish.getStationName());
				routeStationList.setRoute(route);
				LOG.debug("=====================================================================");
				LOG.debug(routeStationList);
				LOG.debug("=====================================================================");
				return routeStationList;
			}
	}
}
