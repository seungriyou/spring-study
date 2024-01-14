package jpabook.jpashop;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)    // @Transactional로 인한 롤백을 취소한다. (실제로는 롤백 해주어야 한다.)
    public void testMember() throws Exception {
        // given
        Member member = new Member();
        member.setUsername("memberA");

        // when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);

        // 같은 영속성 컨텍스트 안에서는 id 값이 같으면 같은 엔티티로 식별된다.
        System.out.println("findMember = " + findMember);
        System.out.println("member = " + member);
        System.out.println("(findMember == member) = " + (findMember == member));
    }

}