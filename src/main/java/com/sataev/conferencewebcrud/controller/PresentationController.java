package com.sataev.conferencewebcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sataev.conferencewebcrud.service.PresentationService;

@RestController
@RequestMapping("protected/presentation")
public class PresentationController {
	
	@Autowired
	private PresentationService presentationService;
	
	@RequestMapping(value = {"/list"}, method = {RequestMethod.GET})
	public ModelAndView welcomePage() {
		ModelAndView model = new ModelAndView();
		model.addObject("presentations", presentationService.getPresentations());
		model.setViewName("presentation-list");
		return model;
	}
}
