package ruby.springbasic2.trace.template;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ruby.springbasic2.trace.template.code.AbstractTemplate;
import ruby.springbasic2.trace.template.code.SubClassLogic1;
import ruby.springbasic2.trace.template.code.SubClassLogic2;

@Slf4j
public class TemplateMethodTest {

    @Test
    @DisplayName("템플릿 메서드 패턴 미적용")
    void templateMethodV0() {
        logic1();
        logic2();
    }

    private void logic1() {
        long startTime = System.currentTimeMillis();

        log.info("비즈니스 로직1 실행");

        long endTime = System.currentTimeMillis();
        log.info("resultTime={}", endTime - startTime);
    }

    private void logic2() {
        long startTime = System.currentTimeMillis();

        log.info("비즈니스 로직2 실행");

        long endTime = System.currentTimeMillis();
        log.info("resultTime={}", endTime - startTime);
    }

    @Test
    @DisplayName("템플릿 메서드 패턴 적용 - 상속을 통한 패턴 적용")
    void templateMethodV1() {
        AbstractTemplate subClassLogic1 = new SubClassLogic1();
        subClassLogic1.execute();
        AbstractTemplate subClassLogic2 = new SubClassLogic2();
        subClassLogic2.execute();
    }

    /**
     * @Slf4j 를 사용하지 않고 추상 메서드가 하나라면 interface 로 바꾸고 람다를 통해 구현 가능
     */
    @Test
    @DisplayName("템플릿 메서드 패턴 적용 - 익명 내부 클래스")
    void templateMethodV2() {
        AbstractTemplate subClassLogic1 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직1 실행");
            }
        };
        subClassLogic1.execute();
        AbstractTemplate subClassLogic2 = new AbstractTemplate() {
            @Override
            protected void call() {
                log.info("비즈니스 로직2 실행");
            }
        };
        subClassLogic2.execute();
    }
}
