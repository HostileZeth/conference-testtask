package com.sataev.conferencewebcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sataev.conferencewebcrud.service.RoomService;

@RestController
@RequestMapping("admin/rooms/")
public class RoomController {
	@Autowired private RoomService roomService;	
	
	@GetMapping("/")
	public ModelAndView welcomePage() {
		ModelAndView model = new ModelAndView();
		model.addObject("rooms", roomService.findAll());
		model.setViewName("room-list");
		return model;
	}
}
