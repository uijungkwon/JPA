package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository//스프링 빈으로 자동 등록 됨 (컴포넌트 스캔의 대상)
@RequiredArgsConstructor
public class MemberRepository {
    
    private final EntityManager em;

    /*public MemberRepository(EntityManager em){ //의존관계 주입 코드
        this.em = em;
    }*/
    public void save(Member member){
        em.persist(member);//멤버 객체를 넣고, 트랜잭션이 커밋되는 시점에 DB에 반영된다=> DB에 insert 쿼리가 날라간다.
    }
    public Member findOne(Long id){
        return em.find(Member.class, id);//(타입, pk)
    }
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class)//JPQL문법은 SQL과 거의 유사하지만, from의 대상이 테이블이 아니라 "엔터티"이다.
                .getResultList();//전체 회원 목록 조회
    }
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}
