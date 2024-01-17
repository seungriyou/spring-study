package hellojpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.Set;

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
            /* 값 타입 저장 */
            EmbeddedMember member = new EmbeddedMember();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            // 값 타입 컬렉션
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            // 값 타입 컬렉션
            member.getAddressHistory().add(new Address("old1", "street", "1034"));
            member.getAddressHistory().add(new Address("old2", "street", "1034"));

            em.persist(member);

            em.flush();
            em.clear();

            /* 값 타입 조회 */
            EmbeddedMember findMember = em.find(EmbeddedMember.class, member.getId());

            // 값 타입 컬렉션은 지연 로딩
            List<Address> addressHistory = findMember.getAddressHistory();
            for (Address address : addressHistory) {
                System.out.println("address.getCity() = " + address.getCity());
            }

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            for (String favoriteFood : favoriteFoods) {
                System.out.println("favoriteFood = " + favoriteFood);
            }

            /* 값 타입 수정 */
            // findMember.getHomeAddress().setCity("newCity") -> 값 타입은 immutable 해야 하므로 X
            Address oldAddress = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity", oldAddress.getStreet(), oldAddress.getZipcode()));

            // 값 타입 컬렉션 수정
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            // 컬렉션에서 객체를 지울 때 -> equals()를 이용한다. (제대로 재정의 해두어야 한다.)
            // 동일한 값을 가지는 새로운 객체를 생성해서 remove()에 넘기고,
            // 새로운 값을 가지는 객체를 생성해서 add()에 넘긴다.
            findMember.getAddressHistory().remove(new Address("old1", "street", "1034"));
            findMember.getAddressHistory().add(new Address("newCity1", "street", "1034"));

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
