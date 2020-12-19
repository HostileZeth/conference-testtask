package com.sataev.conferencewebcrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sataev.conferencewebcrud.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

}
