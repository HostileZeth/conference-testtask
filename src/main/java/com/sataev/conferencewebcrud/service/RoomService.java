package com.sataev.conferencewebcrud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sataev.conferencewebcrud.entity.Room;
import com.sataev.conferencewebcrud.repository.RoomRepository;

@Service
public class RoomService {
	@Autowired
	private RoomRepository roomRepository;
	
	public List<Room> getRooms() { 
		return roomRepository.findAll();
	}
	
	public Room getRoom(long id) {
		return roomRepository.getOne(id);
	}
}
