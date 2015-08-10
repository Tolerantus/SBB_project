package com.mail;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.dto.CodeRole;
import com.entities.User;
import com.entities.UserRole;

@Service("userActivator")
public class UserActivator {
	@Autowired
	private Dao dao;
	@Transactional
	public CodeRole activateUser(CodeRole dto) {
		User user = dao.getUserByAccessCode(dto.getUserAccessCode().getCode());
		if (user != null) {
			Set<UserRole> roles = new HashSet<UserRole>();
			if (dto.isAdmin()) {
				UserRole userRole = dao.getRoleByString("ROLE_ADMIN");
				roles.add(userRole);	
				dao.setUserAs(user, "ROLE_ADMIN");
			} else {
				UserRole userRole = dao.getRoleByString("ROLE_USER");
				roles.add(userRole);
				dao.setUserAs(user, "ROLE_USER");
			}
			dao.deleteUserAccessCode(user);
		}
		dto.getUserAccessCode().setUser(user);
		return dto;
	}
}
