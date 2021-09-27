package edu.coldrain.springmvcpractice.event;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    public List<Event> getEvents() {
        final Event event1 = Event.builder()
                .id(1L)
                .name("스프링 웹 MVC 스터디 1차")
                .limitOfEnrollment(5)
                .startDateTime(LocalDateTime.of(2021, 1, 10, 10, 0))
                .endDateTime(LocalDateTime.of(2021, 1, 17, 12, 0))
                .build();

        final Event event2 = Event.builder()
                .id(2L)
                .name("스프링 웹 MVC 스터디 2차")
                .limitOfEnrollment(5)
                .startDateTime(LocalDateTime.of(2021, 1, 10, 10, 0))
                .endDateTime(LocalDateTime.of(2021, 1, 17, 12, 0))
                .build();

        return List.of(event1, event2);
    }
}
