package sr.TestSecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sr.TestSecurity.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
