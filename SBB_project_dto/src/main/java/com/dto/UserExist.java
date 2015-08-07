package com.dto;

public class UserExist {
private boolean isExist;
private boolean isAdmin;

public UserExist() {
	super();
}
public UserExist(boolean isExist, boolean isAdmin) {
	super();
	this.isExist = isExist;
	this.isAdmin = isAdmin;
}
public boolean isExist() {
	return isExist;
}
public void setExist(boolean isExist) {
	this.isExist = isExist;
}
public boolean isAdmin() {
	return isAdmin;
}
public void setAdmin(boolean isAdmin) {
	this.isAdmin = isAdmin;
}
@Override
public String toString() {
	return "UserExist [isExist=" + isExist + ", isAdmin=" + isAdmin + "]";
}



}
