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
@Import({AtAnnotationTest.AtAnnotationAspect.class})
@SpringBootTest
public class AtAnnotationTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("@annotation 테스트")
    void testMethod() {
        log.info("memberService Proxy={}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class AtAnnotationAspect {

        // @annotation : 지정한 어노테이션이 있는 메서드를 조인포인트로 선정
        @Around("@annotation(ruby.aop.member.annotation.MethodAop)")
        public Object atAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@annotation] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
