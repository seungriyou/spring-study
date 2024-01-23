package jpabook.jpashop.domain;

import static jakarta.persistence.FetchType.LAZY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    /*
    // 생성 메서드 사용을 강제하기 위해 기본 생성자 + protected
    // 혹은 @NoArgsConstructor(access = AccessLevel.PROTECTED)
    protected OrderItem() {
    }
     */

    // === 생성 메서드 ===
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        // 할인 기능이 추가될 수 있으므로 orderPrice는 따로
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        // Item 재고 감소
        item.removeStock(count);
        return orderItem;
    }

    // === 비즈니스 로직 ===
    public void cancel() {
        // Item의 재고를 주문 수량만 추가해주어야 한다.
        getItem().addStock(count);
    }

    // === 조회 로직 ===
    /*
    주문 상품 가격 조회
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
