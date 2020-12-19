package com.sataev.conferencewebcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sataev.conferencewebcrud.service.RoomService;

@RestController
@RequestMapping("confidential/room/")
public class RoomController {

	@Autowired
	private RoomService roomService;
	
	
	/*private List<Room> generateTestRooms() {
		/*List<Room> roomList = new ArrayList<Room>();
		roomList.add(new Room(1, "test1", "test1"));
		roomList.add(new Room(2, "test2", "test2"));
		roomList.add(new Room(3, "test3", "test3"));
		
		
		return roomService.getRooms();
	}*/
	
	//it works
	@RequestMapping(value = {"/list"}, method = {RequestMethod.GET})
	public ModelAndView welcomePage() {
		ModelAndView model = new ModelAndView();
		model.addObject("rooms", roomService.getRooms());
		model.setViewName("room-list");
		return model;
	}
	
}
