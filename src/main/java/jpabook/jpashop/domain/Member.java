package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded //내장 타입 설정
    private Address address;

    @OneToMany(mappedBy = "member") //나는 연관관계의 거울, 저놈에 의해서 매핑된 애야
    private List<Order> orders = new ArrayList<>();
}
