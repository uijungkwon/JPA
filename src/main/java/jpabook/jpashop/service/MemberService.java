package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)//JPA에 활동은 트랙잭션 안에서 DB가 변경가능 해야함(스프링이 제공하는 것으로 사용)//리소스 많이 사용하지 말고 읽기만 해!(쓰기 작용이 필요없을 때)
//@AllArgsConstructor//생성자 의존관계 자동 주입
@RequiredArgsConstructor// final에 있는 필드만 갖고 생성자를 만들어준다.
public class MemberService {
    
    private final MemberRepository memberRepository;//컴파일 할 때 에러나는지 확인할 수 있음
    /*@Autowired//의존관계 자동 주입(생성자 주입)
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;//서비스 생성 시점에 리포지토리 주입
    }*/
    
    //회원 가입
    @Transactional
    public Long join(Member member){
        validateDulicateMember(member);//중복 회원 검증
        memberRepository.save(member);
        return member.getId();//id를 돌려줘야 아~ 이값이 저장 되었군, 확인
    }

    private void validateDulicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());//중복회원이 1 이상이면 예외처리로 할 수 있음
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 조회
    public List<Member> findMembers(){//public 메소드에 트랜잭셔널이 기본으로 적용됨
        return memberRepository.findAll();
    }
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
