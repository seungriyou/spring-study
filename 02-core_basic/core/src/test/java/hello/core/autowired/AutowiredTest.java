package hello.core.autowired;

import hello.core.member.Member;
import jakarta.annotation.Nullable;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutowiredTest {

    @Test
    void AutowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {

        // 1. 의존관계 자동 주입할 스프링 빈이 없다면 메서드 자체가 호출이 안 된다.
        @Autowired(required = false)
        public void setNoBean1(Member noBean1) {
            System.out.println("noBean1 = " + noBean1);
        }

        // 2. 자동 주입할 대상이 없다면 메서드는 호출되나 null로 입력된다.
        @Autowired
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("noBean2 = " + noBean2);
        }

        // 3. (Java 8 문법) 자동 주입할 대상이 없다면 메서드는 호출되나 Optional.empty가 입력된다.
        @Autowired
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("noBean3 = " + noBean3);
        }
    }
}
