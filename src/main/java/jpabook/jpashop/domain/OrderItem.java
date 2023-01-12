package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protectd OrderItem() 과 동일
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }
    //생성 메서드를 사용하지 않고 다른 방법-> 생성자를 이용해 만들 수도 있다. 이경우 유지보수가 어려워지기 때문에
    //기본 생성자를 protected 선언해두는 것이 좋다.(jpa는 protected까지 기본생성자를 만들 수 있게 허용)

    //==비즈니스 로직==//
    public void cancel() {
        getItem().addStock(count);
    } //재고수량 원복

    //==조회 로직==//
    public int getTotalPrice() {
        return orderPrice * count;
//        return getOrderPrice() * getCount();
    }
}
