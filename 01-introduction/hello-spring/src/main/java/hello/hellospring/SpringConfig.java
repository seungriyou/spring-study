package hello.hellospring;

import hello.hellospring.repository.JpaMemberRepository;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    // Jdbc, JdbcTemplate에서는 DataSource를 DI 해주었으나,
    // JPA에서는 EntityManager를 DI 해준다. (생성자 주입 + @Autowired)
    // EntityManager는 DataSource와 마찬가지로 application.properties에 적힌 정보를 토대로 스프링 빈으로 생성 및 관리된다.
    private EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());   // MemberRepository를 넣어주어야 하므로
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JpaMemberRepository(em);
    }
}
