package hello.proxy.proxyfactory;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ConcreteService;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("proxyFactory 활용 - 인터페이스가 있는 경우 JDK 동적 프록시 사용")
    void interfaceProxy() {
        ServiceInterface target = new ServiceImpl();
        // 프록시 팩토리 생성
        ProxyFactory proxyFactory = new ProxyFactory(target);
        // InvocationHandler 나 MethodInterceptor 에서 호출할 Advice 를 지정
        proxyFactory.addAdvice(new TimeAdvice());
        // 프록시 팩토리에서 프록시 객체 생성
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.save();

        // proxyFactory 로 생성한 프록시 객체이어야 AopUtils 로 확인 가능. 직접 만든 JDK 동적 프록시나 CGLIB 프록시는 AopUtils 로 확안 X
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();
    }

    @Test
    @DisplayName("proxyFactory 활용 - 구체 클래스만 있을 경우 CGLIB 사용")
    void concreteProxy() {
        ConcreteService target = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.call();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }



    @Test
    @DisplayName("proxyFactory 활용 - proxyTargetClass 옵션 사용시 인터페이스 유무에 상관없이 CGLIB 로 프록시 객체 생성")
    void proxyTargetClass() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // 해당 옵션 설정시 인터페이스 유무에 상관없이 CGLIB 를 통해 프록시 객체 생성
        // -> 인터페이스가 있어도 인터페이스가 아닌 구현 클래스를 상속받아서 프록시 객체를 생성하게 된다.
        proxyFactory.setProxyTargetClass(true);

        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.save();

        // proxyFactory 로 생성한 프록시 객체이어야 AopUtils 로 확인 가능. 직접 만든 JDK 동적 프록시나 CGLIB 프록시는 AopUtils 로 확안 X
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();
    }
}
