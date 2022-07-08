package hello.proxy.config.v2_dynamicproxy.handler;

import hello.proxy.app.v1.*;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyFilterConfig {

    // 해당 패턴의 메서드가 호출될 때에만 로그를 적용.
    private static final String[] PATTERNS = {"request*", "order*", "save*"};

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {
        OrderRepositoryV1 target = new OrderRepositoryImpl();
        LogTraceFilterHandler handler = new LogTraceFilterHandler(target, logTrace, PATTERNS);
        return (OrderRepositoryV1) Proxy.newProxyInstance(OrderRepositoryV1.class.getClassLoader(), new Class[]{OrderRepositoryV1.class}, handler);
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace) {
        OrderServiceV1 target = new OrderServiceImpl(orderRepository(logTrace));
        LogTraceFilterHandler handler = new LogTraceFilterHandler(target, logTrace, PATTERNS);
        return (OrderServiceV1) Proxy.newProxyInstance(OrderServiceV1.class.getClassLoader(), new Class[]{OrderServiceV1.class}, handler);
    }

    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace) {
        OrderControllerV1 target = new OrderControllerImpl(orderService(logTrace));
        LogTraceFilterHandler handler = new LogTraceFilterHandler(target, logTrace, PATTERNS);
        return (OrderControllerV1) Proxy.newProxyInstance(OrderControllerV1.class.getClassLoader(), new Class[]{OrderControllerV1.class}, handler);
    }
}
