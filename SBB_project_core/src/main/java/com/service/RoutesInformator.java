package com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.AllRoutesInfo;
import com.entities.Direction;
import com.entities.Route;
import com.entities.Shedule;
import com.entities.Station;
@Service("routesInformator")
public class RoutesInformator {
	private Dao dao;
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	private static final Logger LOG = Logger.getLogger(RoutesInformator.class);

	@Transactional
	public   AllRoutesInfo getInfo(AllRoutesInfo dto){

		LOG.debug("=====================================================================");
		LOG.debug(dto);
		LOG.debug("=====================================================================");
			Map<Integer, String> routes = new HashMap<Integer, String>();
			if (!dao.getAllRoutes().isEmpty()) {
				for (Route r : dao.getAllRoutes()) {
					List<Shedule> steps = dao.getShedulesOfRoute(r.getRouteId());
					StringBuilder routeStationsInfo = new StringBuilder();
					for (int i = 0; i < steps.size(); i++) {
						Direction d = (steps.get(i).getDirection());
						Station dep = (d.getStDep());
						routeStationsInfo.append(dep.getStationName());
						routeStationsInfo.append("--->");
					}
					Direction d = (steps.get(steps.size() - 1)
							.getDirection());
					Station arr = (d.getStArr());
					routeStationsInfo.append(arr.getStationName());

					routes.put(r.getRouteId(), routeStationsInfo.toString());
				}
				dto.setRoutes(routes);
			}
			LOG.debug("=====================================================================");
			LOG.debug(dto);
			LOG.debug("=====================================================================");
			return dto;
	}
}
