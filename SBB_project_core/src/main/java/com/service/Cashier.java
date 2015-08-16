package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.MoneyPutRequest;
import com.entities.User;

@Service("cashier")
public class Cashier {
	
	private Dao dao;
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	@Transactional
	public Double getCash(String userName) {
		User user = dao.getUserByName(userName);
		return user.getCash();
	}
	@Transactional
	public void putMoney(MoneyPutRequest dto) {
		User user = dao.getUserByName(dto.getUserName());
		double cash = Double.parseDouble(dto.getCash());
		dao.addFundsToTheAccount(user, cash);
	}
	
}
