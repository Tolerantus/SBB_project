package com.mail;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.CodeRole;
import com.entities.User;

@Service("userActivator")
public class UserActivator {
	@Autowired
	private Dao dao;
	@Transactional
	public CodeRole activateUser(CodeRole dto) {
		User user = dao.getUserByAccessCode(dto.getUserAccessCode().getCode());
		if (user != null) {
			if (dto.isAdmin()) {
				dao.setUserAs(user, "ROLE_ADMIN");
			} else {
				dao.setUserAs(user, "ROLE_USER");
			}
			dao.deleteUserAccessCode(user);
		}
		dto.getUserAccessCode().setUser(user);
		return dto;
	}
}
