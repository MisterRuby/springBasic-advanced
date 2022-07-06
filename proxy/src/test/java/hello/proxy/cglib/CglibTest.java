package hello.proxy.cglib;

import hello.proxy.common.service.ConcreteService;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {

    @Test
    @DisplayName("ServiceInterface 동적 프록시 생성")
    void cglibByService() {
        ServiceInterface target = new ServiceImpl();

        // 동적 프록시 생성 - CGLIB 는 Enhancer 를 통해 프록시를 생성함
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ServiceInterface.class);
        enhancer.setCallback(new TimeMethodInterceptor(target));
        ServiceInterface proxy = (ServiceInterface)enhancer.create();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.find();
    }

    @Test
    @DisplayName("ConcreteService 클래스 동적 프록시 생성")
    void cglibByConcreteService() {
        // 인터페이스가 없는 클래스
        // CGLIB 는 자식 클래스를 동적으로 생성하게 때문에 기본 생성자가 필요하다.
        ConcreteService target = new ConcreteService();

        // 동적 프록시 생성 - CGLIB 는 Enhancer 를 통해 프록시를 생성함
        Enhancer enhancer = new Enhancer();
        // 프록시의 부모 클래스를 지정 - CGLIB 는 구체 클래스를 상속받아서 프록시를 생성할 수 있다.
        enhancer.setSuperclass(ConcreteService.class);
        enhancer.setCallback(new TimeMethodInterceptor(target));
        ConcreteService proxy = (ConcreteService)enhancer.create();
        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.call();
    }
}
