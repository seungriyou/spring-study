package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // 스프링은 (1) 먼저 컨트롤러를 찾고 (2) 없으면 정적 리소스를 찾으므로
    // 이 경우에는 (1) 번에 해당하기 때문에 index.html이 나오지 않는다.
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
