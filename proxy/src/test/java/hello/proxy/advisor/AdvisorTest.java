package hello.proxy.advisor;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

public class AdvisorTest {

    @Test
    @DisplayName("어드바이저 생성")
    void advisorTest1() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // DefaultPointcutAdvisor - Advisor 인터페이스의 가장 기본적인 구현체. 생성자 매개변수로 하나의 포인트컷과 하나의 어드바이스를 지정
        // Pointcut.TRUE - 모든 호출에 advice 를 적용시키는 포인트컷. advisor 의 dafault 포인트컷으로 별도의 포인트컷을 지정하지 않으면 해당 포인트컷이 적용된다.
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    @Test
    @DisplayName("직접 만든 포인트컷을 어드바이저에 적용")
    void advisorTest2() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        // 포인트컷 결과가 false 인 호출이므로 advice 적용
        proxy.save();

        // 포인트컷 결과가 false 인 호출이므로 advice 미적용
        proxy.find();
    }

    @Test
    @DisplayName("스프링이 제공하는 포인트컷")
    void advisorTest3() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // NameMatchMethodPointcut - 메서드 이름을 기반으로 필터링하는 포인트컷. 내부에서 PatternMatchUtils 를 사용
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("save");

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        // 포인트컷 결과가 false 인 호출이므로 advice 적용
        proxy.save();

        // 포인트컷 결과가 false 인 호출이므로 advice 미적용
        proxy.find();
    }
}
