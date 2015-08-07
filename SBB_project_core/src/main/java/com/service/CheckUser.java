package com.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.UserExist;
import com.dto.UserInfo;
import com.entities.User;
@Service("checkUser")
public class CheckUser {
	
	private Dao dao;
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}

	@Transactional
	public UserExist check(UserInfo dto){
			String login = dto.getLogin();
			boolean isExist = false;
			boolean isAdmin = false;
			User user = dao.getUserByName(login);
			if (user != null && user.getUserPassword().equals(dto.getPassword())){
				isExist = true;
				isAdmin = user.isAccountType();
			}
			return new UserExist(isExist, isAdmin);
	}
}
