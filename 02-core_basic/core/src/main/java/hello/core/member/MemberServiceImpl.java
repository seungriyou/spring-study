package hello.core.member;

public class MemberServiceImpl implements MemberService {

    // DIP 준수: 이제 추상화 MemberRepository에만 의존하므로
    private final MemberRepository memberRepository;

    // 생성자 주입
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
