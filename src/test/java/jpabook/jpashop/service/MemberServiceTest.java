package jpabook.jpashop.service;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class) //JUnit4 실행 시 스프링과 같이 실행하고 싶을 때 사용
@SpringBootTest//스프링 부트를 띄운 상태에서 테스트하고 싶을 때 사용(스프링 컨테이너에서 테스트 사용) -자동의존주입관계
@Transactional//테스트가 끝나면 다 롤백 해버림(테스트 데이터니까)
public class MemberServiceTest {
    /**
     * 회원 가입
     * */
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    @Rollback(false)
    public void 회원가입() throws Exception{
        //given(주어짐)
        Member member = new Member();
        member.setName("uijung");
        //when(동작)
        Long savedId = memberService.join(member);
        //then(결과)
        assertEquals(member, memberRepository.findOne(savedId));//db에 정상적으로 데이터가 저장됨을 파악
    }

    /**
     * 중복 회원 가입
     * */
    @Test(expected = IllegalStateException.class) //try -catch구문 생략 가능 이 덕분에
    public void 중복_회원_예외() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("kwon");

        Member member2 = new Member();
        member2.setName("kwon");
        //when
        memberService.join(member1);
        memberService.join(member2);//예외가 발생해야한다.

        //then
        fail("예외가 발생해야 한다.");//여기로 오면 실패
    }
}