package jpabook.jpashop.api;

import java.util.List;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * XToOne 관계의 성능 최적화 (ManyToOne, OneToOne)
 * Order
 * Order - Member
 * Order - Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    /*
    V1: 엔티티를 직접 노출
     */
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAll(new OrderSearch());
        System.out.println("all = " + all);
        return all;
    }
}
