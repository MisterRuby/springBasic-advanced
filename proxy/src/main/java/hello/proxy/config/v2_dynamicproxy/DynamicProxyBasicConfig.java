package hello.proxy.config.v2_dynamicproxy;

import hello.proxy.app.v1.*;
import hello.proxy.config.v2_dynamicproxy.handler.LogTraceBasicHandler;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyBasicConfig {

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1 target = new OrderRepositoryImpl();
        LogTraceBasicHandler handler = new LogTraceBasicHandler(target, logTrace);
        return (OrderRepositoryV1) Proxy.newProxyInstance(OrderRepositoryV1.class.getClassLoader(), new Class[]{OrderRepositoryV1.class}, handler);
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace) {
        OrderServiceV1 target = new OrderServiceImpl(orderRepository(logTrace));
        LogTraceBasicHandler handler = new LogTraceBasicHandler(target, logTrace);
        return (OrderServiceV1) Proxy.newProxyInstance(OrderServiceV1.class.getClassLoader(), new Class[]{OrderServiceV1.class}, handler);
    }

    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) {
        OrderControllerV1 target = new OrderControllerImpl(orderService(logTrace));
//        return (OrderControllerV1) gerProxy(target, logTrace);
        LogTraceBasicHandler handler = new LogTraceBasicHandler(target, logTrace);
        return (OrderControllerV1) Proxy.newProxyInstance(OrderControllerV1.class.getClassLoader(), new Class[]{OrderControllerV1.class}, handler);
    }

    /**
     * Object 로 넘어오는 파라미터의 실제 타입이 인터페이스 타입이 아닌 구현 클래스의 타입이라면 런타임시 에러가 발생하므로 별도 메서드로 분리할 수 없다.
     */
//    private Object gerProxy(Object target, LogTrace logTrace) {
//        LogTraceBasicHandler handler = new LogTraceBasicHandler(target, logTrace);
//        return Proxy.newProxyInstance(target.getClass().getClassLoader(), new Class[]{target.getClass()}, handler);
//    }
}
