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
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne //연관 관계 주인
    @JoinColumn(name = "member_id")
    private Member member;

    /*딜리버리 정보*/
    @OneToMany(mappedBy = "order_id")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name="delivery_id")
    private Delivery delivery;//이 변수명이 mappedBy에 쓰인다.

    private LocalDateTime orderDate; //hibernate 지원 //주문 시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status;//주문 상태


}
