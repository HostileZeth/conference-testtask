package com.sataev.conferencewebcrud.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sataev.conferencewebcrud.entity.Room;
import com.sataev.conferencewebcrud.repository.RoomRepository;

@Service
public class RoomService {
	@Autowired
	private RoomRepository roomRepository;
	
	public List<Room> findAll() { 
		return roomRepository.findAll();
	}
	
	public Room getRoom(long id) {
		return roomRepository.getOne(id);
	}

	public Room findRoomByName(String name) {
		List<Room> resultingRoom = roomRepository.findAllByRoomName(name);
		
		if (resultingRoom.size() == 1) return resultingRoom.get(0);
			else
				throw new EntityNotFoundException("Entity with name "+ name + "not found.");
				
	}
}
