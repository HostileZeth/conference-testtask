package com.sataev.conferencewebcrud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sataev.conferencewebcrud.entity.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

	public List<Room> findAllByRoomName(String name);
	
}