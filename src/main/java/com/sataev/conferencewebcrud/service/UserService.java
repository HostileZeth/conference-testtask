package com.sataev.conferencewebcrud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sataev.conferencewebcrud.entity.User;
import com.sataev.conferencewebcrud.entity.enumerable.Role;
import com.sataev.conferencewebcrud.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public Optional<User> findById(String id) {
		return userRepository.findById(id);
	}
	
	public List<User> findAllByRole(Role role){
		return userRepository.findAllByRole(role);
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
	
	public void delete(User user) {
		userRepository.delete(user);
	}
}
