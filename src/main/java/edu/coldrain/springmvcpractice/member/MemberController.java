package edu.coldrain.springmvcpractice.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
public class MemberController {

    @GetMapping("/member/form")
    public String memberForm(Model model) {
        final Member member = Member.builder()
                .id(1L)
                .name("홍길동")
                .age(20)
                .mobile("010-0000-0001")
                .build();
        model.addAttribute("member", member);
        return "member/form";
    }

    @PostMapping("/members")
    @ResponseBody
    public Member postMembers(@Valid @ModelAttribute Member member, BindingResult bindingResult) {
        //@ModelAttribute 애노테이션이 붙은 객체 바로 오른쪽에 BindingResult 를 추가해 주면
        //값 바인딩 에러가 발생해도 Exception 이 발생하지 않는다.
        //예를들어 Integer age 에 문자열이 바인딩 되면 원래는 400 bad request 와 Exception 을 발생시켜야 하는데
        //Exception 이 발생하지 않고 BindingResult 에 바인딩 실패했다는 에러를 추가한다.

        if (bindingResult.hasErrors()) {
            log.info("바인딩 에러 발생!");
            bindingResult.getAllErrors().forEach(objectError -> log.info("error = {}", objectError));
            //대부분은 바인딩 에러가 발생하면 폼으로 다시 돌려보내고 사용자가 어떤 부분을 잘못 입력했는지 안내 해줘야 한다.
        }
        return member;
    }
}
