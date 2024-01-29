package sr.security.springjwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // csrf disable
        // 세션 방식에서는 세션이 고정되므로 csrf 공격에 방어해야 하지만, JWT 방식에서는 세션은 stateless로 관리하므로 방어하지 않아도 ok
        http
                .csrf((auth) -> auth.disable());

        // JWT 방식을 사용할 것이므로, formLogin, httpBasic 방식 모두 disable
        http
                .formLogin((auth) -> auth.disable());
        http
                .httpBasic((auth) -> auth.disable());

        // 경로별 인가 설정
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/login", "/", "/join").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated());

        // 세션 stateless 설정 (중요**)
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
