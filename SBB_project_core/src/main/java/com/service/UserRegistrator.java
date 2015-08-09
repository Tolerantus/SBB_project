package com.service;




import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.NewUserInfo;
import com.dto.UserExist;
import com.entities.User;
@Service("userRegistrator")
public class UserRegistrator {
	private Dao dao;
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
private static final Logger LOG = Logger.getLogger(UserRegistrator.class);

	@Transactional
	public   UserExist register(NewUserInfo dto){
			User oldUser = null;
			oldUser = dao.getUserByName(dto.getLogin());
			UserExist user = null;
			if (oldUser == null) {
				Set<String> role = new HashSet<String>(); role.add("ROLE_USER");
				dao.createUser(dto.getLogin(), dto.getPassword(), false, role);
				LOG.info("User created");
				user = new UserExist(false, false);
			} else {
				user = new UserExist(true, false);
			}
			return user;
	}
}
