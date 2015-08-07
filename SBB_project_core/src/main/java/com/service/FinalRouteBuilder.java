package com.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.NewRouteSummary;
import com.dto.RequiredDataForNewRoute;
import com.entities.Direction;
import com.entities.Route;
import com.entities.Shedule;
import com.entities.Station;
@Service("finalRouteBuilder")
public class FinalRouteBuilder {
	private Dao dao;
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	private static final Logger LOG = Logger.getLogger(FinalRouteBuilder.class);

	@Transactional
	public  NewRouteSummary build(RequiredDataForNewRoute dto){
		LOG.debug("=====================================================================");
		LOG.debug(dto);
		LOG.debug("=====================================================================");
			List<String> newRoute = dto.getNewRoute();
			List<String> newDirections = dto.getNewDirections();
			boolean error = false;
			Route r = dao.getRouteByName(dto.getRouteName());
			if (r != null) {
				error = true;
			}
			if (!error) {
				int sumMinutes = 0;
				int sumHours = 0;
				double sumCost = 0;
				for (int i = 0; i < newDirections.size(); i++) {
					String[] stations = newDirections.get(i).split("/");
					Station s1 = dao.getStationByName(stations[0]);
					Station s2 = dao.getStationByName(stations[1]);
					int hours = Integer.parseInt(dto.getData().get(i)
							.getHours());
					int minutes = Integer.parseInt(dto.getData().get(i)
							.getMinutes());
					double cost = Double.parseDouble(dto.getData().get(i)
							.getCost());
					if (!isDirectionExist(s1.getStationId(),s2.getStationId())) {
						 dao.createDirection(s1, s2, (hours * 60 + minutes) * 60 * 1000, cost);
						
					}

				}
				r = dao.createRoute(dto.getRouteName());
				for (int i = 0; i < newRoute.size() - 1; i++) {
					Station s1 = dao.getStationByName(newRoute.get(i));
					Station s2 = dao.getStationByName(newRoute.get(i + 1));
					Direction d = dao.getDirectionByStartFinish(
							s1.getStationId(), s2.getStationId());
					sumMinutes += d.getTime() / (1000 * 60);
					sumCost += d.getCost();
					if (!isSheduleExist(d.getDirectionId(), r.getRouteId(), i)) {
						dao.createShedule(d, r,	i);
					}
				}
				sumHours = sumMinutes / 60;
				sumMinutes = sumMinutes % 60;
				List<String> routeInfo = new ArrayList<String>();
				routeInfo.add(r.getRouteName());
				routeInfo.add(String.valueOf(sumHours));
				routeInfo.add(String.valueOf(sumMinutes));
				routeInfo.add(String.valueOf(sumCost));
				for (String station : newRoute) {
					routeInfo.add(station);
				}
				LOG.debug("=====================================================================");
				LOG.debug(routeInfo);
				LOG.debug("=====================================================================");
				LOG.info("Route created");
				return new NewRouteSummary(routeInfo);
			} else {
				return new NewRouteSummary(null);
			}
	}
	private  boolean isDirectionExist(int st_dep, int st_arr){
		Direction d = dao.getDirectionByStartFinish(st_dep, st_arr);
		if (d != null) {
			return true;
		} else {
			return false;
		}

	}
	private  boolean isSheduleExist(int dirId, int routeId, int step){
		Shedule s = dao.getShedule(routeId, dirId, step);
		if (s !=null) {
			return true;
		} else {
			return false;
		}
	}
}
