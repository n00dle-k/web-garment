package com.garderob.web_garderob.controllers;

import com.garderob.web_garderob.dto.NewFavouriteDto;
import com.garderob.web_garderob.models.Event;
import com.garderob.web_garderob.models.FavoriteLook;
import com.garderob.web_garderob.services.AccountService;
import com.garderob.web_garderob.services.EventService;
import com.garderob.web_garderob.services.GarmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EventController extends ControllerBase {

    private final EventService eventService;
    private final GarmentService garmentsService;

    @Autowired
    protected EventController(
            EventService eventService,
            AccountService accountService,
            GarmentService garmentService) {
        super(accountService);
        this.eventService = eventService;
        this.garmentsService = garmentService;
    }

    @GetMapping("/events")
    public String getEventsPage(Authentication authentication, Model model) {
        final var account = getAccount(authentication);
        model.addAttribute("account", account);
        model.addAttribute("events", account.getEvents());
        model.addAttribute("look", new FavoriteLook());
        return "/events";
    }

    @GetMapping("/events/add")
    public String getAddEventPage(Authentication authentication, Model model) {
        final var account = getAccount(authentication);
        model.addAttribute("account", account);
        model.addAttribute("event", new Event());
        return "/events/add";
    }

    @PostMapping("/events/add")
    public String addNewEvent(
            Authentication authentication,
            Model model,
            @ModelAttribute("event") Event newEvent) {
        final var account = getAccount(authentication);
        model.addAttribute("account", account);
        eventService.saveNewEvent(newEvent, account);
        return "redirect:/events";
    }

    @GetMapping("/events/edit/{id}")
    public String getEditEventPage(
            Authentication authentication,
            @PathVariable("id") long id,
            Model model) {
        final var account = getAccount(authentication);
        model.addAttribute("account", account);
        model.addAttribute("event", eventService.findById(id).orElseThrow());
        return "/events/edit";
    }

    @PostMapping("/events/edit/{id}")
    public String editEvent(
            Authentication authentication,
            @ModelAttribute("events") Event updatedEventState) {
        eventService.updateEvent(updatedEventState, getAccount(authentication));
        return "redirect:/events";
    }

    @GetMapping("/events/delete/{id}")
    public String deleteTag(
            @PathVariable("id") long id
    ) {
        eventService.deleteById(id);
        return "redirect:/events";
    }

    @GetMapping("/events/process/{id}")
    public String processEvent(
            @PathVariable("id") long id,
            Model model,
            Authentication authentication
    ) {
        model.addAttribute("looks", garmentsService.getGarmentsForEventId(id));
        model.addAttribute("newLook", new NewFavouriteDto());
        model.addAttribute("account", getAccount(authentication));
        return "/events/process";
    }


}
