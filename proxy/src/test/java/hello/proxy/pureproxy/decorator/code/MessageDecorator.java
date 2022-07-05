package hello.proxy.pureproxy.decorator.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class MessageDecorator implements Component{

    // 실제 서버 객체
    private final Component component;
    @Override
    public String operation() {
        log.info("MessageDecorator 실행");

        String result = component.operation();
        // 실제 서버 객체를 통해 반환한 값을 변형하여 반환
        String decoResult = "*****" + result + "*****";

        log.info("MessageDecorator 적용 전={}, 적용 후={}", result, decoResult);

        return decoResult;
    }
}
