package study.datajpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // <1> 메서드 이름으로 쿼리 생성
    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3HelloBy();

    // <2> 메서드 이름으로 JPA NamedQuery 호출 (거의 사용 X)
    // 엔티티 클래스와 메서드 이름을 가지고 NamedQuery를 먼저 찾는다.
    // 없으면 메서드 이름으로 쿼리를 생성한다.
    @Query(name = "Member.findByUsername")
    // -> 생략해도 된다!
    List<Member> findByUsername(@Param("username") String username);
}
