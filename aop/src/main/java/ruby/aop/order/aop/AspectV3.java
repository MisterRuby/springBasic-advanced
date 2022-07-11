package ruby.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV3 {

    @Pointcut("execution(* ruby.aop.order..*(..))")
    private void allOrder() {}          // Pointcut Signature

    // 모든 경로의 *Service 패턴의 클래스의 모든 메서드를 대상으로
    @Pointcut("execution(* *..*Service.*(..))")
    private void allService() {}

    // 지정한 Pointcut Signature 를 포인트컷으로 적용
    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }

    // 지정한 포인트컷 시그니처들의 모든 조건을 만족하는 대상에게 적용
    @Around("allOrder() && allService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            log.info("[Transaction Start] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[Transaction Commit] {}", joinPoint.getSignature());

            return result;
        } catch (Exception e) {
            log.info("[Transaction Rollback] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[Resource Release] {}", joinPoint.getSignature());
        }
    }
}
