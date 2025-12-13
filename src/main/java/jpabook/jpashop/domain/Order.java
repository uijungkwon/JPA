package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //연관 관계 주인
    @JoinColumn(name = "member_id")
    private Member member;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;//이 변수명이 mappedBy에 쓰인다.

    private LocalDateTime orderDate; //hibernate 지원 //주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status;//주문 상태를 문자열로 2개 중 하나 나타냄

    //==연관관계 메서드==//
    public void setMember(Member member) {/*양방향 코드 저장*/
        this.member = member; //내 테이블에 멤버 정보 저장
        member.getOrders().add(this);//멤버 테이블에 주문 정보 저장
    }
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);//리스트에 추가
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery; //테이블 양방향으로 값을 저장한다.
        delivery.setOrder(this);
    }

}
