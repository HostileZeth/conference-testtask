package com.sataev.conferencewebcrud.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Room {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private long id;
	private String roomName;
	private String description;
	
	public Room() {}
	
	public Room(long id, String name, String description) {
		this.id = id;
		this.roomName = name;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String name) {
		this.roomName = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
}
