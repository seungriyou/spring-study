package study.datajpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void testMember() {
        // setter 보다는 생성자에서 주입 or 의미있는 메서드
        Member member = new Member("memberA");
        Member savedMember = memberRepository.save(member);

        // 좋은 방법은 아니나, Optional 결과 값을 get()으로 바로 받아온다.
        Member findMember = memberRepository.findById(savedMember.getId()).get();

        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);   // 같은 영속성 컨텍스트(=> 트랜잭션) 내에서는 동일한 엔티티!
    }

}