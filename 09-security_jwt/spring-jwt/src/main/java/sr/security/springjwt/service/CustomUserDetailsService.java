package sr.security.springjwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sr.security.springjwt.dto.CustomUserDetails;
import sr.security.springjwt.entity.User;
import sr.security.springjwt.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // DB에서 username으로 회원 조회
        User findUser = userRepository.findByUsername(username);

        if (findUser != null) {
            // UserDetails에 담아서 return하면 AuthenticationManager가 검증한다.
            return new CustomUserDetails(findUser);
        }

        return null;
    }
}
