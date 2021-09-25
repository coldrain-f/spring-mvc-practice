package edu.coldrain.springmvcpractice.member;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Member {

    private Long id;

    private String name;

    private Integer age;

    private String mobile;
}
