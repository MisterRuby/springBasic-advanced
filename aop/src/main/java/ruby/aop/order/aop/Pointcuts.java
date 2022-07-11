package ruby.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

/**
 * 포인트컷들을 모아서 관리하기 위한 클래스
 */
public class Pointcuts {

    @Pointcut("execution(* ruby.aop.order..*(..))")
    public void allOrder() {}          // Pointcut Signature

    // 모든 경로의 *Service 패턴의 클래스의 모든 메서드를 대상으로
    @Pointcut("execution(* *..*Service.*(..))")
    public void allService() {}

    @Pointcut("allOrder() && allService()")
    public void orderAndService() {}
}
