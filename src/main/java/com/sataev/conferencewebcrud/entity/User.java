package com.sataev.conferencewebcrud.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sataev.conferencewebcrud.entity.enumerable.Role;
import com.sun.istack.NotNull;

@Entity
@Table(name="users")
public class User {
	@NotNull @JsonIgnore
	@Id private String username;
	
	@NotNull @JsonIgnore
	private String password;
	
	@NotNull
	private String displayingName;
	@JsonIgnore
	private Role role;
	
	@OneToMany(mappedBy = "creator", cascade = {CascadeType.ALL})
	@JsonIgnore
	private List<PresentationSchedule> createdPresentations;
	
	@ManyToMany
	@JoinTable(
			  name = "presenter_presentation", 
			  joinColumns = @JoinColumn(name = "user_id"), 
			  inverseJoinColumns = @JoinColumn(name = "presentation_id"))
	@JsonIgnore
	private List<PresentationSchedule> participatedPresentations;
	
	@ManyToMany
	@JoinTable(
			  name = "listener_presentation", 
			  joinColumns = @JoinColumn(name = "user_id"), 
			  inverseJoinColumns = @JoinColumn(name = "presentation_id"))
	@JsonIgnore
	private Set<PresentationSchedule> signUps;
		
	public User() {}
	public User(String username, String password, String displayingName, Role role) {
		this.username = username;
		this.password = password;
		this.displayingName = displayingName;
		this.role = role;
	}
	
	public User(String username, String password, String displayingName) {
		this.username = username;
		this.password = password;
		this.displayingName = displayingName;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDisplayingName() {
		return displayingName;
	}
	public void setDisplayingName(String displayingName) {
		this.displayingName = displayingName;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	public List<PresentationSchedule> getParticipatedPresentations() {
		return participatedPresentations;
	}
	public void setParticipatedPresentations(List<PresentationSchedule> participatedPresentations) {
		this.participatedPresentations = participatedPresentations;
	}
	
	public Set <PresentationSchedule> getSignUps() {
		return signUps;
	}
	public void setSignUps(Set<PresentationSchedule> signUps) {
		this.signUps = signUps;
	}
	public List<PresentationSchedule> getCreatedPresentations() {
		return createdPresentations;
	}
	public void setCreatedPresentations(List<PresentationSchedule> createdPresentations) {
		this.createdPresentations = createdPresentations;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", displayingName=" + displayingName
				+ ", role=" + role + "]";
	}
}
