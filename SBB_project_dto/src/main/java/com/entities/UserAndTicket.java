package com.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;



/**
 * Entity implementation class for Entity: User_Tickets
 *
 */
@Entity
@XmlRootElement
public class UserAndTicket implements Serializable {
	@Id	
	@JoinColumn(name = "ticket_id", referencedColumnName = "ticket_id")
	@OneToOne
	private Ticket ticket;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private User user;
	
	private static final long serialVersionUID = 1L;

	
	public UserAndTicket() {
		super();
	}


	public UserAndTicket(Ticket ticket, User user) {
		super();
		this.ticket = ticket;
		this.user = user;
	}


	public Ticket getTicket() {
		return ticket;
	}


	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ticket == null) ? 0 : ticket.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAndTicket other = (UserAndTicket) obj;
		if (ticket == null) {
			if (other.ticket != null)
				return false;
		} else if (!ticket.equals(other.ticket))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
}
