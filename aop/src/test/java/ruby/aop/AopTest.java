package ruby.aop;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ruby.aop.order.OrderRepository;
import ruby.aop.order.OrderService;
import ruby.aop.order.aop.*;

@Slf4j
@SpringBootTest
//@Import(AspectV1.class)     // @Import 을 통해 해당 클래스를 빈으로 등록하는 것도 가능
//@Import(AspectV2.class)
//@Import(AspectV3.class)
//@Import(AspectV4Pointcut.class)
//@Import({AspectV5Order.LogAspect.class, AspectV5Order.TransactionAspect.class})
//@Import(AspectV6Advice.class)
public class AopTest {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;

    @Test
    @DisplayName("Aop 적용 확인")
    void aopInfo() {
        log.info("isAopProxy, orderRepository={}", AopUtils.isAopProxy(orderRepository));
        log.info("isAopProxy, orderService={}", AopUtils.isAopProxy(orderService));
    }

    @Test
    void success() {
        orderService.orderItem("itemA");
    }

    @Test
    void fail() {
        Assertions.assertThatThrownBy(() -> orderService.orderItem("ex"))
                .isInstanceOf(IllegalStateException.class);
    }
}
