package hello.proxy.pureproxy.concreteproxy;

import hello.proxy.pureproxy.concreteproxy.code.ConcreteClient;
import hello.proxy.pureproxy.concreteproxy.code.ConcreteLogic;
import hello.proxy.pureproxy.concreteproxy.code.TimeProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ConcreteProxyTest {

    @Test
    @DisplayName("프록시 미적용")
    void noProxy() {
        ConcreteLogic logic = new ConcreteLogic();
        ConcreteClient client = new ConcreteClient(logic);
        client.execute();
    }

    @Test
    @DisplayName("프록시 적용")
    void addProxy() {
        ConcreteLogic logic = new ConcreteLogic();
        ConcreteLogic timeProxy = new TimeProxy(logic);
        ConcreteClient client = new ConcreteClient(timeProxy);
        client.execute();
    }
}
