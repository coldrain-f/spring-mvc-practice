package edu.coldrain.springmvcpractice.event;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class Event {

    private Long id;

    private String name;

    private int limitOfEnrollment;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;
}
