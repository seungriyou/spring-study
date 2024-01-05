package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

// 컴포넌트 스캔을 위해 @Component를 달아도 되지만,
// 정형화된 컴포넌트가 아닌 AOP이므로 명확하게 명시하기 위해 직접 스프링 빈에 등록하는 경우가 많다.
// 여기에서는 그냥 @Component를 사용하자.
@Component
@Aspect
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        System.out.println("START: " + joinPoint.toString());

        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;

            System.out.println("END: " + joinPoint.toString() + "" + timeMs + "ms");
        }
    }
}
