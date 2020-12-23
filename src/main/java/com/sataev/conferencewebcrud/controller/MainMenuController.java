package com.sataev.conferencewebcrud.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sataev.conferencewebcrud.dto.RoomScheduleDto;
import com.sataev.conferencewebcrud.entity.PresentationSchedule;
import com.sataev.conferencewebcrud.entity.User;
import com.sataev.conferencewebcrud.entity.enumerable.Role;
import com.sataev.conferencewebcrud.service.PresentationScheduleService;
import com.sataev.conferencewebcrud.service.UserService;

@RestController
public class MainMenuController {
	
	@Autowired private UserService userService;
	@Autowired private PresentationScheduleService presentationScheduleService;
	@Autowired private PasswordEncoder passwordEncoder;

	@RequestMapping("/index")
	public String index() {
		return "Greetings from Spring Boot!";
	}
	
	@GetMapping("/signup")
	public ModelAndView showSignUpForm(User user) {
		ModelAndView model = new ModelAndView();
		model.setViewName("add-user");
        return model;
    }
    
    @PostMapping("/adduser")
    public ModelAndView addUser(@ModelAttribute("user") User user, BindingResult result) {
    	ModelAndView model = new ModelAndView();
    	
    	if (user.getUsername().isEmpty())
    		result.rejectValue("username", "error.wrongUsername", "Username cannot be empty");
    	if (user.getDisplayingName().isEmpty())
    		result.rejectValue("displayingName", "error.wrongDisplayingName", "Displaying name cannot be empty");
    	if (result.hasErrors()) {
    		model.setViewName("add-user");
            return model;
        }
        
    	user.setRole(Role.LISTENER);
    	String password = user.getPassword();
    	user.setPassword(passwordEncoder.encode(password));
    	userService.save(user);
        model.setViewName("redirect:/");
        return model;
    }
	
	@GetMapping({"/", "/helloworld"})
	public ModelAndView welcomePage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("main-menu");
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User currentUser = userService.findById(username).orElse(null);
		Set<PresentationSchedule> userPresentationSignUps = null;
		
		int presentationsCounter = 0; 
		model.addObject("message", "Main Menu ! Check page bottom for hints");
		if (!username.equals("anonymousUser")) {
			userPresentationSignUps = currentUser.getSignUps();
			presentationsCounter = userPresentationSignUps.size();
			if (currentUser.getRole().equals(Role.LISTENER))
				model.addObject("title", "Welcome. You have " + presentationsCounter + " presentations to visit. (only listeners see this info)");
			else
				model.addObject("title", "Welcome.");
			} else {
			model.addObject("title", "Welcome.");
		}
		
		List<PresentationSchedule> orderedList = presentationScheduleService.findAllByOrderByRoomAscPresentationBeginAsc();
		if (orderedList.size() == 0) return model;
		
		List<RoomScheduleDto> roomSchedule = generateRoomScheduleDtos(userPresentationSignUps, orderedList);
		List<String> usernames = userService.getUsernames();
		
		model.addObject("usernames", usernames);
		model.addObject("roomScheduleDtos", roomSchedule);
		return model;
	}

	//generates schedule for each room (main menu and JSON output)
	private List<RoomScheduleDto> generateRoomScheduleDtos(Set<PresentationSchedule> userPresentationSignUps, List<PresentationSchedule> orderedList) {
		Iterator<PresentationSchedule> presentationScheduleIterator = orderedList.iterator();
		List<RoomScheduleDto> roomSchedule = new ArrayList<>();
		PresentationSchedule current = presentationScheduleIterator.next();
		RoomScheduleDto roomScheduleDto = new RoomScheduleDto(current.getRoom(), new ArrayList<PresentationSchedule>(Arrays.asList(current)));
		
		while (presentationScheduleIterator.hasNext()) {
			current = presentationScheduleIterator.next();
			if (current.getRoom().getId() == roomScheduleDto.getRoom().getId())
				roomScheduleDto.getPresentationScheduleList().add(current);
			else {				
				if (userPresentationSignUps!=null)
				{
					roomScheduleDto.setIsSignedIn(new ArrayList<Boolean>());
					for (PresentationSchedule ps : roomScheduleDto.getPresentationScheduleList()) {
						if (userPresentationSignUps.contains(ps))
							ps.setIsSignedUp(true);
						else 
							ps.setIsSignedUp(false);
					}
				}
				roomSchedule.add(roomScheduleDto);
				roomScheduleDto = new RoomScheduleDto(current.getRoom(), new ArrayList<PresentationSchedule>(Arrays.asList(current)));
			}
		}
		
		if (userPresentationSignUps!=null)
		{
			roomScheduleDto.setIsSignedIn(new ArrayList<Boolean>());
			for (PresentationSchedule ps : roomScheduleDto.getPresentationScheduleList()) {
				if (userPresentationSignUps.contains(ps))
					ps.setIsSignedUp(true);
				else 
					ps.setIsSignedUp(false);
			}
		}
		
		roomSchedule.add(roomScheduleDto);
		return roomSchedule;
	}
	
	@GetMapping("/cancel/{id}")
	public ModelAndView cancelPresentationSubscription(@PathVariable("id") long id, ModelAndView model) {
	    PresentationSchedule presentationSchedule = presentationScheduleService.findById(id)
	    		.orElseThrow(() -> new IllegalArgumentException("Invalid presentation Id:" + id));
	    
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User currentUser = userService.findById(username).orElse(null);
		
		presentationSchedule.removeListener(currentUser);
		presentationScheduleService.save(presentationSchedule);
		
	    model.setViewName("redirect:/");
	    return model;
	}
	
	@GetMapping("/join/{id}")
	public ModelAndView joinPresentation(@PathVariable("id") long id, ModelAndView model) {		
	    PresentationSchedule presentationSchedule = presentationScheduleService.findById(id)
	    		.orElseThrow(() -> new IllegalArgumentException("Invalid presentation Id:" + id));
	    
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User currentUser = userService.findById(username).orElse(null);
		
		presentationSchedule.addListener(currentUser);
		presentationScheduleService.save(presentationSchedule);
			    
	    model.setViewName("redirect:/");
	    return model;
	}
	
	@RequestMapping(value = {"/nothing", "presenter/nothing"}, method = {RequestMethod.GET})
	public ModelAndView nothingHere() {
		ModelAndView model = new ModelAndView();
		model.setViewName("nothing");
		return model;
	}
	
	@RequestMapping(value = {"/forbidden"}, method = {RequestMethod.GET})
	public ModelAndView forbiddenAccess() {
		ModelAndView model = new ModelAndView();
		model.setViewName("forbidden");
		return model;
	}
	
	@RequestMapping(value = {"/api/v1/schedule/"}, method = {RequestMethod.GET})
	public List<RoomScheduleDto> getScheduleJson() {
		List<RoomScheduleDto> x = generateRoomScheduleDtos(null, presentationScheduleService.findAllByOrderByRoomAscPresentationBeginAsc());
		return x;
	}
}
