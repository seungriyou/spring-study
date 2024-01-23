package jpabook.jpashop.api;

import java.util.List;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    /*
    V1: 엔티티 직접 노출
     */
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        // LAZY 로딩으로 불러와진 프록시 객체를 강제 초기화 한다.
        // 현재 hibernate5Module을 사용하고 있으므로, 프록시 객체는 null로 불러와지기 때문이다.
        // 이때, 양방향 연관관계는 한쪽에 @JsonIgnore를 붙여주어야 한다.
        for (Order order : all) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            order.getDelivery().getAddress();

            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }
}
