package hello.proxy.config.v1_proxy;

import hello.proxy.app.v1.*;
import hello.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OrderControllerInterfaceProxy (프록시 컨트롤러 객체)
 *  -> orderControllerImpl (실제 컨트롤러 객체)
 *      -> OrderServiceInterfaceProxy (프록시 서비스 객체)
 *          -> orderServiceImpl (실제 서비스 객체)
 *              -> OrderRepositoryInterfaceProxy (프록시 리포지토리 객체)
 *                  -> orderRepositoryImpl (실제 리포지토리 객체)
 *
 * 프록시 객체들이 실제 객체들을 대신해서 스프링 빈으로 등록
 *  - 실제 객체들은 프록시 객체들이 참조함으로서 프록시 객체가 호출될 때 내부에서 실제 객체들을 호출하게 된다.
 */
@Configuration
public class InterfaceProxyConfig {

    /**
     * 프록시 컨트롤러 객체를 스프링 빈으로 등록
     *  - 실제 컨트롤러 객체는 스프링 빈으로 등록하지 않음
     *  - 프록시 내부에서 실제 객체를 참조하고 있음
     * @param logTrace
     * @return
     */
    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) {
        OrderControllerImpl orderControllerImpl = new OrderControllerImpl(orderService(logTrace));
        return new OrderControllerInterfaceProxy(orderControllerImpl, logTrace);
    }

    /**
     * 프록시 서비스 객체를 스프링 빈으로 등록
     *  - 실제 서비스 객체는 스프링 빈으로 등록하지 않음
     * @param logTrace
     * @return
     */
    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace) {
        OrderServiceImpl orderServiceImpl = new OrderServiceImpl(orderRepository(logTrace));
        return new OrderServiceInterfaceProxy(orderServiceImpl, logTrace);
    }

    /**
     * 프록시 리포지토리 객체를 스프링 빈으로 등록
     *  - 실제 리포지토리 객체는 스프링 빈으로 등록하지 않음
     * @param logTrace
     * @return
     */
    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryImpl orderRepositoryImpl = new OrderRepositoryImpl();
        return new OrderRepositoryInterfaceProxy(orderRepositoryImpl, logTrace);
    }

}
