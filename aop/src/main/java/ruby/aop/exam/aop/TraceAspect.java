package ruby.aop.exam.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import ruby.aop.exam.annotation.Trace;

@Slf4j
@Aspect
public class TraceAspect {

    /**
     * 지정한 애노테이션이 붙은 메서드에 어드바이스를 적용
     */
    @Before("@annotation(trace)")
    public void doTrace(JoinPoint joinPoint, Trace trace) {
        Object[] args = joinPoint.getArgs();
        log.info("[trace] {} / args={}", joinPoint.getSignature(), args);
    }

    // @Transactional 역시 애노테이션 방식으로 된 AOP 이다.
}
