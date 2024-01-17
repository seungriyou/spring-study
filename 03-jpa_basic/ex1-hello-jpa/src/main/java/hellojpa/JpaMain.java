package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

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
            EmbeddedMember member = new EmbeddedMember();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            // 값 타입 컬렉션
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            // 값 타입 컬렉션 대신 일대다 관계
            member.getAddressHistory().add(new AddressEntity("old1", "street", "1034"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "1034"));

            em.persist(member);

            em.flush();
            em.clear();

            EmbeddedMember findMember = em.find(EmbeddedMember.class, member.getId());

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
