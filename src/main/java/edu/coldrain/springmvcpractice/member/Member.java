package edu.coldrain.springmvcpractice.member;

import lombok.*;

import javax.validation.constraints.Min;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Member {

    private Long id;

    private String name;

    @Min(1)
    private Integer age;

    private String mobile;
}
