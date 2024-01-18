package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor    // final이 있는 필드만 가지고 생성자 만들어 줌
public class MemberService {
    private final MemberRepository memberRepository;

    /*
    @Autowired  // 생성자 주입 (@Autowired 생략 가능)
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
     */

    /*
    회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);    // 중복회원 검증 로직
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // 문제가 있으면 예외 발생
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /*
    회원 전체 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /*
    회원 단건 조회
     */
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
