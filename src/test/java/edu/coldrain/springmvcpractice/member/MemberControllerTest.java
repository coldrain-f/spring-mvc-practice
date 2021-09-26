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

    @Test
    public void bindingErrorMembers () throws Exception {
        // 핸들러에 BindingResult 가 있으므로 바인딩이 실패해도 400번 에러가 발생하지 않고
        // 정상적으로 200 상태코드가 응답에 담긴다.
        mockMvc.perform(post("/members")
                        .param("age", "aaa"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void validMembers() throws Exception {
        // 핸들러에 @Valid 애노테이션이 붙어있고 Member 모델에 @Min(1) 이 설정되어 있어서
        // age 에 -1을 바인딩 하면 bindingResult 에 에러로 담긴다.
        // @Valid 이외에도 @Validated 를 사용해도 된다.
        mockMvc.perform(post("/members")
                        .param("age", "-1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}