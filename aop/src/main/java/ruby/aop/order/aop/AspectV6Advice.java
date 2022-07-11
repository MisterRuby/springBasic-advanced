package ruby.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
//@Component
public class AspectV6Advice {

    /**
     * @Around
     *  - 대상 메서드의 실행의 주변에서 실행. 대상 메서드의 실행 전후에 작업을 수행
     *  - @Before, @AfterReturning, @AfterThrowing, @After 의 역할을 모두 수행 가능
     *  - ProceedingJoinPoint 를 통해 proceed() 를 호출하여 다음 어드바이스나 타켓을 호출해야주어야 한다.
     *      - 조인 포인트의 실행여부를 선택할 수 있다.
     *      - proceed() 에 전달 값을 설정해 파라미터를 변경할 수 있다.
     *      - proceed() 를 여러번 호출할 수도 있다.
     *  - 반환 값 변환 가능
     *  - 예외 변환 가능
     *  - 트랜잭션처럼 try ~ catch ~ finally 가 모두 들어가는 구문을 처리할 수 있다.
     *  - 리턴 값을 조작할 경우가 아니라면 @Around 보다는 @Before, @AfterReturning 등으로 처리하는 것이 좋다.
     *      - proceed() 미작성 등의 실수를 줄여줌
     */
    @Around("ruby.aop.order.aop.Pointcuts.orderAndService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

        try {
            // @Before
            log.info("[Transaction Start] {}", joinPoint.getSignature());

            Object result = joinPoint.proceed();

            // @AfterReturning
            log.info("[Transaction Commit] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e) {
            // @AfterThrowing
            log.info("[Transaction Rollback] {}", joinPoint.getSignature());
            throw e;
        } finally {
            // @After
            log.info("[Resource Release] {}", joinPoint.getSignature());
        }
    }

    /**
     * 조인 포인트 실행 전 호출
     */
    @Before("ruby.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint) {
        log.info("[Before Start] {}", joinPoint.getSignature());
    }

    /**
     * 조인 포인트가 정상 완료 후 실행
     * - 대상 메서드의 반환타입과 returning 으로 지정한 값의 타입이 맞지 않으면 호출되지 않음
     */
    @AfterReturning(value = "ruby.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        log.info("[AfterReturning Start] {} return={}", joinPoint.getSignature(), result);
    }

    /**
     * 대상 메서드 실행이 예외를 던져서 종료될 떄 실행
     *  - 대상 메서드에서 발생한 예외타입과 throwing 으로 지정한 값의 예외타입이 맞지 않으면 호출되지 않음
     */
    @AfterThrowing(value = "ruby.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint, Exception ex) {
        log.info("[AfterThrowing Start] {} exception={}", joinPoint.getSignature(), ex.getMessage());
    }

    /**
     * 대상 메서드 실행이 종료되면 실행
     *  - 대상 메서드의 정상 종료 / 예외 발생과 상관없이 실행
     *  - 주로 리소스 해제 및 유사한 목적으로 사용
     */
    @After("ruby.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint) {
        log.info("[After Start] {}", joinPoint.getSignature());
    }
}
