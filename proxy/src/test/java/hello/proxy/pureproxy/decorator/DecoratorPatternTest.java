package hello.proxy.pureproxy.decorator;

import hello.proxy.pureproxy.decorator.code.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DecoratorPatternTest {

    @Test
    @DisplayName("데코레이터 패턴 미적용")
    void noDecorator() {
        Component component = new RealComponent();
        DecoratorPatternClient client = new DecoratorPatternClient(component);
        client.execute();
    }

    @Test
    @DisplayName("데코레이터 패턴 적용")
    void decorator1() {
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);

        DecoratorPatternClient client = new DecoratorPatternClient(messageDecorator);
        client.execute();
    }

    @Test
    @DisplayName("데코레이터 중첩 적용")
    void decorator2() {
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        Component timeDecorator = new TimeDecorator(messageDecorator);

        DecoratorPatternClient client = new DecoratorPatternClient(timeDecorator);
        client.execute();
    }
}
