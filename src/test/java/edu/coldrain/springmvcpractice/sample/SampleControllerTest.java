package edu.coldrain.springmvcpractice.sample;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void exist() {
        Assertions.assertThat(mockMvc).isNotNull();
    }

    @Test
    public void hello() throws Exception {
        mockMvc.perform(get("/hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

    /**
     * perform: 요청을 전송하는 역할
     * andDo(print()): 요청/응답 전체 메시지를 확인
     * andExpect: 응답을 검증
     * andExpect(content()): 응답에 대한 정보 검증
     */

    @Test
    public void isMethodNotAllowed() throws Exception {
        mockMvc.perform(delete("/hello"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed())
                .andExpect(content().string(""));
    }

    @Test
    public void questionIsOk() throws Exception {
        // "question?" 로 설정하면
        // ? 뒤에 반드시 한 글자가 와야 한다.
        mockMvc.perform(get("/question1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void questionIsNotFound() throws Exception {
        // ? 뒤에 한 글자가 오지 않으면 상태코드에 404 Not Found 가 온다.
        mockMvc.perform(get("/question"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void asteriskIsOk() throws Exception {
        // "asterisk/*" 로 설정하면
        // 하나의 패스에 어떤 글자가 와도 된다.
        // "asterisk/aaa" 나 "asterisk/bb" 등
        // 하지만 "asterisk/aaa/a"와 같은 여러 패스는 안 된다.

        mockMvc.perform(get("/asterisk/aaa"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/asterisk/bb"))
                .andExpect(status().isOk());
    }

    @Test
    public void asteriskIsNotFound() throws Exception {
        mockMvc.perform(get("/asterisk/aaa/a"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void doubleAsteriskIsOk() throws Exception {
        mockMvc.perform(get("/doubleAsterisk/aaa"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/doubleAsterisk/aaa/aa"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/doubleAsterisk/aaa/aa/a"))
                .andExpect(status().isOk());
    }

    @Test
    public void exampleOne() throws Exception {
        //중복되면 구체적인 URI 가 맵핑된다.
        mockMvc.perform(get("/example/two"))
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(SampleController.class))
                .andExpect(handler().methodName("exampleTwo"));
    }

    //URI 확장자 맵핑은 스프링 부트는 보안 이슈로 지원하지 않는다.
    //RFD Attack 이슈가 있음 hello.* 권장X

    @Test
    public void testSample() throws Exception {
        mockMvc.perform(get("/sample")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.TEXT_PLAIN_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
}