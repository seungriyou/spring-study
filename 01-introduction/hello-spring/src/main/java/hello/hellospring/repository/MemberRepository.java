package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    Optional<Member> findById(Long id); // null을 optional로 감싸서 반환
    Optional<Member> findByName(String name);

    List<Member> findAll();

}
