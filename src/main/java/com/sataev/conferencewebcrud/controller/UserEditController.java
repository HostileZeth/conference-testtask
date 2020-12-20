package com.sataev.conferencewebcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sataev.conferencewebcrud.entity.User;
import com.sataev.conferencewebcrud.service.UserService;

//@RestController
@Controller
@RequestMapping("admin/users")
public class UserEditController {
	
	@Autowired
	private UserService userService;
	
	//@RequestMapping(value = {"/"}, method = {RequestMethod.GET})
	@GetMapping("/")
	public String showUserList(Model model) {
		model.addAttribute("users", userService.findAll());
		return "user-list";
	}
	
	@GetMapping("/edit/{username}")
	public String showUpdateForm(@PathVariable("username") String username, Model model) throws Exception {
	    User user = userService.findById(username).orElseThrow(() -> new IllegalArgumentException("Invalid username:" + username));
	    model.addAttribute("user", user);
	    return "update-user";
	}
	
	@PostMapping("/update/{username}")
	public String updateUser(@PathVariable("username") String id, User user, 
	  BindingResult result, Model model) {
		
		System.out.println("USER : " + user);
	        
	    userService.save(user);
	    model.addAttribute("users", userService.findAll());
	    return "redirect:/admin/users/";
	}
}
