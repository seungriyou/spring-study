package jpabook.jpashop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest // junit 실행 시 스프링과 엮어서 실행
@Transactional  // @Transactional이 테스트 케이스에 있으면 commit이 아닌 rollback 된다.
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    // 쿼리 확인용
    @Autowired
    EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("you");

        // when
        Long saveId = memberService.join(member);

        // then
        em.flush();
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("you");
        Member member2 = new Member();
        member2.setName("you");

        // when
        memberService.join(member1);
        try {
            memberService.join(member2);    // 예외 발생해야 함
        } catch (IllegalStateException e) {
            return;
        }

        // then
        fail("예외가 발생해야 한다.");
    }

    @Test
    public void 중복_회원_예외2() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("you");
        Member member2 = new Member();
        member2.setName("you");

        // when
        memberService.join(member1);

        // then
        Assertions.assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));
    }
    
}