package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
* ex) 공연 기획자
* 애플리케이션의 실제 동작에 필요한 "구현 객체를 생성" 함
* 생성한 객체 인스턴스의 참조(레퍼런스)를 "생성자를 통해서 주입(연결)" 해줌
* */
@Configuration  //애플리케이션 구성 설정 정보
public class AppConfig {
    //빈 이름은 항상 다른 이름을 부여
    @Bean   //스프링 컨테이너에 등록. 빈 이름 {return new 빈 객체}
    public MemberService memberService(){
        return new MemberServiceImpl(getMemberRepository());
    }
    @Bean
    public MemoryMemberRepository getMemberRepository() {
        return new MemoryMemberRepository();
    }
    @Bean
    public OrderService orderService(){
        return new OrderServiceImpl(getMemberRepository(), discountPolicy());
    }

    //할인 정책은 이 부분만 변경해주면 됨
    @Bean
    public DiscountPolicy discountPolicy(){
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }
}
