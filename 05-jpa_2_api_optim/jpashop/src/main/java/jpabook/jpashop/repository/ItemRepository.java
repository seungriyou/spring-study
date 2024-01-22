package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import java.util.List;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if (item.getId() == null) {     // (1) item이 영속화 되기 전의 새롭게 생성한 객체라면
            em.persist(item);           // 신규 등록 (persist)
        } else {                        // (2) 이미 DB에 등록된 걸 가져온 것이라면
            em.merge(item);             // merge (update와 비슷한 동작)
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
