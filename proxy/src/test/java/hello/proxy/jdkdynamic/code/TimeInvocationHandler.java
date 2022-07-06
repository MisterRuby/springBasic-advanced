package hello.proxy.jdkdynamic.code;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * InvocationHandler 를 구현
 *  - JDK 동적 프록시에 적용할 공통 로직을 개발하기 위함
 */
@Slf4j
public class TimeInvocationHandler implements InvocationHandler {

    // 동적 프록시가 호출할 대상(서버 객체)
    private final Object target;
    private final String[] patterns;

    public TimeInvocationHandler(Object target, String[] patterns) {
        this.target = target;
        this.patterns = patterns;
    }

    /**
     *
     * @param proxy     프록시 객체
     * @param method    메소드 메타 정보
     * @param args      호출할 메소드에 적용할 파라미터 배열
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 메서드 이름 필터 - 등록한 메서드에 포함되지 않는 메서드는 로그를 남기지 않고 실행
        String methodName = method.getName();
        if (!PatternMatchUtils.simpleMatch(patterns, methodName)) {
            return method.invoke(target, args);
        }

        log.info("TimeProxy 실행");
        long start = System.currentTimeMillis();
        Object result = method.invoke(target, args);
        long end = System.currentTimeMillis();
        log.info("TimeProxy 종료 resultTime={}ms", end - start);
        return result;
    }
}
