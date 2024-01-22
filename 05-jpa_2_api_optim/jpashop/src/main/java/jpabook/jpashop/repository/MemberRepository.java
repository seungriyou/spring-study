package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository // 스프링 빈 등록 (컴포넌트 스캔)
@RequiredArgsConstructor
public class MemberRepository {

    /*
    @PersistenceContext
    private EntityManager em;   // 스프링이 EntityManager를 만들어서 주입해 줌
     */

    private final EntityManager em; // 다른 컴포넌트와 동일하게 생성자 주입 가능

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        // JPQL을 사용해야 함 (엔티티 객체에 대해 쿼리)
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
