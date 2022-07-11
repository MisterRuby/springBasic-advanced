package ruby.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    /**
     * @Pointcut
     *  - 포인트컷을 적용할 경로 및 대상을 지정
     *  - Pointcut Signature
     *      - 메서드 명 + 파라미터
     *  - 메서드의 반환타입은 void, 메서드 바디는 비워둔다.
     *  - 어드바이스 생성을 위한 메서드에서 해당 포인트컷 시그니처를 지정하면 설정한 경로 및 대상에 어드바이스를 적용
     *  - 해당 포인트컷 시그니처에 작성한 포인트컷 표현식을 여러 어드바이스 및 외부 클래스의 어드바이스에서 사용 가능
     */
    @Pointcut("execution(* ruby.aop.order..*(..))")
    private void allOrder() {}          // Pointcut Signature

    // 지정한 Pointcut Signature 를 포인트컷으로 적용
    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("[log] {}", joinPoint.getSignature());
        return joinPoint.proceed();
    }
}
