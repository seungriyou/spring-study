package sr.TestSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 람다식으로 작성
                // 작성 순서대로 우선순위 부여되므로 주의
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "/loginProc", "/join", "/joinProc").permitAll() // 모두 접근 가능
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN", "USER")  // wildcard 사용 가능
                        .anyRequest().authenticated()   // 로그인한 사용자들은 접근 가능하도록
                );

        // === 로그인 === //
        // formLogin 방식
//        http
//                .formLogin((auth) -> auth.loginPage("/login")   // 로그인 페이지 경로 (admin 페이지 접근 시 자동으로 리다이렉트)
//                        .loginProcessingUrl("/loginProc")       // POST 방식으로 로그인 데이터를 넘긴다.
//                        .permitAll()                            // 누구나 들어올 수 있다.
//                );
        // httpBasic 방식
        http
                .httpBasic(Customizer.withDefaults());

        // 개발 환경에서는 잠시 disable 시켜두자.
        http
                .csrf((auth) -> auth.disable());                // 스프링 시큐리티에서는 기본적으로 csrf 설정이 되어있는데, 이러면 POST 시 csrf 토큰도 보내주어야 한다.

        // 다중 로그인 관련
        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1)                 // 하나의 아이디에 대한 다중 로그인 허용 개수
                        .maxSessionsPreventsLogin(true));   // true: 초과 시 새로운 로그인 차단 / false: 초과 시 기존 세션 하나 삭제


        // 세션 고정 보호 관련
        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId());

        return http.build();    // 빌드해서 리턴
    }
}
