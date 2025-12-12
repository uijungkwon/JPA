package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "order_id")//다대다 관계를 일대다, 다대일로 연관테이블 생성했기 때문에
    private Order order;

    private int orderPrice; //"주문 당시 가격"

    private int count;//"주문 당시 수량"
}
