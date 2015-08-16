package com.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.NewStationInfo;
import com.entities.Station;
@Service("stationCreator")
public class StationCreator {
	private Dao dao;
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	private static final Logger LOG = Logger.getLogger(StationCreator.class);

	@Transactional
	public   NewStationInfo create(NewStationInfo dto){

		LOG.debug("=====================================================================");
		LOG.debug(dto);
		LOG.debug("=====================================================================");
			String stationName = dto.getStation();
			if (stationName == null) {
				throw new RuntimeException();
			} else {
				boolean isExist = false;
				Station s = dao.getStationByName(stationName);
				if (s != null) {
					isExist = true;
				}
				if (!isExist) {
					dao.createStation(stationName);
					LOG.info("Station created");
				} else {
					dto.setExist(true);
				}
				LOG.debug("=====================================================================");
				LOG.debug(dto);
				LOG.debug("=====================================================================");
				return dto;
			}
	}
}
