package com.entities;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Route
 *
 */
@Entity

public class Route implements Serializable {
@Id
@GeneratedValue (strategy=GenerationType.AUTO)
@Column(name = "route_id")
private int routeId;

@Column(name = "route_name")
private String routeName;
	


public int getRouteId() {
	return routeId;
}

public void setRouteId(int routeId) {
	this.routeId = routeId;
}

public String getRouteName() {
	return routeName;
}

public void setRouteName(String routeName) {
	this.routeName = routeName;
}

@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + routeId;
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
		Route other = (Route) obj;
		if (routeId != other.routeId)
			return false;
		return true;
	}




	public Route(int routeId, String routeName) {
		super();
		this.routeId = routeId;
		this.routeName = routeName;
	}




	private static final long serialVersionUID = 1L;

	public Route() {
		super();
	}
   
}
