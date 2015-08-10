package com.mail;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.Dao;
import com.entities.User;

@Service("mailService")
public class MailService {
	
	@Autowired
	private CustomMailSender mailSender;
	@Autowired
	private Dao dao;
	
	@Transactional
	public void sendVerificationLetter(User user, boolean isAdmin) {
		 SecureRandom random = new SecureRandom();
         String key = new BigInteger(130, random).toString(32);
         dao.createUserAccessCode(user, key);
         String message = "Hello, I'm Aleksandr Klementev and I've sent you this letter to confirm you are not a fraud. "
	    		   + "Please click the link below to confirm registration in SBB-project or ignore it if you"
	    		   + " didn't make any actions.  "
	    		   + getUrl(isAdmin, key);
         
         
		 mailSender.sendMail("sbb.project.mail.sender@gmail.com",
	    		   user.getUserLogin(),
	    		   "E-mail verification", 
	    		   message);
	}
	
	public String getUrl(boolean isAdmin, String key) {
		String template = "<a href='http://localhost:8080/SBB_project_core/";
        if (isAdmin) {
        	return template + "adminVerification/" + key + "'>Verificate!</a>";
        } else {
        	return template + "userVerification/" + key + "'>Verificate!</a>";
        }
	}
}
