package com.sataev.conferencewebcrud.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.sataev.conferencewebcrud.entity.PresentationSchedule;
import com.sataev.conferencewebcrud.entity.Room;
import com.sataev.conferencewebcrud.entity.User;
import com.sataev.conferencewebcrud.service.PresentationScheduleService;
import com.sataev.conferencewebcrud.service.RoomService;
import com.sataev.conferencewebcrud.service.UserService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PresentationScheduleControllerTests {

	@LocalServerPort private int port;

	@Autowired private MockMvc mockMvc;

	@Autowired private PresentationScheduleController presentationScheduleController;
	@Autowired private PresentationScheduleService presentationScheduleService;
	@Autowired private UserService userService;
	@Autowired private RoomService roomService;
	
	@Test
	void contextLoads() {
		assert (presentationScheduleController !=null);
		assert (presentationScheduleController !=null);
	}
	
	@Test
	@WithMockUser(username="presenter",roles={"PRESENTER"})
	@Transactional
	public void getPresentationScheduleListWithMockUser() throws Exception {
		this.mockMvc.perform( get("/presenter/presentations/")).andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(containsString("Jack")))
		.andExpect(content().string(containsString("one")));
	}
	
	@Test
	@WithMockUser(username="presenter2",roles={"PRESENTER"})
	@Transactional
	public void whenDeletePresentation_thenRecievePresentationsList() throws Exception {
		

		PresentationSchedule ps = presentationScheduleService.findById(3).orElse(null);
		
		assert(ps!=null);
		
		this.mockMvc.perform( get("/presenter/presentations/delete/3"))
			.andExpect(redirectedUrl("/presenter/presentations/")); 
		
		ps = presentationScheduleService.findById(3).orElse(null);
		
		assert(ps==null);
	}
	
	@Test
	@WithMockUser(username="listener",roles={"LISTENER"})
	@Transactional
	public void whenDeletePresentationWithNoPermission_thenForbiddenStatus() throws Exception {
		PresentationSchedule ps = presentationScheduleService.findById(3).orElse(null);		
		assert(ps!=null);
		
		this.mockMvc.perform( get("/presenter/presentations/delete/3"))
			.andExpect(status().is4xxClientError()); 
		
		ps = presentationScheduleService.findById(3).orElse(null);		
		assert(ps!=null);
	}
	
	@Test
	@WithMockUser(username="presenter2",roles={"PRESENTER"})
	@Transactional
	public void whenAddingPresentation_thenRecievePresentationsList() throws Exception {		
		User user = userService.findById("presenter2").orElse(null);
		Room room = roomService.getRoom(1);
		
		PresentationSchedule presentationSchedule = new PresentationSchedule("Sample title", user, room, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
		presentationSchedule.setRoomString(room.getRoomName());
		presentationSchedule.setCreatorString(user.getUsername());
		presentationSchedule.setPresentersString("");
		
		this.mockMvc.perform( post("/presenter/presentations/create/").flashAttr("presentationSchedule", presentationSchedule)).andExpect(redirectedUrl("/presenter/presentations/"));
		
		this.mockMvc.perform( get("/presenter/presentations/")).andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(containsString("Sample title")));
	}
}
