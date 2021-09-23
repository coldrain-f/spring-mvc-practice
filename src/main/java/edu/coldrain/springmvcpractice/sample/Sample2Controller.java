package edu.coldrain.springmvcpractice.sample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class Sample2Controller {

    @GetMapping("/books")
    public String books(@RequestParam Long id, @RequestParam String title) {
        return "id=" + id + ",title=" + title;
    }
}
