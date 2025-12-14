package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //한 테이블에 모든 정보 저장
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();//일부로 다대다 양방향관계로 생성

    //==비즈니스 로직==//
    /**
     * stock 증가
     * */
    public void addStock(int quantity){
        this.stockQuantity +=quantity;
    }
    /**
     * stock 감소
     * */
    public void removeStock(int quantity){ //객체지향 설계원칙과 응집도를 높이기 위해, 엔티티내부에 비즈니스 로직 설정(데이터 관련 로직을 함께 관리하는게 효과적)
        int restStock = this.stockQuantity - quantity;
        if( restStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}
