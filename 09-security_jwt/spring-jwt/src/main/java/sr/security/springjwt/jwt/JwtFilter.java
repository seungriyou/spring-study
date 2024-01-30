package sr.security.springjwt.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import sr.security.springjwt.dto.CustomUserDetails;
import sr.security.springjwt.entity.User;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    // JwtUtil 동작 사용하기 위해 주입
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // token 검증 로직 구현

        // request에서 Authorization header 뽑아오기
        String authorization = request.getHeader("Authorization");

        // Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("token null");
            // filter chain의 다음 filter 이어서 수행
            filterChain.doFilter(request, response);

            // 메서드 종료
            return;
        }

        System.out.println("authorization now");
        // Bearer 접두사 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];

        // 토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            System.out.println("token expired");
            // filter chain의 다음 filter 이어서 수행
            filterChain.doFilter(request, response);

            // 메서드 종료
            return;
        }

        // === 일시적인 세션을 만들어서 사용자를 등록 === //
        // token에서 username, role 획득
        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        // user 엔티티를 생성하여 값 set
        User user = User.builder()
                .username(username)
                .password("temppassword")   // contextholder에 정확한 password를 넣을 필요는 없다. (매번 DB에서 가져오면 효율 X)
                .role(role)
                .build();

        // UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        // 스프링 시큐리티 인증 토큰 생성
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        // 세션에 사용자 등록 (-> 특정 경로 접근 가능)
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // filter chain의 다음 filter 이어서 수행
        filterChain.doFilter(request, response);
    }
}
