package ruby.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 빈 후처리에 적용시키기 위해 해당 Aspect 를 빈으로 등록시켜줘야 함
 *  - 해당 Aspect 를 통해 포인트컷과 어드바이스가 생성
 */
@Slf4j
@Aspect
public class AspectV1 {

    /**
     * @Around
     *  - execution 으로 설정한 값으로 포인트컷 필터링
     * 해당 메서드가 advice 의 역할을 담당
     */
    @Around("execution(* ruby.aop.order..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }
}
