package edu.coldrain.springmvcpractice.sample;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(Sample2Controller.class)
public class Sample2ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void exist() {
        Assertions.assertThat(mockMvc).isNotNull();
    }

    @Test
    public void getBooks() throws Exception {
        mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("id=null,title=null"));
    }

    @Test
    public void getBooks2() throws Exception {
        final MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("id", "1");
        multiValueMap.add("title", "단어가 읽기다 기본편");

        mockMvc.perform(get("/books")
                        .param("id", "1")
                        .params(multiValueMap))
                .andDo(print())
                .andExpect(content().string("id=1,title=단어가 읽기다 기본편"));
    }

    @Test
    public void getBooks3() throws Exception {
        //@RequestParam 애노테이션이 붙어있으면
        //required=true 가 기본값이라 값을 반드시 보내야한다.
        //@RequestParam 이 없는 파라미터도 값이 바인딩되며 값을 보내지 않으면 null 이다.
        mockMvc.perform(get("/books")
                        .param("id", "1"))
                .andExpect(status().isBadRequest()) //400 error
                .andDo(print());
    }

    @Test
    public void getBooks4() throws Exception {
        mockMvc.perform(get("/books")
                        .param("title", "단어가 읽기다 기본편"))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void getBooks5() throws Exception {
        final MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("id", "1");
        multiValueMap.add("title", "단어가 읽기다 기본편");

        mockMvc.perform(get("/books")
                        .params(multiValueMap)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("id=1,title=단어가 읽기다 기본편"))
                .andDo(print());
    }

    @Test
    void postBooks() throws Exception {
        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

}