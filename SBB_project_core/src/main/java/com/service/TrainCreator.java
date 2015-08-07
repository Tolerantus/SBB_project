package com.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.NewTrainInfo;
@Service("trainCreator")
public class TrainCreator {
	private Dao dao;
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	private static final Logger LOG = Logger.getLogger(TrainCreator.class);

	@Transactional
	public   void create(NewTrainInfo dto){
		LOG.debug("=====================================================================");
		LOG.debug(dto);
		LOG.debug("=====================================================================");
		dao.createTrain(Integer.parseInt(dto.getTrainCapacity()));
	}
}
