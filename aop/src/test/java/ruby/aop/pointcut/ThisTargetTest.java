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
import ruby.aop.member.MemberService;

@Slf4j
@Import({ThisTargetTest.ThisTargetAspect.class})
@SpringBootTest(properties = "spring.aop.proxy-target-class=false")     // proxy 를 JDK 동적 프록시를 통해서 생성
public class ThisTargetTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("")
    void testMethod() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ThisTargetAspect {
        @Around("this(ruby.aop.member.MemberService)")
        public Object doThisInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
        @Around("target(ruby.aop.member.MemberService)")
        public Object doTargetInterface(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-interface] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        /**
         *  proxy 를 JDK 동적 프록시를 통해서 생성할 시 해당 어드바이스를 대상으로 하지 못함
         *      - 프록시 객체가 MemberService 타입이기 때문
         *  proxy 를 CGLIB 프록시를 통해서 생성할 시 해당 어드바이스를 대상으로 할 수 있음
         *      - CGLIB 를 통해 생성한 프록시는 MemberServiceImpl 을 상속한 타입이기 때
         */
        @Around("this(ruby.aop.member.MemberServiceImpl)")
        public Object doThisImpl(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[this-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
        @Around("target(ruby.aop.member.MemberServiceImpl)")
        public Object doTargetImpl(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[target-impl] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
