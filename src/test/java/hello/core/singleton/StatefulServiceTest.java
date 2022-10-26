package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/*
* 최대한 단순히 설명하기 위해 실제 쓰레드는 사용하지 않음
* ThreadA가 사용자A 코드를 호출하고 ThreadB가 사용자 B 코드를  호출한다 가정하자
* 'StatefulService'의 'price' 필드는 공유되는 필드인데, 특정 클라이언트가 값을 변경ㄹ한다
* 사용자 A의 주문금액은 10000원이 되어야 하는데, 20000원이라는 결과가 나왔다.
* 실무에서 이런 경우를 종종 보는데, 이로인해 정말 해결하기 어려운 큰 문제들이 터진다
* 진짜 공유필드는 조심해야 한다. 스프링 빈은 항상 무상태(stateless)로 설계하자
* */
class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //ThraedA: A 사용자 10000원 주문
        int userAPrice = statefulService1.order("userA", 10000);

        //ThraedB: B 사용자 20000원 주문
        int userBPrice = statefulService2.order("userB", 20000);

        //ThraedA: 사용자 A 주문 금액 조회
//        int price = statefulService1.getPrice();
        System.out.println("price = " + userAPrice);

//        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}