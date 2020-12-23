package com.sataev.conferencewebcrud.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="presentation")
@SecondaryTable(name="schedule", pkJoinColumns = @PrimaryKeyJoinColumn(name="presentation_id"))
public class PresentationSchedule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	@JsonIgnore
	private long id;
	
	@Column(name="title")
	private String title;
	
	@ManyToOne
	@JoinColumn(name="creator_username", nullable = false)
	private User creator;
	
	@ManyToMany
	@JoinTable(
			  name = "presenter_presentation", 
			  joinColumns = @JoinColumn(name = "presentation_id"), 
			  inverseJoinColumns = @JoinColumn(name = "user_id"))
	private	List<User> presenters;
	
	@ManyToMany
	@JoinTable(
			  name = "listener_presentation", 
			  joinColumns = @JoinColumn(name = "presentation_id"), 
			  inverseJoinColumns = @JoinColumn(name = "user_id"))
	@JsonIgnore
	private Set<User> listeners;
	
	@ManyToOne
	@JoinColumn(name="room_id", table="schedule", nullable = false)
	private Room room;
	
	@Column(columnDefinition = "TIMESTAMP", table="schedule")
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime presentationBegin;
	
	@Column(columnDefinition = "TIMESTAMP", table="schedule")
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private LocalDateTime presentationEnd;
	
	@Transient @JsonIgnore
	private String presentersString;
	@Transient @JsonIgnore
	private String creatorString;
	@Transient @JsonIgnore
	private String roomString;
	@Transient @JsonIgnore
	private boolean isSignedUp;

	public PresentationSchedule() {}
	
	public PresentationSchedule(String title, User creator, Room room, LocalDateTime presentationBegin,
			LocalDateTime presentationEnd) {
		this.title = title;
		this.creator = creator;
		this.room = room;
		this.presentationBegin = presentationBegin;
		this.presentationEnd = presentationEnd;
	}
	
	public void addListener(User user) {
		if (listeners == null) 
			listeners = new HashSet<User>();
		
		listeners.add(user);
	}
	
	public void removeListener(User user) {
		listeners.remove(user);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public LocalDateTime getPresentationBegin() {
		return presentationBegin;
	}

	public void setPresentationBegin(LocalDateTime presentationBegin) {
		this.presentationBegin = presentationBegin;
	}

	public LocalDateTime getPresentationEnd() {
		return presentationEnd;
	}

	public void setPresentationEnd(LocalDateTime presentationEnd) {
		this.presentationEnd = presentationEnd;
	}

	public String getPresentersString() {
		return presentersString;
	}

	public void setPresentersString(String presentersString) {
		this.presentersString = presentersString;
	}

	public String getCreatorString() {
		return creatorString;
	}

	public void setCreatorString(String creatorString) {
		this.creatorString = creatorString;
	}

	public String getRoomString() {
		return roomString;
	}

	public void setRoomString(String roomString) {
		this.roomString = roomString;
	}

	public List<User> getPresenters() {
		return presenters;
	}

	public void setPresenters(List<User> presenters) {
		this.presenters = presenters;
	}
	
	public Set<User> getListeners() {
		return listeners;
	}

	public void setListeners(Set<User> listeners) {
		this.listeners = listeners;
	}

	public boolean getIsSignedUp() {
		return isSignedUp;
	}

	public void setIsSignedUp(boolean isSignedUp) {
		this.isSignedUp = isSignedUp;
	}

	@Override
	public String toString() {
		return "PresentationSchedule [id=" + id + ", title=" + title + ", creator=" + creator + ", presenters="
				+ presenters + ", listeners=" + listeners + ", room=" + room + ", presentationBegin="
				+ presentationBegin + ", presentationEnd=" + presentationEnd + ", presentersString=" + presentersString
				+ ", creatorString=" + creatorString + ", roomString=" + roomString + "]";
	}
	
	@Override
	public int hashCode() {
		return (int) (id % Integer.MAX_VALUE);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o.getClass() != PresentationSchedule.class)
			return false;
		
		PresentationSchedule ps = (PresentationSchedule) o;
		return ps.id == this.id;
	}
}
