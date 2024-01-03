package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    // 실무에서는 동시성 문제가 있을 수 있어 ConcurrentHashMap, AtomicLong의 사용을 고려해야 한다.
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // 요즘에는 null이 반환될 가능성이 있다면 optional로 감싸서 반환한다.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny(); // map을 돌면서 찾으면 그것을 반환하고, 없으면 optional에 null이 포함되어 반환된다.
    }

    @Override
    public List<Member> findAll() {
        // 실무에서는 ArrayList를 많이 쓴다.
        return new ArrayList<>(store.values());
    }

    // 테스트 시 메서드 수행 후 데이터 정리 용도
    public void clearStore() {
        store.clear();
    }
}
