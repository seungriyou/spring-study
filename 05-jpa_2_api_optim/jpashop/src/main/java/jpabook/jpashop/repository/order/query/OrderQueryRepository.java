package jpabook.jpashop.repository.order.query;

import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    // 화면, API와 의존성이 있는 쿼리를 내보내기 위해 리포지토리 분리

    private final EntityManager em;

    /*
    V4: 컬렉션 조회 시 N+1 문제 발생
     */
    public List<OrderQueryDto> findOrderQueryDtos() {
        // 컬렉션 부분 빼고 가져오기 (-> 1)
        List<OrderQueryDto> result = findOrders();

        // 컬렉션 부분 직접 채우기 (-> N)
        result.forEach(o -> {
            List<OrderItemQueryDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });

        return result;
    }

    /*
    V5: 컬렉션 조회 시 N+1 문제 해결 (findOrderQueryDtos() 개선) (1+1)
     */
    public List<OrderQueryDto> findAllByDto_optim() {
        // ToOne 관계까지 조인해서 가져오기 (row가 불어나지 않음)
        List<OrderQueryDto> result = findOrders();                                      // (-> 1)

        // result에서 Order.id 추출하기
        List<Long> orderIds = toOrderIds(result);

        // 추출한 Order.id를 in으로 조회하는 쿼리 실행하기 (Order를 Map에 넣어 메모리에 올리기)
        Map<Long, List<OrderItemQueryDto>> orderItemMap = findOrderItemMap(orderIds);   // (-> 1)

        // OrderQueryDto.orderItems에 OrderItemQueryDto 추가하기
        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }

    /*
    V6: 플랫 데이터 최적화 (1)
     */
    public List<OrderFlatDto> findAllByDto_flat() {
        return em.createQuery(
                "select new" +
                        " jpabook.jpashop.repository.order.query.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d" +
                        " join o.orderItems oi" +
                        " join oi.item i", OrderFlatDto.class
        ).getResultList();
    }

    // === V5 === //
    private List<Long> toOrderIds(List<OrderQueryDto> result) {
        return result.stream()
                .map(o -> o.getOrderId())
                .collect(Collectors.toList());
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQueryDto> orderItems = em.createQuery("select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id in :orderIds", OrderItemQueryDto.class)  // id를 in으로 찾는다!
                .setParameter("orderIds", orderIds)
                .getResultList();                   // (-> 1)

        // 편리하게 사용하기 위해 Map으로 변환 (루프 대신) (메모리에 Map으로 올려둔다!)
        Map<Long, List<OrderItemQueryDto>> orderItemMap = orderItems.stream()
                .collect(Collectors.groupingBy(orderItemQueryDto -> orderItemQueryDto.getOrderId()));
        return orderItemMap;
    }

    // === V4 === //
    public List<OrderQueryDto> findOrders() {
        return em.createQuery(
                "select new jpabook.jpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d", OrderQueryDto.class
        ).getResultList();
    }

    private List<OrderItemQueryDto> findOrderItems(Long orderId) {
        return em.createQuery("select new jpabook.jpashop.repository.order.query.OrderItemQueryDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
                        " from OrderItem oi" +
                        " join oi.item i" +
                        " where oi.order.id = :orderId", OrderItemQueryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }
}
