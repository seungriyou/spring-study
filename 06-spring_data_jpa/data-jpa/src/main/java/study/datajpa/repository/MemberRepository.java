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

    // <3> @Query: 리포지토리 메서드에 쿼리 정의하기
    // JPQL을 인터페이스 메서드에 작성할 수 있다. (실무에서 많이 쓴다!)
    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);
}
