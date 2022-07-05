package hello.proxy.config.v1_proxy.concrete_proxy;

import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderServiceV2 {
    private final OrderServiceV2 target;
    private final LogTrace logTrace;

    /**
     * 클래스 기반 프록시의 단점
     * - 자바 기본 문법에 의해 자식 클래스를 생성할 때에는 항상 부모 클래스의 생성자를 먼저 호출해주어야 한다.
     *  그러나 프록시 객체는 부모 클래스의 기능을 사용하지 않기 때문에 파라미터로 null을 입력해서 처리
     */
    public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace logTrace) {
        super(null);    // 부모의 기능을 사용하지 않으므로 null로 처리함
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderService.orderItem()");
            target.orderItem(itemId);
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
