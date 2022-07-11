package ruby.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
public class AspectV5Order {

    /**
     * @Order 를 통해 어드바이스 적용 순서를 지정할 수 있다.
     *  - 해당 애노테이션은 클래스 단위로 적용된다. 메서드 단위로는 제대로 적용되지 않음에 주의할 것
     */
    @Aspect
    @Order(1)
    @Component
    public static class LogAspect {
        @Around("ruby.aop.order.aop.Pointcuts.allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[log] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }

    @Aspect
    @Order(2)
    @Component
    public static class TransactionAspect {
        @Around("ruby.aop.order.aop.Pointcuts.orderAndService()")
        public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

            try {
                // @Before
                log.info("[Transaction Start] {}", joinPoint.getSignature());

                Object result = joinPoint.proceed();

                // @After Returning
                log.info("[Transaction Commit] {}", joinPoint.getSignature());
                return result;
            } catch (Exception e) {
                // @After Throwing
                log.info("[Transaction Rollback] {}", joinPoint.getSignature());
                throw e;
            } finally {
                // @After
                log.info("[Resource Release] {}", joinPoint.getSignature());
            }
        }
    }

}
