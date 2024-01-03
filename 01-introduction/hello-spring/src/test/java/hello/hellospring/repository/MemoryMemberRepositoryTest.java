package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 메서드 실행이 끝난 후 repository 데이터를 정리하도록 콜백 메서드 정의
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        // optional에서 값을 꺼낼 때는 get으로 꺼낼 수 있다. (좋은 방법은 아니지만 테스트 코드에서는 ok)
        Member result = repository.findById(member.getId()).get();

        /* <테스트 방법 #1> 직접 println 찍어보기 */
        // System.out.println("result = " + (result == member));

        /* <테스트 방법 #2> junit의 Assertions 이용하기 */
        // Assertions.assertEquals(member, result);

        /* <테스트 방법 #3> assertj의 Assertions 이용하기 (더 편리) */
        // (option + enter) 치고 static import 해두면 편리하게 메서드 이름으로만 사용 가능
        assertThat(member).isEqualTo(result);

    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
