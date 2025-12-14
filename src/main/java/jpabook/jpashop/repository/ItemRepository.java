package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import jpabook.jpashop.domain.item.Item;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null) //없으면 새로운 객체로 생성
            em.persist(item);
        else em.merge(item);//아이템이 이미 존재(업데이트 느낌)
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }
    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
