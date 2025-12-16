package jpabook.jpashop.service;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//엔터티에서 생성한 비즈니스 로직과 리포지토리에 생성한것을 합쳐서 어떻게 기능을 할지 서비스역할 구현
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    //==의존 관계==//
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    //==주문==//
    //여기서 서비스의 역할은 단순하게 엔터티의 조회하고 연결하고 호출해주는 역할만하고, 리포지토리랑 연결시켜 저장해주는 역할을한다.
    @Transactional
    public Long order(Long memberId, Long itemId, int count){//주문 조회할 때 (회원아이디, 상품아이디, 주문수량) 정보를 보여준다
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //이번에는 Order에서만 delivery와 orderItem을 사용하기 때문에 cascade를 사용했지만, 그게 아니라면 즉 다른곳에서도 order을 참고할 수 있다면 cascade를 쓰지 않는게 좋다.

        //배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }
    //==취소==//
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔터티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
    }

    //==검색==//
    /*
    public List<Order> findOrders(OrderSearch orderSearch){
        return
    }*/
}
