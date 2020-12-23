package com.sataev.conferencewebcrud.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sataev.conferencewebcrud.entity.Room;

@SpringBootTest
class RoomServiceTests {

	@Autowired private RoomService roomService;
	
	@Test
	void contextLoads() {
		assert(roomService!=null);
	}
	
	@Test
	@Transactional
	void whenFindById_thenGetRoomName() {
		Room room = roomService.getRoom(1);
		String roomName = room.getRoomName();
		assert(roomName.equals("404"));
	}
	
	@Test
	@Transactional
	void whenFindById_throwException() {
		assertThrows(EntityNotFoundException.class, () -> {
			Room room = roomService.getRoom(Long.MAX_VALUE);
			System.out.println(room);
		});
	}
}
