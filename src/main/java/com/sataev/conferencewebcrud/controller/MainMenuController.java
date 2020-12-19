package com.sataev.conferencewebcrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sataev.conferencewebcrud.entity.User;
import com.sataev.conferencewebcrud.entity.enumerable.Role;
import com.sataev.conferencewebcrud.service.UserService;

//@RestController
@RestController
public class MainMenuController {
	
	@Autowired private UserService userService;
	@Autowired private PasswordEncoder passwordEncoder;
	//@Autowired private InMemoryUserDetailsManager inMemoryUserDetailsManager;

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
    public ModelAndView addUser(User user, BindingResult result) {
        
    	ModelAndView model = new ModelAndView();
		
    	if (result.hasErrors()) {
    		model.setViewName("add-user");
            return model;
        }
        
    	user.setRole(Role.LISTENER);
    	
    	String password = user.getPassword();
    	
    	user.setPassword(passwordEncoder.encode(password));
    	
    	System.out.println(password);
    	System.out.println(user.getPassword());
    	
    	userService.save(user);
    	
    	//user doesn't actually put into context
    	
        //model.addAttribute("users", userService.findAll());
        model.setViewName("redirect:/index");
        return model;
    }
	

	@RequestMapping(value = {"/", "/helloworld"}, method = {RequestMethod.GET})
	public ModelAndView welcomePage() {
		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Tutorial");
		model.addObject("message", "Welcome Page !");
		model.setViewName("helloworld");
		return model;
	}
	
	@RequestMapping(value = {"presenter/nothing"}, method = {RequestMethod.GET})
	public ModelAndView protectedPage() {
		ModelAndView model = new ModelAndView();
		//model.addObject("title", "Spring Security Tutorial");
		//model.addObject("message", "Welcome Page !");
		model.setViewName("nothing");
		return model;
	}
	
	@RequestMapping(value = {"/forbidden"}, method = {RequestMethod.GET})
	public ModelAndView forbiddenAccess() {
		ModelAndView model = new ModelAndView();
		//model.addObject("title", "Spring Security Tutorial");
		//model.addObject("message", "Welcome Page !");
		model.setViewName("forbidden");
		return model;
	}
}
