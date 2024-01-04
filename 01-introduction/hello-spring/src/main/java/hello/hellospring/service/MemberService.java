package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    // MemberRepository를 외부에서 넣어주도록 한다.
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입
     */
    public Long join(Member member) {
        /* 같은 이름이 있는 회원은 저장 X */
        validateDuplicateMember(member);    // 메서드를 따로 빼는 편이 낫다.

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // 다음 결과는 optional로 반환되므로, 그 안에 값이 있으면 이미 이름이 같은 회원이 존재한다는 것 (ifPresent)
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
