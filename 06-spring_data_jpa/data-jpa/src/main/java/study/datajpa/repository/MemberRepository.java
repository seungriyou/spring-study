package study.datajpa.repository;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import study.datajpa.dto.MemberDto;
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

    // 단순 값 조회
    @Query("select m.username from Member m")
    List<String> findUsernameList();

    // DTO로 조회
    @Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDto> findMemberDto();

    // 컬렉션 파라미터 바인딩 (Collection 타입으로 IN 절을 지원)
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);
}
