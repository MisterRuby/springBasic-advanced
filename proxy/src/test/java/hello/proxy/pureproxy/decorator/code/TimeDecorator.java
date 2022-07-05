package hello.proxy.pureproxy.decorator.code;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class TimeDecorator implements Component{

    private final Component component;

    @Override
    public String operation() {
        log.info("TimeDecorator 실행");
        Long startTimeMs = System.currentTimeMillis();
        String result = component.operation();
        Long endTimeMs = System.currentTimeMillis();
        log.info("걸린 시간={}ms", endTimeMs - startTimeMs);

        return result;
    }
}
