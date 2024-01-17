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

            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);
            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("hello1");
            member1.setTeam(teamA);
            em.persist(member1);
            Member member2 = new Member();
            member2.setUsername("hello2");
            member2.setTeam(teamB);
            em.persist(member2);

            em.flush();
            em.clear();

            List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();

            tx.commit();
        } catch (Exception e) {
            // 오류가 발생했으면 롤백
            tx.rollback();
            e.printStackTrace();
        } finally {
            // EntityManager가 DB 커넥션을 물고 있으므로 사용했으면 닫기
            em.close();
        }
        // transaction 끝

        emf.close();
    }
}
