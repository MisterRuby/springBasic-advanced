package hello.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

@Slf4j
public class TimeAdvice implements MethodInterceptor {

    /**
     * 프록시 팩토리로 프록시를 생성하는 과정에서 이미 target 정보를 파라미터로 전달한 상태
     *  - invocation 안에 target 클래스의 정보가 모두 포함되어 있다.
     */
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("TimeProxy 실행");
        long start = System.currentTimeMillis();

        // invocation.proceed() : target 대상인 클래스를 호출하고 그 결과를 받는다.
        Object result = invocation.proceed();

        long end = System.currentTimeMillis();
        log.info("TimeProxy 종료 resultTime={}ms", end - start);

        return result;
    }
}
