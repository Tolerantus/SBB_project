package com.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.ResetRequest;
@Service("resetter")
public class Resetter {
	private Dao dao;
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	private static final Logger LOG = Logger.getLogger(Resetter.class);

	@Transactional
	public  void reset(ResetRequest dto){
		dao.clearStations();
		dao.clearTrains();
		dao.clearRoutes();
		dao.clearPassengers();
		dao.initUsers();
		LOG.info("==========================================DB reset========================================");
	}
}
