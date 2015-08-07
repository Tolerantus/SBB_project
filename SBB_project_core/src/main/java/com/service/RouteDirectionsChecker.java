package com.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.DirectionData;
import com.dto.RouteStationList;
import com.entities.Direction;
import com.entities.Station;
@Service("routeDirectionsChecker")
public class RouteDirectionsChecker {
	private Dao dao;
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	private static final Logger LOG = Logger.getLogger(RouteDirectionsChecker.class);

	@Transactional
	public   DirectionData check(RouteStationList dto){
		LOG.debug("=====================================================================");
		LOG.debug(dto);
		LOG.debug("=====================================================================");
			List<String> newRoute = dto.getRoute();
			List<String> directionData = new ArrayList<String>();
			for (int i = 0; i < newRoute.size() - 1; i++) {
				Station s1 = dao.getStationByName(newRoute.get(i));
				Station s2 = dao.getStationByName(newRoute.get(i + 1));
				boolean isDirectionNew = true;
				Direction candidateForNewDirection = dao.getDirectionByStartFinish(s1.getStationId(), s2.getStationId());
				if (candidateForNewDirection != null)
					isDirectionNew = false;
				
				if (isDirectionNew) {
					StringBuilder sb = new StringBuilder(s1.getStationName());
					sb.append("/");
					sb.append(s2.getStationName());
					directionData.add(sb.toString());
				}
			}
			DirectionData d = new DirectionData(directionData);
			LOG.debug("=====================================================================");
			LOG.debug(dto);
			LOG.debug("=====================================================================");
			return d;

	}
}
