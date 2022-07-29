package ruby.aop.exam.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import ruby.aop.exam.annotation.Timer;

@Slf4j
@Aspect
public class TimerAspect {

    @Around("@annotation(timer)")
    public void doTimer(ProceedingJoinPoint joinPoint, Timer timer) throws Throwable {
        long startTime = System.currentTimeMillis();
        log.info("{} start", joinPoint.getSignature());

        joinPoint.proceed();

        long endTime = System.currentTimeMillis();
        log.info("{} end, totalTime={}ms", joinPoint.getSignature(), endTime - startTime);
    }
}
