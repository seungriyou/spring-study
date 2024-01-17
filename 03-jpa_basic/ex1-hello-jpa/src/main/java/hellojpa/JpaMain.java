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

            Address address = new Address("city", "street", "1999");

            EmbeddedMember member = new EmbeddedMember();
            member.setUsername("hello");
            member.setHomeAddress(address);

            // address.setCity("newCity"); 불가능
            // 값 타입은 통으로 갈아끼우는 것이 맞다!
            // 복사해서 새롭게 생성하자.
            Address newAddress = new Address(
                    "NewCity",
                    address.getStreet(),
                    address.getZipcode()
            );
            member.setHomeAddress(newAddress);

            em.persist(member);

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
