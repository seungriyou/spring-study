package hello.hellospring.controller;

import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {

    // 생성자 & @Autowired를 통해 스프링 컨테이너에 딱 하나의 MemberService만 등록해두고, 그것을 사용하도록 한다.
    private final MemberService memberService;

    // @Autowired: 스프링 컨테이너에서 가져온다.
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
