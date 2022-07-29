package ruby.aop.pointcut;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ruby.aop.member.MemberService;
import ruby.aop.member.annotation.ClassAop;
import ruby.aop.member.annotation.MethodAop;

@Slf4j
@Import(ParameterTest.ParameterAspect.class)
@SpringBootTest
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("@parameter 테스트")
    void testMethod() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ParameterAspect {

        @Pointcut("execution(* ruby.aop.member.MemberServiceImpl.hello(String))")
        private void allMember(){}
        
        @Around("allMember()")
        public Object logArgs(ProceedingJoinPoint joinPoint) throws Throwable {
            Object arg1 = joinPoint.getArgs()[0];
            log.info("[logArgs1]{}, arg={}", joinPoint.getSignature(), arg1);
            return joinPoint.proceed();
        }

        // 표현식으로 지정한 파라미터를 어드바이스에서 파라미터로 받을 수 있다.
        // - 어드바이스에 파라미터를 기재하면 표현식에서 파라미터의 타입 대신 어드바이스에 기재한 파라미터 명을 대신 사용할 수 있다.
        @Around("allMember() && args(arg, ..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArgs2]{}, arg={}", joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        @Around("allMember() && args(arg, ..)")
        public Object logArgs3(ProceedingJoinPoint joinPoint, String arg) throws Throwable {
            log.info("[logArgs3]{}, arg={}", joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        // this 는 스프링 빈 객체(실제 객체 대신 등록되는 AOP 프록시)를 대상으로하는 조인 포인트
        @Around("allMember() && this(obj)")
        public Object thisArgs(ProceedingJoinPoint joinPoint, MemberService obj) throws Throwable {
            log.info("[this]{}, arg={}", joinPoint.getSignature(), obj.getClass());
            return joinPoint.proceed();
        }

        // target 은 프록시 객체가 가리키는 실제 객체를 대상으로 하는 조인포인트. this 와 target 은 부모타입을 허용함
        @Around("allMember() && target(obj)")
        public Object targetArgs(ProceedingJoinPoint joinPoint, MemberService obj) throws Throwable {
            log.info("[target]{}, arg={}", joinPoint.getSignature(), obj.getClass());
            return joinPoint.proceed();
        }

        // @target, @within 은 대상이되는 타입의 어노테이션을 가리킴
        @Around("allMember() && @target(obj)")
        public Object atTargetArgs(ProceedingJoinPoint joinPoint, ClassAop obj) throws Throwable {
            log.info("[@target]{}, arg={}", joinPoint.getSignature(), obj);
            return joinPoint.proceed();
        }

        @Around("allMember() && @within(obj)")
        public Object atWithinArgs(ProceedingJoinPoint joinPoint, ClassAop obj) throws Throwable {
            log.info("[@within]{}, arg={}", joinPoint.getSignature(), obj);
            return joinPoint.proceed();
        }

        // @target, @within 은 대상이되는 메소드 어노테이션을 가리킴
        @Around("allMember() && @annotation(obj)")
        public Object atAnnotationArgs(ProceedingJoinPoint joinPoint, MethodAop obj) throws Throwable {
            // 어노테이션에 저장한 값을 꺼내서 확인
            log.info("[@annotation]{}, arg={}", joinPoint.getSignature(), obj.value());
            return joinPoint.proceed();
        }
    }
}
