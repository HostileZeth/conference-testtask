package com.sataev.conferencewebcrud;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sataev.conferencewebcrud.entity.Room;
import com.sataev.conferencewebcrud.service.RoomService;

@SpringBootTest
class ConferenceWebCrudApplicationTests {

	@Autowired private RoomService roomService;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	@Transactional
	void testRoomsService() {
		Room room = roomService.getRoom(1);
		String roomName = room.getRoomName();
		assert(roomName.equals("404"));
	}
}
