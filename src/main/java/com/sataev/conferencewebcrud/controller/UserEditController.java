package com.sataev.conferencewebcrud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserEditController {

	@RequestMapping("/index")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	@RequestMapping(value = {"/", "/helloworld**"}, method = {RequestMethod.GET})
	public ModelAndView welcomePage() {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Tutorial");
		model.addObject("message", "Welcome Page !");
		model.setViewName("helloworld");
		return model;
	}
	
}
