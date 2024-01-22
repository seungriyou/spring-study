package jpabook.jpashop.service;

import java.util.List;
import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /*
    주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문 상품 생성 (w/ 생성 메서드)
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);
        // new OrderItem() 불가능

        // 주문 생성 (w/ 생성 메서드)
        Order order = Order.createOrder(member, delivery, orderItem);
        // new Order() 불가능

        // 주문 저장 (CASCADE로 인해 delivery와 orderItem도 함께 persist 됨)
        orderRepository.save(order);

        return order.getId();
    }

    /*
    주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        // 주문 취소
        // -> JPA의 큰 장점: 서비스 계층에 비즈니스 로직을 전부 꺼내와서 쿼리를 각각 짤 필요가 없다!
        order.cancel();
    }

    /*
    검색
     */
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }
}
