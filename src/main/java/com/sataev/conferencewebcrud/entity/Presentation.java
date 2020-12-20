package com.sataev.conferencewebcrud.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;

@Entity
public class Presentation {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne(mappedBy = "presentation")
	@PrimaryKeyJoinColumn
	private Schedule schedule;
	
	private String title;
	
	@ManyToMany
	@JoinTable(
			  name = "user_presentation", 
			  joinColumns = @JoinColumn(name = "presentation_id"), 
			  inverseJoinColumns = @JoinColumn(name = "user_id"))
	private	List<User> presenters;
	
	@Transient
	private String presentersString;
	@Transient
	private String creatorString;
	
	@ManyToOne
	@JoinColumn(name="creator_username", nullable = false)
	private User creator;

	public Presentation() {}
	
	public Presentation(long id, String title) {
		this.id = id;
		this.title = title;
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
	
	public List<User> getPresenters() {
		return presenters;
	}

	public void setPresenters(List<User> presenters) {
		this.presenters = presenters;
	}
	
	public Schedule getSchedule() {
		return schedule;
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}
	
	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
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

	@Override
	public String toString() {
		return "Presentation [id=" + id + ", schedule=" + schedule + ", title=" + title + ", presenters=" + presenters
				+ ", presentersString=" + presentersString + ", creatorString=" + creatorString + ", creator=" + creator
				+ "]";
	}

	
}
