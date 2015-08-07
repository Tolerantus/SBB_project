package com.service;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.cdi.bean.LoginBean;
import com.dto.UserExist;
import com.dto.UserInfo;



/**
 * Session Bean implementation class User
 */
@Named
@RequestScoped
public class UserValidator {
	@Inject
	private LoginBean login;
	/*@Inject
	private FacesContext facesContext;*/
	
    /**
     * Default constructor. 
     */
    public UserValidator() {
    }
    
    public String validate() {
    	UserExist userExist = getServerResponse(new UserInfo(login.getEmail(), login.getPassword()));
    	login.setAdmin(userExist.isAdmin());
    	login.setStatus(getMessage(userExist));
    	return getNextPage(userExist);
    }
    public UserExist getServerResponse(UserInfo info) {
    	ResteasyClient client = new ResteasyClientBuilder().build();
    	String url = Url.USER.getUrl() + 
    	    	info.getLogin() + "/" + info.getPassword();
		ResteasyWebTarget target = client.target(url);
		Response response = target.request().get();
		return response.readEntity(UserExist.class);
    }
    public String getMessage(UserExist userExist) {
    	String briefMessage;
		String detailMessage;
		String role = null;
		if (userExist.isExist()) {
			briefMessage = "Ok!";
			detailMessage = "You are successfully logged in!";
			if (userExist.isAdmin()) {
				role = "You have a right to request tickets information.";
			} else {
				role = "You don't have a right to request tickets information.";
			}
		} else {
			briefMessage = "Fail!";
			detailMessage = "User with such personal data does not exist!";
		}
		return briefMessage + "\n" + detailMessage + (role != null ? role : "");
    }
    
    public String getNextPage(UserExist userExist) {
    	if (userExist.isExist() && userExist.isAdmin()) {
    		return "TicketWindow";
    	} else {
    		return "Login";
    	}
    }

}
