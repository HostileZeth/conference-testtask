package com.sataev.conferencewebcrud.service;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sataev.conferencewebcrud.entity.PresentationSchedule;
import com.sataev.conferencewebcrud.entity.Room;
import com.sataev.conferencewebcrud.entity.User;

@SpringBootTest
public class PresentationScheduleServiceTests {
	
	@Autowired
	private PresentationScheduleService presentationScheduleService;
	
	@Autowired 
	private RoomService roomService;
	
	@Autowired 
	private UserService userService;
	
	@Test
	void whenFindById_thenGetPresentationScheduleCount() {
		List<PresentationSchedule> presentationScheduleList = presentationScheduleService.getPresentationSchedules();
		assert(presentationScheduleList.size() == 4);
	}
	
	@Test
	void whenSavePresentation_thenGetPresentation() {	
		User user = userService.findById("presenter").orElse(null);
		Room room = roomService.getRoom(1);
		
		PresentationSchedule presentationSchedule = new PresentationSchedule("Sample title", user, room, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
		
		presentationScheduleService.save(presentationSchedule);
		
		long id = presentationSchedule.getId();
		
		PresentationSchedule received = presentationScheduleService.findById(id).orElse(null);
		
		assert(received.getTitle().equals("Sample title"));
	}
	
	@Test
	void whenCheckingDate_thenRoomNotAvailable() {	
		
		Room room = roomService.getRoom(1);
		
		LocalDateTime begin = LocalDateTime.now().minusYears(10);
		LocalDateTime end = LocalDateTime.now();
		
		boolean checkResult = presentationScheduleService.isRoomAvailable(room, begin, end, Long.MAX_VALUE);

		assert(checkResult == false);
	}
	
	@Test
	void whenCheckingDate_thenRoomNotAvailable2() {	
		
		Room room = roomService.getRoom(1);
		
		LocalDateTime begin = LocalDateTime.of(2020, 10, 10, 8, 10); // in default DB fill 404 room is occupied in this time
		LocalDateTime end = LocalDateTime.of(2020, 10, 10, 9, 50); 
		
		boolean checkResult = presentationScheduleService.isRoomAvailable(room, begin, end, Long.MAX_VALUE);

		assert(checkResult == false);
	}
	
	@Test
	void whenCheckingDate_thenRoomAvailable() {	
		
		Room room = roomService.getRoom(1);
		
		LocalDateTime begin = LocalDateTime.of(2010, 01, 01, 1, 00);
		LocalDateTime end = LocalDateTime.of(2010, 01, 01, 2, 00);
		
		boolean checkResult = presentationScheduleService.isRoomAvailable(room, begin, end, Long.MAX_VALUE);

		assert(checkResult == true);
	}
	
}
