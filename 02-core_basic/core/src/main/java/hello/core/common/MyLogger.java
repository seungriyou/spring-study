package hello.core.common;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.UUID;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/*
request 스코프 빈은 HTTP request 단위로 존재하며,
스프링 컨테이너에게 효청하는 시점에 생성되고, HTTP request가 끝나는 시점에 소멸된다.
 */
@Component
@Scope(value = "request")
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        // 빈 생성 시점에는 알 수 없기 때문에 setter로 입력 받는다.
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "][" + requestURL + "] " + message);
    }

    @PostConstruct
    public void init() {
        // 빈이 생성되는 시점에 초기화 메서드에서 uuid를 생성해서 저장해두므로,
        // HTTP request 당 하나씩 생성되기 때문에 uuid를 저장해두면 다른 request와 구분할 수 있다.
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create: " + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close: " + this);
    }
}
