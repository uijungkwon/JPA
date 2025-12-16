package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //생성자를 직접 생성하면 안되는구나, 다른 메소드를 통해 생성자를 소환해야겠구나 => 유지보수 하기 위해서는 이렇게 "제약"하도록 코드를 짜야 유지보수 하기 용이하다.
public class Order { //엔터티 안에 로직 설정
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //연관 관계 주인
    @JoinColumn(name = "member_id")
    private Member member;


    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)//Order가 persist되면 Order안에 있는 OrderITem도 강제로 Persist를 날려준다.
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)//Order가 persist되면 Order안에 있는 Delivery도  강제로 Persist를 날려준다.
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

    //== 생성 메서드 ==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){ //주문 생성
        //주문이 생성 되었을 때부터 이 함수를 호출하여 모든 값들을 정의함.
        Order order = new Order();

        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }
    //== 비즈니스 로직==//
    /**
     * 주문 취소
     * */
    public void cancel(){ //현재 Order 엔티티 안에 있다.
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송 완료 된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem: orderItems){
            orderItem.cancel();//주문 취소는 전체 품목이 취소된다고 가정하자.

        }
    }
    //== 조회 로직==//
    /**
     * 전체 주문 가격 조회
     * */
    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem: orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }
}
