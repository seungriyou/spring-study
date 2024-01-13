package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

    /*
    request 스코프 빈은 실제 고객의 요청이 와야지만 생성할 수 있으므로,
    스프링 컨테이너에게 request 스코프 빈(MyLogger)을 요청하는 것을 의존관계 주입 단계가 아닌,
    실제 고객 요청이 왔을 때로 지연시켜야 한다.
     */
    private final ObjectProvider<MyLogger> myLoggerProvider;

    public void logic(String id) {
        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.log("service id = " + id);
    }
}
