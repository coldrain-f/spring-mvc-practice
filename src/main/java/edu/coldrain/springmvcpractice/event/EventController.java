package edu.coldrain.springmvcpractice.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public String events(Model model) {
        model.addAttribute("events", eventService.getEvents());

        return "events";
    }

    @GetMapping("/events/{id}")
    @ResponseBody
    public ResponseEntity<Event> getEvents(@PathVariable Long id) {
        //핸들러에 @ResponseBody 를 붙이고
        //반환을 ResponseEntity<Object> 를 하게되면 JSON 으로 변환해서 응답 본체에 실어보낸다.
        final Event event = Event.builder().id(id).build();
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @GetMapping("/events/response/entity")
    @ResponseBody
    public ResponseEntity<String> getResponseEntity() {
        //핸들러에 @ResponseBody 를 붙이고
        //반환을 ResponseEntity<String> 를 하게되면 text/plain 으로 변환해서 응답 본체에 실어보낸다.
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping(
            value = "/events",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String postEvents() {
        return "events";
    }

    @DeleteMapping("/events/{id}")
    @ResponseBody
    public String deleteEvents(@PathVariable int id) {
        return "events/" + id;
    }

    @PutMapping(
            value = "/events/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String putEvents(@PathVariable int id) {
        return "events/" + id;
    }
}
