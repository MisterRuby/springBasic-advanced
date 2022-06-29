package ruby.springbasic2.trace.template.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SubClassLogic2 extends AbstractTemplate{
    @Override
    public void call() {
        log.info("비즈니스 로직2 실행");
    }
}
