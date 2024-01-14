package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    // 스프링 부트가 @PersistenceContext 어노테이션을 보고 EntityManager를 주입해준다.
    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();  // member가 아닌 id를 return 하는 것은 스타일이다. (커맨드와 쿼리를 분리하라. 이것은 커맨드성)
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
