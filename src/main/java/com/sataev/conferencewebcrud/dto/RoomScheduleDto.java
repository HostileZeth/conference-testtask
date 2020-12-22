package com.sataev.conferencewebcrud.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sataev.conferencewebcrud.entity.PresentationSchedule;
import com.sataev.conferencewebcrud.entity.Room;

public class RoomScheduleDto {
	
	/*class PresentationScheduleSignUp {
		PresentationSchedule presentationSchedule;
		Boolean isSignedUp;
	}*/
	
	private Room room;
	private List<PresentationSchedule> presentationScheduleList;
	
	@JsonIgnore
	private List<Boolean> isSignedIn;
	
	public RoomScheduleDto() {}
	
	public RoomScheduleDto(Room room, List<PresentationSchedule> presentationScheduleList) {
		this.room = room;
		this.presentationScheduleList = presentationScheduleList;
	}
	
	public RoomScheduleDto(Room room, List<PresentationSchedule> presentationScheduleList, List<Boolean> isSignedIn) {
		this.room = room;
		this.presentationScheduleList = presentationScheduleList;
		this.isSignedIn = isSignedIn;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public List<PresentationSchedule> getPresentationScheduleList() {
		return presentationScheduleList;
	}

	public void setPresentationScheduleList(List<PresentationSchedule> presentationScheduleList) {
		this.presentationScheduleList = presentationScheduleList;
	}

	public List<Boolean> getIsSignedIn() {
		return isSignedIn;
	}

	public void setIsSignedIn(List<Boolean> isSignedIn) {
		this.isSignedIn = isSignedIn;
	}
}
