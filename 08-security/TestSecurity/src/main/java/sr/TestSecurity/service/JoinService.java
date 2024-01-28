package sr.TestSecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sr.TestSecurity.dto.JoinDTO;
import sr.TestSecurity.entity.Role;
import sr.TestSecurity.entity.User;
import sr.TestSecurity.repository.UserRepository;

@Service
public class JoinService {

    // TODO: 추후 생성자 주입으로 변경 필요
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO) {
        // db에 이미 동일한 username을 가진 회원이 존재하는지 확인
        boolean isDuplicate = userRepository.existsByUsername(joinDTO.getUsername());
        if (isDuplicate) {
            return;
        }

        User user = new User();
        user.setUsername(joinDTO.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
    }
}
