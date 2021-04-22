package com.james.beltreviewer.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.james.beltreviewer.models.User;
import com.james.beltreviewer.services.BeltReviewerService;
import com.james.beltreviewer.services.UserService;
import com.james.beltreviewer.validators.UserValidator;
import com.james.beltreviewer.models.Event;

// imports removed for brevity
@Controller
public class UsersController {
	private final UserService userService;
	@SuppressWarnings("unused")
	private final UserValidator userValidator;
	private final BeltReviewerService beltReviewerService;
    
    public UsersController(UserService userService, UserValidator userValidator, BeltReviewerService beltReviewerService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.beltReviewerService = beltReviewerService;
    }
    
    ArrayList<String> states = new ArrayList<String>(Arrays.asList("AL", "AK", "AZ", "AR", "CA", "CO", "CT",
			"DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN",
			"MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI",
			"SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"));
	
	@GetMapping("/registration")
	public String registerForm(@ModelAttribute("user") User user, Model model) {
		model.addAttribute("states", states);
		return "registrationPage.jsp";
	}
    
    @RequestMapping("/registration")
    public String registerForm(@ModelAttribute("user") User user) {
        return "registrationPage.jsp";
    }
    @RequestMapping("/login")
    public String login() {
        return "registrationPage.jsp";
    }
    
    @RequestMapping(value="/registration", method=RequestMethod.POST)
    public String registerUser(@Validated @ModelAttribute("user") User user, BindingResult result, HttpSession session, Model model) {
		if(result.hasErrors()) {
			model.addAttribute("states", states);
			return "registrationPage.jsp";
		}
    	User u = userService.registerUser(user);
    	session.setAttribute("userId", u.getId());
    	return "redirect:/home";
    }
    
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String loginUser(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session) {
		boolean isAuthenticated = userService.authenticateUser(email, password);
		if(isAuthenticated) {
			User u = userService.findByEmail(email);
			session.setAttribute("userId", u.getId());
			return "redirect:/home";
		} else {
			model.addAttribute("error", "Please use valid login credentials");
			return "loginPage.jsp";
		}
    }
    @RequestMapping("/home")
	public String dashboard(@Valid @ModelAttribute("event") Event event, BindingResult result, HttpSession session, Model model) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/registration";
		}
		User user = userService.findUserById((Long) session.getAttribute("userId"));
		model.addAttribute("user", user);
		session.setAttribute("states", states);
        List<Event> events = beltReviewerService.allEvents();
        List<Event> instate = new ArrayList<Event>();
        List<Event> outofstate = new ArrayList<Event>();
        for(Event origin: events) {
        	if(origin.getState().equals(user.getState())) {
        		instate.add(origin);
        	}
        	else {
        		outofstate.add(origin);
        	}
        }
        model.addAttribute("instate", instate);
        model.addAttribute("outofstate", outofstate);
		return "homePage.jsp";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session) {
	session.invalidate();
	return "redirect:/registration";
	}
}
