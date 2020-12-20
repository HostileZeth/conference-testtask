package com.sataev.conferencewebcrud.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class Schedule {
	
	@Id
    private long id;
	
	@OneToOne
	@MapsId
	@JoinColumn(name="presentation_id")
	private Presentation presentation;
	
	@ManyToOne
	@JoinColumn(name="room_id", nullable = false)
	private Room room;
	
	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime presentationBegin;
	
	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime presentationEnd;

	public Schedule() {}

	public Schedule(long id, Presentation presentation, Room room, LocalDateTime presentationBegin,
			LocalDateTime presentationEnd) {
		this.id = id;
		this.presentation = presentation;
		this.room = room;
		this.presentationBegin = presentationBegin;
		this.presentationEnd = presentationEnd;
	}



	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Presentation getPresentation() {
		return presentation;
	}

	public void setPresentation(Presentation presentation) {
		this.presentation = presentation;
	}
}
