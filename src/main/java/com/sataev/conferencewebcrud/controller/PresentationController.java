package com.sataev.conferencewebcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sataev.conferencewebcrud.entity.Presentation;
import com.sataev.conferencewebcrud.entity.User;
import com.sataev.conferencewebcrud.service.PresentationService;
import com.sataev.conferencewebcrud.service.UserService;

@RestController
@RequestMapping("presenter/presentations")
public class PresentationController {
	
	@Autowired private PresentationService presentationService;
	@Autowired private UserService userService;
	
	
	@RequestMapping(value = {"/"}, method = {RequestMethod.GET})
	public ModelAndView welcomePage() {
		ModelAndView model = new ModelAndView();
		model.addObject("presentations", presentationService.getPresentations());
		model.setViewName("presentation-list");
		return model;
	}
	
	@GetMapping("/add/")
    public ModelAndView showCreatePresentationForm(Presentation presentation) {
		ModelAndView model = new ModelAndView();
		
		String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(); 		
		presentation.setCreatorString(loggedInUsername);
		
		model.setViewName("add-presentation");
        return model;
    }
	
    @PostMapping("/create/")
    public ModelAndView addPresentation(Presentation presentation, BindingResult result) {
    	ModelAndView model = new ModelAndView();
    	
    	String creatorString = presentation.getCreatorString();
    	System.out.println("PRESENTATION: " + presentation);
    	
    	User creator = userService.findById(creatorString).orElse(null);
    	
    	if (creator == null) {
    		result.rejectValue("creatorString", "error.wrongCreatorString", "The creatorString is invalid, something went wrong");
    		model.setViewName("add-presentation");
            return model;
    	}
    	
    	
    	presentation.setCreator(creator);
    	presentationService.save(presentation);
    	

        model.setViewName("redirect:/presenter/presentations/");
        return model;
    }
	
}
