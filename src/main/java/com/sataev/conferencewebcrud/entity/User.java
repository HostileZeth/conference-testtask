package com.sataev.conferencewebcrud.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.sataev.conferencewebcrud.entity.enumerable.Role;

@Entity
@Table(name="users")
public class User {
	
	@Id private String username;
	private String password;
	private String displayingName;
	private Role role;
	
	@ManyToMany
	@JoinTable(
			  name = "user_presentation", 
			  joinColumns = @JoinColumn(name = "user_id"), 
			  inverseJoinColumns = @JoinColumn(name = "presentation_id"))
	private List<Presentation> presentations;
		
	public User() {}
	public User(String username, String password, String displayingName, Role role) {
		this.username = username;
		this.password = password;
		this.displayingName = displayingName;
		this.role = role;
	}
	
	public List<Presentation> getPresentations() {
		return presentations;
	}
	public void setPresentations(List<Presentation> presentations) {
		this.presentations = presentations;
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
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", displayingName=" + displayingName
				+ ", role=" + role + "]";
	}
}
