package com.sataev.conferencewebcrud.entity.enumerable;

public enum Role {
	LISTENER, PRESENTER, ADMIN;
	
	public String toString() {
		return name();
	}
}
