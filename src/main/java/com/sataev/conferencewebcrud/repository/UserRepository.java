package com.sataev.conferencewebcrud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sataev.conferencewebcrud.entity.User;
import com.sataev.conferencewebcrud.entity.enumerable.Role;

public interface UserRepository extends JpaRepository<User, String> {

	public List<User> findAllByRole(Role role);
	
}
