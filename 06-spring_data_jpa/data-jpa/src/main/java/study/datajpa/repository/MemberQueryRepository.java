package study.datajpa.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import study.datajpa.entity.Member;

@Repository
@RequiredArgsConstructor
public class MemberQueryRepository {

    // 복잡한 화면용 쿼리는 사용자 정의 리포지토리에 구현하기보다는,
    // 아예 이렇게 리포지토리를 분리하는 편을 권장한다. (라이프 사이클 측면)

    private final EntityManager em;

    List<Member> findAllMembers() {
        return em.createQuery("select m from Member m")
                .getResultList();
    }
}
