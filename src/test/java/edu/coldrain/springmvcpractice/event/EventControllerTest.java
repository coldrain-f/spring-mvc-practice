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
        // options ??? ???????????? ?????? ????????? ALLOW ??? ???????????? ???????????? ????????????.
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
        //????????????????????? ??????
        //post ????????? ?????? ??????????????? ?????? ?????? ??? ??????.
        mockMvc.perform(post("/events/param?id=1&name=????????? ??? MVC ????????? 1???"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("name").value("????????? ??? MVC ????????? 1???"))
                .andDo(print());
    }

    @Test
    public void postParam2() throws Exception {
        // form ???????????? ?????? ????????? ?????? ??????.
        mockMvc.perform(post("/events/param")
                        .param("id", "2")
                        .param("name", "????????? ??? MVC ????????? 2???"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value("2"))
                .andExpect(jsonPath("name").value("????????? ??? MVC ????????? 2???"));
    }



}