package com.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service("customMailSender")
public class CustomMailSender {
	private static final Logger LOG = Logger.getLogger("customMailSender");
	@Autowired
	private JavaMailSender mailSender;
		
		public void setMailSender(JavaMailSender mailSender) {
			this.mailSender = mailSender;
		}
		
		public void sendMail(String from, String to, String subject, String msg) {
	

			
			try {

	            MimeMessage message = mailSender.createMimeMessage();

	            message.setSubject(subject);
	            MimeMessageHelper helper;
	            helper = new MimeMessageHelper(message, true);
	            helper.setFrom(from);
	            helper.setTo(to);
	            helper.setText(msg, true);
	            mailSender.send(message);
	        } catch (MessagingException ex) {
	            LOG.error(ex);
	        }
			
		}
}
