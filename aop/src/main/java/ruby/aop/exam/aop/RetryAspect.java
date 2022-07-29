package ruby.aop.exam.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import ruby.aop.exam.annotation.Retry;

@Slf4j
@Aspect
public class RetryAspect {

    /**
     * 지정한 애노테이션이 붙은 메서드에 어드바이스를 적용
     */
    @Around("@annotation(retry)")
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        log.info("[retry] {} / retry={}", joinPoint.getSignature(), retry);

        int maxRetry = retry.maxValue();
        Exception exceptionHolder = null;

        // 메서드 호출에서 예외가 나더라도 Loop는 끝까지 돌게 된다.
        for (int retryCnt = 1; retryCnt < maxRetry; retryCnt++) {
            try {
                log.info("[retry] tryCnt = {}/{} 회", retryCnt, maxRetry);
                return joinPoint.proceed();
            } catch (Exception e) {
                exceptionHolder = e;
            }
        }

        throw exceptionHolder;
    }
}
