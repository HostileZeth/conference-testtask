package com.sataev.conferencewebcrud.service;

import java.util.List;
import java.util.Optional;

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

	public Optional<Room> findRoomByName(String name) {
		
		List<Room> resultingRoom = roomRepository.findAllByRoomName(name);
		
		if (resultingRoom.size() == 1) return Optional.ofNullable(resultingRoom.get(0));
			else
				return Optional.empty();
	}
}
