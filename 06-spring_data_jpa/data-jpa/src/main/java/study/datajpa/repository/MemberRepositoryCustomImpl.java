package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    // 구현 클래스 이름은 <리포지토리 인터페이스 이름 + Impl> 또는 <사용자 정의 인터페이스명 + Impl>로 설정하면 된다.
    // 이때, 후자를 권장하는데, 그 이유는 더 직관적이고 인터페이스를 분리하여 구현하는 것도 가능하기 때문이다.

    private final EntityManager em; // 생성자 주입도 가능

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}
