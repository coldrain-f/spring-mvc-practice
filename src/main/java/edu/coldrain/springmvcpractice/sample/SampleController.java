package edu.coldrain.springmvcpractice.sample;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SampleController {

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @GetMapping("/question?")
    @ResponseBody
    public String question() {
        return "";
    }

    @GetMapping("/asterisk/*")
    @ResponseBody
    public String asterisk() {
        return "";
    }

    @GetMapping("/doubleAsterisk/**")
    @ResponseBody
    public String doubleAsterisk() {
        return "";
    }

    @GetMapping("/example/**")
    @ResponseBody
    public String exampleOne() {
        return "exampleOne";
    }

    @GetMapping("/example/two")
    @ResponseBody
    public String exampleTwo() {
        return "exampleTwo";
    }

    @GetMapping(
            value = "/sample",
            consumes = MediaType.APPLICATION_JSON_VALUE, // contextType=application/json
            produces = MediaType.TEXT_PLAIN_VALUE // accept=text/plain
    )
    @ResponseBody
    public String sample() {

        return "sample";
    }


}
