package com.sataev.conferencewebcrud.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.sataev.conferencewebcrud.entity.User;
import com.sataev.conferencewebcrud.service.UserService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserEditControllerTests {

	@LocalServerPort private int port;
	@Autowired private MockMvc mockMvc;

	@Autowired private UserEditController userController;
	@Autowired private UserService userService;
	
	@Test
	void contextLoads() {
		assert (userController !=null);
		assert (userService !=null);
	}
	
	@Test
	@WithMockUser(username="mock-admin",roles={"ADMIN"})
	public void getUserListWithMockUserCustomUser() throws Exception {
		this.mockMvc.perform( get("/admin/users/")).andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(containsString("John")))
		.andExpect(content().string(containsString("ADMIN")));
	}
	
	@Test
	@WithMockUser(username="admin",roles={"ADMIN"})
	@Transactional
	public void whenDeleteUser_thenUserDeleted() throws Exception {
		User user = userService.findById("presenter2").orElse(null);		
		assert(user!=null);
		
		this.mockMvc.perform( get("/admin/users/delete/presenter2"))
			.andExpect(redirectedUrl("/admin/users/")); 
		
		user = userService.findById("presenter2").orElse(null);	
		assert(user==null);
	}
	
	@Test
	@WithMockUser(username="mock-user",roles={"LISTENER"})
	@Transactional
	public void whenDeleteUserWithNoPermission_thenForbiddenStatus() throws Exception {
		User user = userService.findById("presenter2").orElse(null);		
		assert(user!=null);
		
		this.mockMvc.perform( get("/admin/users/delete/presenter2"))
			.andExpect(status().is4xxClientError()); 
		
		user = userService.findById("presenter2").orElse(null);	
		assert(user!=null);
	}
}
