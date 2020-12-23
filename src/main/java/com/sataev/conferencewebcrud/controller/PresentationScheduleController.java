package com.sataev.conferencewebcrud.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sataev.conferencewebcrud.entity.PresentationSchedule;
import com.sataev.conferencewebcrud.entity.Room;
import com.sataev.conferencewebcrud.entity.User;
import com.sataev.conferencewebcrud.entity.enumerable.Role;
import com.sataev.conferencewebcrud.service.PresentationScheduleService;
import com.sataev.conferencewebcrud.service.RoomService;
import com.sataev.conferencewebcrud.service.UserService;

@Controller
@RequestMapping("presenter/presentations")
public class PresentationScheduleController {
	
	@Autowired private PresentationScheduleService presentationScheduleService;
	@Autowired private UserService userService;
	@Autowired private RoomService roomService;
	
	@GetMapping("/")
	public ModelAndView showPresentationsList() {
		ModelAndView model = new ModelAndView();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User currentUser = userService.findById(username).orElseThrow(()->new IllegalArgumentException("Error. Wrong username " + username ));

		model.addObject("presentationSchedules", currentUser.getCreatedPresentations());
		model.setViewName("presentation-schedule-list");
		return model;
	}
	
	@GetMapping("/add/")
    public ModelAndView showCreatePresentationForm(PresentationSchedule presentationSchedule) {
		ModelAndView model = new ModelAndView();
		String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(); 
		presentationSchedule.setCreatorString(loggedInUsername);
		addAvailablePresentersAndRoomsToModel(model);	
		model.setViewName("add-presentation-schedule");
        return model;
    }
	
    @PostMapping("/create/")
    public ModelAndView addPresentation(@ModelAttribute("presentationSchedule") PresentationSchedule presentationSchedule,
    		BindingResult result) {
    	ModelAndView model = processPresentationSchedule(presentationSchedule, result);
        return model;
    }
    
    @GetMapping("/edit/{id}")													// wow it works
    public ModelAndView editPresentationSchedule(@PathVariable("id") long id, ModelAndView model, BindingResult result) {
    	PresentationSchedule presentationSchedule = presentationScheduleService.findById(id).orElse(null);
    	
    	presentationSchedule.setRoomString( presentationSchedule.getRoom().getRoomName() );
    	presentationSchedule.setCreatorString( presentationSchedule.getCreator().getUsername() ); // maybe not
    	
    	String presentersString = presentationSchedule.getPresenters().stream().map(User::getUsername).collect(Collectors.joining(" "));
    	presentationSchedule.setPresentersString(presentersString);
    	
    	List<User> presenterList = userService.findAllByRole(Role.PRESENTER);
    	presenterList.removeIf(x -> x.getUsername().equals( presentationSchedule.getCreator().getUsername() ));
    	System.out.println(presenterList);
    	
    	// validating begin - end - room data
    	Room room = presentationSchedule.getRoom();
    	
    	LocalDateTime begin = presentationSchedule.getPresentationBegin();
    	LocalDateTime end = presentationSchedule.getPresentationEnd();
    	
    	if (!(begin.compareTo(end) < 0)) {
    		result.rejectValue("presentationBegin", "error.wrongDate", "PresentationBegin should be earlier, than PresentationEnd");
    		addAvailablePresentersAndRoomsToModel(model);
    		model.setViewName("add-presentation-schedule");
            return model;
    	}
    	
    	if (!(presentationScheduleService.isRoomAvailable(room, begin, end, presentationSchedule.getId()))) {
    		result.rejectValue("presentationEnd", "error.wrongDate", "Presentation time should not overlap with other presentations in the same room ");
    		addAvailablePresentersAndRoomsToModel(model);
    		model.setViewName("add-presentation-schedule");
            return model;
    	}
    	
    	model.addObject("rooms",roomService.findAll());
    	model.addObject("availablePresenters",presenterList);
    	model.addObject("presentationSchedule", presentationSchedule);
    	model.setViewName("add-presentation-schedule");
        return model;
    }
    
	@GetMapping("/delete/{id}")
	public ModelAndView deletePresentationSchedule(@PathVariable("id") long id, ModelAndView model) throws Exception {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User currentUser = userService.findById(username).orElseThrow(()->new IllegalArgumentException("Error. Wrong username " + username ));
		
		PresentationSchedule presentationSchedule = presentationScheduleService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id:" + id));
		presentationScheduleService.delete(presentationSchedule);
		model.addObject("presentationSchedules", currentUser.getCreatedPresentations());
		model.setViewName("redirect:/presenter/presentations/");
		
		return model;
	}
        
    private ModelAndView processPresentationSchedule(PresentationSchedule presentationSchedule, BindingResult result) {
    	ModelAndView model = new ModelAndView();
    	String creatorString = presentationSchedule.getCreatorString();
    	User creator = userService.findById(creatorString).orElse(null);
    	
    	if (creator == null) {
    		result.rejectValue("creatorString", "error.wrongCreatorString", "The creatorString is invalid, something went wrong");
    		addAvailablePresentersAndRoomsToModel(model);
    		model.setViewName("add-presentation-schedule");
            return model;
    	}
    	
    	presentationSchedule.setCreator(creator);
    	Room room = null;
    	try {
    		room = roomService.findRoomByName(presentationSchedule.getRoomString());
    	} catch (EntityNotFoundException e) {
    		
    		result.rejectValue("roomName", "error.wrongRoomName", "No room with name " + presentationSchedule.getRoomString());
    		model.setViewName("add-presentation-schedule");
    		addAvailablePresentersAndRoomsToModel(model);
            return model;
    	}
    	
    	LocalDateTime begin = presentationSchedule.getPresentationBegin();
    	LocalDateTime end = presentationSchedule.getPresentationEnd();
    	
    	if (!(begin.compareTo(end) < 0)) {
    		result.rejectValue("presentationBegin", "error.wrongDate", "PresentationBegin should be earlier, than PresentationEnd");
    		model.setViewName("add-presentation-schedule");
    		addAvailablePresentersAndRoomsToModel(model);
            return model;
    	}
    	
    	if (!(presentationScheduleService.isRoomAvailable(room, begin, end, presentationSchedule.getId()))) {
    		result.rejectValue("presentationEnd", "error.wrongDate", "Presentation time should not overlap with other presentations in the same room ");
    		model.setViewName("add-presentation-schedule");
    		addAvailablePresentersAndRoomsToModel(model);
            return model;
    	}
    	
    	presentationSchedule.setRoom(room);

    	List<User> supportingPresentersList = new ArrayList<User>();
    	if (!presentationSchedule.getPresentersString().equals("")) {
    		String[] supportingPresentersUsernameArray = presentationSchedule.getPresentersString().split(" ");
    	
	    	for(String s : supportingPresentersUsernameArray) {
		    		if (s.equals(creatorString)) {
		    	    	result.rejectValue("presentersString", "error.wrongPresentersString", "You should not put creator in supporting presenters list");
		    	    	model.setViewName("add-presentation-schedule");
		    	    	addAvailablePresentersAndRoomsToModel(model);
		    	        return model;
		    		}
		    		User supportingPresenter = userService.findById(s).orElse(null);
		    		if (supportingPresenter == null) {
		    	    	result.rejectValue("presentersString", "error.wrongPresentersString", "No user with username " + s);
		    	    	model.setViewName("add-presentation-schedule");
		    	    	addAvailablePresentersAndRoomsToModel(model);
		    	        return model;
		    	    }
		    		if (!supportingPresenter.getRole().equals(Role.PRESENTER)) {
		    	    	result.rejectValue("presentersString", "error.wrongPresentersString", "User with username " + s + " isn't a PRESENTER");
		    	    	model.setViewName("add-presentation-schedule");
		    	    	addAvailablePresentersAndRoomsToModel(model);
		    	        return model;
		    		}
		    		supportingPresentersList.add(supportingPresenter);
	    		}
	    	presentationSchedule.setPresenters(supportingPresentersList);
    	}
    	
    	presentationScheduleService.save(presentationSchedule);
        model.setViewName("redirect:/presenter/presentations/");
        return model;
    }
	
    private void addAvailablePresentersAndRoomsToModel(ModelAndView model) {
		String loggedInUsername = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString(); 
    	List<User> presenterList = userService.findAllByRole(Role.PRESENTER);
    	presenterList.removeIf(x -> x.getUsername().equals( loggedInUsername ));
    	model.addObject("availablePresenters",presenterList);
    	model.addObject("rooms",roomService.findAll());
    }
}
