package ruby.springbasic2.trace.template.code;

import lombok.extern.slf4j.Slf4j;

/**
 * 템플릿 추상 클래스를 상속받아 call 메서드에 비즈니스 로직을 작성
 */
@Slf4j
public class SubClassLogic1 extends AbstractTemplate{
    @Override
    public void call() {
        log.info("비즈니스 로직1 실행");
    }
}
