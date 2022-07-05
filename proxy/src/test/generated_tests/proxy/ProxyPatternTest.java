package proxy;

import proxy.code.CacheProxy;
import proxy.code.ProxyPatternClient;
import proxy.code.RealSubject;
import proxy.code.Subject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProxyPatternTest {

    @Test
    @DisplayName("프록시 패턴 미적용")
    void noProxyTest() {
        Subject realSubject = new RealSubject();
        ProxyPatternClient client = new ProxyPatternClient(realSubject);

        client.execute();
        client.execute();
        client.execute();
    }


    @Test
    @DisplayName("프록시 패턴 적용")
    void proxyTest() {
        Subject realSubject = new RealSubject();
        Subject cacheProxy = new CacheProxy(realSubject);
        ProxyPatternClient client = new ProxyPatternClient(cacheProxy);

        client.execute();
        client.execute();
        client.execute();
    }
}
