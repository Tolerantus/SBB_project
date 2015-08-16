package com.service;





import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.NewUserInfo;
import com.dto.UserExist;
import com.entities.User;
import com.mail.MailService;
@Service("userRegistrator")
public class UserRegistrator {
	private Dao dao;
	private MailService mailService;
	@Autowired
	public void setDao(Dao dao) {
		this.dao = dao;
	}
	@Autowired
	public void setMailService(MailService mailService) {
		this.mailService = mailService;
	}

private static final Logger LOG = Logger.getLogger(UserRegistrator.class);

	@Transactional
	public UserExist register(NewUserInfo dto){
			User oldUser = null;
			oldUser = dao.getUserByName(dto.getLogin());
			UserExist user = null;
			if (oldUser == null) {
				
				User newUser = dao.createUser(dto.getLogin(), dto.getPassword(), dto.isAdmin(), null);
				mailService.sendVerificationLetter(newUser);
				LOG.info("User created");
				
				user = new UserExist(false, false);
			} else {
				user = new UserExist(true, false);
			}
			return user;
	}
}
