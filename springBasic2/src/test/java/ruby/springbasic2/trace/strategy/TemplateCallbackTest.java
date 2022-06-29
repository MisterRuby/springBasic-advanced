package ruby.springbasic2.trace.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ruby.springbasic2.trace.strategy.template.TimeLogTemplate;

@Slf4j
public class TemplateCallbackTest {

    @Test
    @DisplayName("템플릿 콜백 패턴")
    void callbackV1() {
        TimeLogTemplate template = new TimeLogTemplate();
        template.execute(() -> log.info("비즈니스 1 실행"));
        template.execute(() -> log.info("비즈니스 2 실행"));
    }
}
