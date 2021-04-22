package com.james.beltreviewer.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.james.beltreviewer.models.Event;
import com.james.beltreviewer.models.Message;
import com.james.beltreviewer.models.User;
import com.james.beltreviewer.services.BeltReviewerService;
import com.james.beltreviewer.services.UserService;



@Controller
public class BeltReviewerController {
	private final BeltReviewerService beltReviewerService;
	private final UserService userService;


	public BeltReviewerController(BeltReviewerService beltReviewerService, UserService userService) {
		this.beltReviewerService = beltReviewerService;
		this.userService = userService;

	}
	
	ArrayList<String> states = new ArrayList<String>(Arrays.asList("AL", "AK", "AZ", "AR", "CA", "CO", "CT",
			"DE", "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN",
			"MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI",
			"SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"));

	@RequestMapping("/events")
	public String dashboard(@Valid @ModelAttribute("event") Event event, BindingResult result, HttpSession session, Model model) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/registration";
		}
		User user = beltReviewerService.findUserById((Long) session.getAttribute("userId"));
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
	
	@RequestMapping("/events/{id}")
	public String viewEvent(@PathVariable("id") Long id, @Valid @ModelAttribute("messageobj") Message message, BindingResult result, Model model, HttpSession session) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/home";
		}
		User user = userService.findUserById((Long) session.getAttribute("userId"));
		Event event = beltReviewerService.findEventById(id);
		List<Message> messages = event.getMessages();
		Collections.reverse(messages);
		model.addAttribute("event", event);
		model.addAttribute("user", user);
		model.addAttribute("attendees", event.getJoinedUsers());
		model.addAttribute("messages", messages);
		return "details.jsp";
	}

	@GetMapping("/events/{id}/edit")
	public String editPage(@PathVariable("id") Long id, @ModelAttribute("eventobj") Event event, Model model, HttpSession session) {
		if(session.getAttribute("userId") == null) {
			return "redirect:/";
		}
		User user = beltReviewerService.findUserById((Long)session.getAttribute("userId"));
		if(beltReviewerService.findEventById(id).getUser().getId() == user.getId()) {
			model.addAttribute("event", beltReviewerService.findEventById(id));
			return "edit.jsp";
		}
		else {
			return "redirect:/";
		}
	}

	//CRUD
	@PostMapping("/addevent")
	public String addEvent(@Valid @ModelAttribute("event") Event event, BindingResult result, HttpSession session) {
		if(result.hasErrors()) {
			return "homePage.jsp";
		}
		else {
			beltReviewerService.addEvent(event);
			return "redirect:/home";	
		}	
	}
	
	@PostMapping("/events/{id}/edit")
	public String editEvent(@PathVariable("id") Long eventId, Model model, HttpSession session, @Valid @ModelAttribute("event") Event editEvent, BindingResult result) {
		Long userId = (Long) session.getAttribute("userId");
		@SuppressWarnings("unused")
		User user = beltReviewerService.findUserById(userId);
		Event event = beltReviewerService.findEventById(eventId);
		if(result.hasErrors()) {
			model.addAttribute("date", event.getDate());
			model.addAttribute("event", event);
			model.addAttribute("states", states);
			return "editEventPage.jsp";
		}else {
			event.setDate(editEvent.getDate());
			event.setName(editEvent.getName());
			event.setLocation(editEvent.getLocation());
			event.setState(editEvent.getState());
			beltReviewerService.updateEvent(event);
			return "redirect:/events";
		}
	}
	
	@RequestMapping("/events/{id}/join")
	public String joinEvent(@PathVariable("id") Long id, HttpSession session) {
		User user = beltReviewerService.findUserById((Long) session.getAttribute("userId"));
		Event event = beltReviewerService.findEventById(id);
		List<User> attendees = event.getJoinedUsers();
		attendees.add(user);
		event.setJoinedUsers(attendees);
		beltReviewerService.updateUser(user);	
		return "redirect:/events";
	}
	
    @RequestMapping("/events/{id}/cancel")
    public String cancelEvent(@PathVariable("id") Long id, HttpSession session) {
    	User user = beltReviewerService.findUserById((Long) session.getAttribute("userId"));
		Event event = beltReviewerService.findEventById(id);
    	List<User> attendees = event.getJoinedUsers();
        for(int i=0; i<attendees.size(); i++) {
            if(attendees.get(i).getId() == user.getId()) {
            	attendees.remove(i);
            }
        }
        event.setJoinedUsers(attendees);
        beltReviewerService.updateUser(user);
    	return "redirect:/events";
    }
    
    @RequestMapping("/events/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
    	Event event = beltReviewerService.findEventById(id);
    	for(User user: event.getJoinedUsers()) {
    		List<Event> myevents = user.getJoinedevents();
    		myevents.remove(event);
    		user.setJoinedevents(myevents);;
    		beltReviewerService.updateUser(user);
    	}

    	beltReviewerService.deleteEvent(id);
    	return "redirect:/events";
    }
	
	@PostMapping("events/addmsg")
	public String addMessage(@ModelAttribute("messageobj") Message message, Model model, HttpSession session) {
		User user = beltReviewerService.findUserById((Long) session.getAttribute("userId"));
		model.addAttribute("user", user);
		beltReviewerService.newMessage(message);
		return "redirect:/events";
		
	}
	
}