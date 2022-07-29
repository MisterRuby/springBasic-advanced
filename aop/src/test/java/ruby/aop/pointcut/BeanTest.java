package ruby.aop.pointcut;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ruby.aop.order.OrderService;

@Slf4j
@Import({BeanTest.BeanAspect.class})
@SpringBootTest
public class BeanTest {

    @Autowired
    OrderService orderService;

    @Test
    @DisplayName("")
    void testMethod() {
        orderService.orderItem("itemA");
    }

    @Slf4j
    @Aspect
    static class BeanAspect {

        // bean : 지정한 빈에 있는 메소드들을 조인포인트로 선정
        @Around("bean(orderService) || bean(*Repository)")
        public Object doBean(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[bean] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
