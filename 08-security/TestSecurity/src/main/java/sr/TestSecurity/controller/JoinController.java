package sr.TestSecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import sr.TestSecurity.dto.JoinDTO;
import sr.TestSecurity.service.JoinService;

@Controller
public class JoinController {

    // TODO: 추후 생성자 주입으로 변경 필요
    @Autowired
    private JoinService joinService;

    @GetMapping("/join")
    public String joinP() {

        return "join";
    }

    @PostMapping("/joinProc")
    public String joinProcess(JoinDTO joinDTO) {

        System.out.println("joinDTO.getUsername() = " + joinDTO.getUsername());
        joinService.joinProcess(joinDTO);

        return "redirect:/login";
    }
}
