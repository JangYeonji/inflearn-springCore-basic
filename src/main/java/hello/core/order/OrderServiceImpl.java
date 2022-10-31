package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor    //final 붙은 생성자와 같은 의미
public class OrderServiceImpl implements OrderService{

//    final이 있으면 객체 생성 필수, 없으면 객체 생성 안됨
//    private final MemberRepository memberRepository = new MemoryMemberRepository();
//    private DiscountPolicy discountPolicy;

//    필드 주입. Appconfig의 orderService()의 return null 해야 함
//    @Autowired
    private final MemberRepository memberRepository;
//    @Autowired
    private final DiscountPolicy discountPolicy;

    //setter로 의존관계 주입. 위에꺼 final 빼야 함
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }


    //생성자로 의존관계 주입
    //@RequiredArgsConstructor 대신
//    @Autowired
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
