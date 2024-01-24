package study.datajpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

@SpringBootTest // 테스트 시에 스프링 빈 주입 받아야 하므로
@Transactional  // 스프링에서 제공해주는 transactional 사용 권장 (JPA의 모든 데이터 변경은 트랜잭션 안에서 일어나야 함)
class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() {
        // setter 보다는 생성자에서 주입 or 의미있는 메서드
        Member member = new Member("memberA");
        Member savedMember = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(savedMember.getId());

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);   // 같은 영속성 컨텍스트(=> 트랜잭션) 내에서는 동일한 엔티티!
    }

}