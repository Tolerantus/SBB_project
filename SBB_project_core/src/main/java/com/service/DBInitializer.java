package com.service;

import java.text.ParseException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.InitDBRequest;
@Service("DBInitializer")
public class DBInitializer {
	private Dao dao;
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	private static final Logger LOG = Logger.getLogger(DBInitializer.class);

	@Transactional
	public void init(InitDBRequest dto) throws ParseException{
		dao.initDatabase();
		LOG.info("Database initialized");
	}
}
