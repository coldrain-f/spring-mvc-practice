package edu.coldrain.springmvcpractice.member;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void exist() {
        Assertions.assertThat(mockMvc).isNotNull();
    }

    @Test
    public void memberForm() throws Exception {
        // view().name(expectedViewName) 요청했을 경우 반환되는 View 이름이 "member/form" 인지 테스트
        // model().attributeExists(...names) Model 에 "member" 가 존재하는지 테스트
        mockMvc.perform(get("/member/form"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/form"))
                .andExpect(model().attributeExists("member"))
                .andDo(print());
    }

    @Test
    public void postMembers() throws Exception {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("id", "1");
        params.add("name", "홍길동");
        params.add("age", "20");
        params.add("mobile", "010-0000-0001");

        mockMvc.perform(post("/members")
                        .params(params))
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("name").value("홍길동"))
                .andExpect(jsonPath("age").value("20"))
                .andExpect(jsonPath("mobile").value("010-0000-0001"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}