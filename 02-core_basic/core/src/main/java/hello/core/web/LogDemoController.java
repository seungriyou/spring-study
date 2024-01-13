package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    /*
    request 스코프 빈은 실제 고객의 요청이 와야지만 생성할 수 있으므로,
    스프링 컨테이너에게 request 스코프 빈(MyLogger)을 요청하는 것을 의존관계 주입 단계가 아닌,
    실제 고객 요청이 왔을 때로 지연시켜야 한다.
     */
    private final LogDemoService logDemoService;
    private final ObjectProvider<MyLogger> myLoggerProvider;    // MyLogger를 DL 할 수 있는 provider가 주입된다.

    @RequestMapping("log-demo")
    @ResponseBody   // 데이터를 그대로 반환하기 위함
    public String logDemo(HttpServletRequest request) throws InterruptedException {
        MyLogger myLogger = myLoggerProvider.getObject();
        String requestURL = request.getRequestURL().toString();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        Thread.sleep(1_000);    // 동시 요청 살펴보기
        logDemoService.logic("testId");
        return "OK";
    }
}
