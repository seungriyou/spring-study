package sr.security.springjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sr.security.springjwt.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    // username으로 존재 여부 확인
    Boolean existsByUsername(String username);

    // username을 받아 DB에서 조회
    User findByUsername(String username);
}
