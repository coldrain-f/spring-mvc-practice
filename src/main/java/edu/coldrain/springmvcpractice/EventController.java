package edu.coldrain.springmvcpractice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class EventController {

    private final EventService eventservice;

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public String events(Model model) {
        model.addAttribute("events", eventservice.getEvents());

        return "events";
    }
}
