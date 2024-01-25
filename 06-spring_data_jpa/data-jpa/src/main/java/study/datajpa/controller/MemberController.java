package study.datajpa.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    // 도메인 클래스 컨버터 사용
    @GetMapping("/members2/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<MemberDto> list(
            @PageableDefault(size = 3, sort = "username", direction = Direction.DESC) Pageable pageable
    ) {
        Page<Member> page = memberRepository.findAll(pageable);

        // 받아온 엔티티를 절대로 그냥 반환하면 안 된다!
        // DTO로 변환해야 한다.
        Page<MemberDto> map = page.map(MemberDto::new);

        return map;

        // 최적화
        // return memberRepository.findAll(pageable).map(MemberDto::new);
    }

    // @PostConstruct
    public void init() {
        // 데이터 넣기
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("user" + i, i));
        }
    }
}
