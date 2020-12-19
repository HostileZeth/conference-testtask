package com.sataev.conferencewebcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sataev.conferencewebcrud.entity.User;
import com.sataev.conferencewebcrud.service.UserService;

//@RestController
@RestController
@RequestMapping("admin/users")
public class UserEditController {
	
	@Autowired
	private UserService userService;
	
	//@RequestMapping(value = {"/"}, method = {RequestMethod.GET})
	@GetMapping("/")
	public ModelAndView userPage() {
		ModelAndView model = new ModelAndView();
		model.addObject("users", userService.findAll());
		model.setViewName("user-list");
		return model;
	}
	
	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) throws Exception {
	    User user = userService.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
	    model.addAttribute("user", user);
	    return "update-user";
	}
	
	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") long id, User user, 
	  BindingResult result, Model model) {
	    
		/*if (result.hasErrors()) {
	        user.setId(id);
	        return "update-user";
	    }*/
	        
	    userService.save(user);
	    model.addAttribute("users", userService.findAll());
	    return "redirect:/";
	}
}
