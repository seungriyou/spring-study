package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        // EntityManagerFactory: 어플리케이션 로딩 시점에 딱 하나만
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello"); // persistence.xml에서 읽어온다.
        // EntityManager: DB에 저장하거나 하는 트랜잭션 단위, DB 커넥션을 얻어서 동작해야 하는 경우마다
        EntityManager em = emf.createEntityManager();

        // transaction 시작
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            // 팀 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            // 회원 저장
            Member member = new Member();
            member.setUsername("member1");
            member.changeTeam(team);    // ** Member 쪽의 연관관계 편의 메서드 (양쪽 모두 값 설정)
            em.persist(member);

            // 연관관계 편의 메서드는 한 쪽에서만 생성하자.
            // team.addMember(member);     // ** Team 쪽의 연관관계 편의 메서드 (양쪽 모두 값 설정)

            // 1차 캐시에서 말고 다시 SQL을 보내서 확인해보자.
            em.flush();
            em.clear();

            // 저장한 Member의 Team 확인
            Member findMember = em.find(Member.class, member.getId());
            List<Member> members = findMember.getTeam().getMembers();

            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }

            tx.commit();
        } catch (Exception e) {
            // 오류가 발생했으면 롤백
            tx.rollback();
        } finally {
            // EntityManager가 DB 커넥션을 물고 있으므로 사용했으면 닫기
            em.close();
        }
        // transaction 끝

        emf.close();
    }
}
