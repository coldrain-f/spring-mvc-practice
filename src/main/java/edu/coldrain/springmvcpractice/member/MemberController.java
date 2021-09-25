package edu.coldrain.springmvcpractice.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public Member postMembers(@ModelAttribute Member member) {
        return member;
    }
}
