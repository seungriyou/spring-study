package sr.TestSecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sr.TestSecurity.dto.CustomUserDetails;
import sr.TestSecurity.entity.User;
import sr.TestSecurity.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // TODO: 추후 생성자 주입으로 변경 필요
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user != null) {
            return new CustomUserDetails(user);
        }

        return null;
    }
}
