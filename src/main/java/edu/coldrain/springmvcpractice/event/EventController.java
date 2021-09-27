package edu.coldrain.springmvcpractice.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @PostMapping("/events/param")
    @ResponseBody
    public Event postParam(@RequestParam Long id, @RequestParam String name) {
        // @RequestParam 은 기본이 required=true 로 설정되어 있어서
        // 값이 반드시 넘어와야 한다.
        // 그리고 넘어온 기본타입 값은 매개변수 타입에 맞게 자동으로 변환이 된다.
        // @RequestParam 을 생략하면 붙은것과 같고 required 는 false 이다. (테스트 예정)
        // 넘어오는 key 와 매개변의 이름이 같으면 바인딩되고
        // 같지 않다면 기본적으로 바인딩 되지 않기때문에 name 속성으로 넘어오는 key 와 같게 해줘야 한다.
        // defaultValue 로 값이 넘어오지 않았을 경우 기본값을 설정할 수 있다. (defaultValue 를 설정하면 required=false 와 같음)
        return Event.builder().id(id).name(name).build();
    }

    @GetMapping("/events/form")
    public String eventsForm(Model model) {
        model.addAttribute("event", new Event());
        return "events/eventsForm";
    }

    @PostMapping("/events/new")
    public String createEvent(@Validated @ModelAttribute Event event, BindingResult bindingResult) {

        //값을 잘못 입력했다면 값을 유지한채 다시 입력폼으로 돌려보낸다.
        if (bindingResult.hasErrors()) {
            return "events/eventsForm";
        }

        //정상 값이라면 저장소에 이벤트를 저장했다고 가정하고 목록을 보여준다.
        return "redirect:/events/list";
    }

    @GetMapping("/events/list")
    public String listEvent(Model model) {
        //저장소에서 이벤트 목록을 가져온다고 가정
        final List<Event> eventList = eventService.getEvents();
        model.addAttribute("eventList", eventList);
        return "events/list";
    }
}
