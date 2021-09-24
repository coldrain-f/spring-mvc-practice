package edu.coldrain.springmvcpractice.event;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest({EventController.class, EventService.class})
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void exist() {
        Assertions.assertThat(mockMvc).isNotNull();
    }

    @Test
    public void events() throws Exception {
        mockMvc.perform(get("/events"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getEvents() throws Exception {
        mockMvc.perform(get("/events/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("events/1"));
        mockMvc.perform(get("/events/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("events/2"));
        mockMvc.perform(get("/events/3"))
                .andExpect(status().isOk())
                .andExpect(content().string("events/3"));
    }

    @Test
    public void postEvents() throws Exception {
        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteEvents() throws Exception {
        mockMvc.perform(delete("/events/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("events/1"));
        mockMvc.perform(delete("/events/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("events/2"));
        mockMvc.perform(delete("/events/3"))
                .andExpect(status().isOk())
                .andExpect(content().string("events/3"));
    }

    @Test
    public void putEvents() throws Exception {
        mockMvc.perform(put("/events/1")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("events/1"));
    }

    @Test
    public void optionsEvents() throws Exception {
        // options 로 요청하면 응답 헤더에 ALLOW 로 지원하는 메서드를 포함한다.
        mockMvc.perform(options("/events"))
                .andExpect(status().isOk())
                .andExpect(header().stringValues(
                        HttpHeaders.ALLOW,"GET","HEAD","POST","OPTIONS"))
                .andDo(print());
    }

    @Test
    public void jsonEvents() throws Exception {
        mockMvc.perform(get("/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andDo(print());
    }

    @Test
    public void stringEvents() throws Exception {
        mockMvc.perform(get("/events/response/entity")
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("success"))
                .andDo(print());
    }

    @Test
    public void postParam() throws Exception {
        //쿼리파라미터로 전송
        //post 방식도 쿼리 파라미터로 값을 보낼 수 있다.
        mockMvc.perform(post("/events/param?id=1&name=스프링 웹 MVC 스터디 1차"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("name").value("스프링 웹 MVC 스터디 1차"))
                .andDo(print());
    }

    @Test
    public void postParam2() throws Exception {
        // form 방식으로 값을 전송할 수도 있다.
        mockMvc.perform(post("/events/param")
                        .param("id", "2")
                        .param("name", "스프링 웹 MVC 스터디 2차"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("2"))
                .andExpect(jsonPath("name").value("스프링 웹 MVC 스터디 2차"));
    }



}