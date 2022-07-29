package ruby.aop.pointcut;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import ruby.aop.member.annotation.ClassAop;

@Slf4j
@SpringBootTest
public class AtTargetAndAtWithinTest {

    @Autowired
    Child child;

    @Test
    @DisplayName("@target 과 @within 비교")
    void AtTargetVsAtWithin() {
        log.info("child Proxy={}", child.getClass());
        child.childMethod();        // @target, @within 의 조인포인트로 선정된 메서드
        child.parentMethod();       // @target 의 조인포인트로만 선정된 메서드
    }


    static class Parent{
        public void parentMethod(){}
    }

    @ClassAop
    static class Child extends Parent {
        public void childMethod(){};
    }

    @Slf4j
    @Aspect
    static class AtTargetAndAtWithinAspect {

        // @target : 인스턴스 기준으로 모든 메서드의 조인포인트를 선정. 해당 인스턴스의 부모 타입의 메서드도 적용
        @Around("execution(* ruby.aop..*(..)) && @target(ruby.aop.member.annotation.ClassAop)")
        public Object atTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@target] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }

        // @within : 선택된 클래스 내부에 있는 메서드만 조인포인트로 선정. 부모 타입의 메서드는 적용하지 않음
        @Around("execution(* ruby.aop..*(..)) && @within(ruby.aop.member.annotation.ClassAop)")
        public Object atWithin(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@within] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }

    @TestConfiguration
    static class Config {
        @Bean
        public Child child() {
            return new Child();
        }
        @Bean
        public AtTargetAndAtWithinAspect atTargetAndAtWithinAspect() {
            return new AtTargetAndAtWithinAspect();
        }
    }
}
