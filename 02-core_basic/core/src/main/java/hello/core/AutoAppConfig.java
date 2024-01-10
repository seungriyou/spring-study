package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        // 컴포넌트 스캔 대상이 되는 최상위 패키지 설정
        basePackages = "hello.core",
        // 기존의 AppConfig을 제거하기 위함
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

    // 다음과 같이 이름이 동일한 스프링 빈을 수동으로 등록하면 스프링 부트 어플리케이션 실행 시 오류가 발생한다.
    /*
    @Bean(name = "memoryMemberRepository")
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
    */

}
