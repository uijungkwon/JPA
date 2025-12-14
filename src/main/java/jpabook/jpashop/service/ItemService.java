package jpabook.jpashop.service;

import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jpabook.jpashop.domain.item.Item;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;//상품서비스는 리포지토리에 단순히 위임만 하는 역할을 함

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item); //특별히 추가 로직은 없음
    }
    public List<Item> findItems(){
        return itemRepository.findAll();
    }
    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
