package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;//스프링부트 자동 주입

    public Long save(Member member){
        em.persist(member);
        return member.getId();//command와 쿼리를 구별해라.
    }
    public Member find(Long id){
        return em.find(Member.class, id);
    }
}
