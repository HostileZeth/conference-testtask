package com.sataev.conferencewebcrud.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import javax.naming.directory.InvalidAttributesException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sataev.conferencewebcrud.entity.User;
import com.sataev.conferencewebcrud.entity.enumerable.Role;

@SpringBootTest
public class UserServiceTests {
	
	@Autowired private UserService userService;
	
	@Test
	public void whenFindByName_thenReturnsUser() {
	    User testUser = new User();
	    testUser.setUsername("testuser");
	    testUser.setDisplayingName("testuser");
	    testUser.setPassword("testuser");
	    testUser.setRole(Role.PRESENTER);
	    userService.save(testUser);

		    User found = null;
			try {
				found = userService.findById("testuser").orElseThrow(() -> new InvalidAttributesException());
			} catch (InvalidAttributesException e) {
			}
			
		    String userName = found.getUsername();
		    assert(userName == "testuser");
	}
	
	@Test
	public void whenCantFindByName_thenThrowsException() {
		
		assertThrows(InvalidAttributesException.class, ()->{
			User found = userService.findById("imNotExist").orElseThrow(() -> new InvalidAttributesException());
			System.out.println(found);
		});
	}
	
	@Test
	public void whenGetUsersByRole_thenCountsUsers() {
		List<User> users = userService.findAllByRole(Role.PRESENTER);
		assert(users.size() == 3);
	}
}
