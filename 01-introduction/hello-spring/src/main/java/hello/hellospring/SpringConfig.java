package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    // MemberRepository를 상속 받고 있는 리포지토리 인터페이스의 구현체가 스프링 데이터 JPA에 의해 이미 스프링 빈으로 등록되어 있다.
    // 따라서 SpringConfig에서 새로 생성 및 @Bean으로 등록할 필요 없이, 생성자로 주입만 받으면 된다.
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);   // MemberRepository를 넣어주어야 하므로
    }

    /*
    @Bean
    public TimeTraceAop timeTraceAop() {
        // AOP는 컴포넌트 스캔 방식보다 명시적으로 직접 스프링 빈에 등록하는 것을 권장한다. (정형화 X)
        return new TimeTraceAop();
    }
    */
}
