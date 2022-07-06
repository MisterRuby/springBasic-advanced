package hello.proxy.jdkdynamic;

import hello.proxy.jdkdynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

/**
 * AInterface 와 BInterface 의 프록시 클래스를 별도로 작성하지 않고 동적 프록시로 처리
 */
@Slf4j
public class JdkDynamicProxyTest {

    String[] PATTERNS = {"call"};

    @Test
    @DisplayName("동적 프록시 생성")
    void dynamicA() {
        AInterface target = new AImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target, PATTERNS);

        // 동적 프록시 객체 생성
        // AInterface 타입의 동적 프록시로서 타입이 동일할 뿐 AImpl 와 동일한 구성의 객체가 아니라는 것을 인지할 것!
        AInterface dynamicProxy = (AInterface) Proxy.newProxyInstance(AInterface.class.getClassLoader(), new Class[]{AInterface.class}, handler);

        // handler 의 invoke 가 호출됨
        //  -> 동적 프록시의 call 메서드는 타겟과 완벽히 동일한 것이 아니다!
        //  -> 동적 프록시의 call 메서드 내부는 handler 의 invoke 를 호출하게 되어 있다.
        dynamicProxy.call();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", dynamicProxy.getClass());
    }

    @Test
    @DisplayName("동적 프록시 생성")
    void dynamicB() {
        BInterface target = new BImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target, PATTERNS);

        BInterface dynamicProxy = (BInterface) Proxy.newProxyInstance(BInterface.class.getClassLoader(), new Class[]{BInterface.class}, handler);

        dynamicProxy.call();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", dynamicProxy.getClass());
    }
}
