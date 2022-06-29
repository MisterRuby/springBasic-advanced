package ruby.springbasic2.trace.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ruby.springbasic2.trace.strategy.code.strategy.*;

@Slf4j
public class ContextV1Test {
    @Test
    @DisplayName("전략 패턴 미적용")
    void strategyV0() {
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
    @DisplayName("전략 패턴 적용")
    void strategyV1() {
        Strategy strategyLogic1 = new StrategyLogic1();
        ContextV1 context1 = new ContextV1(strategyLogic1);
        context1.execute();

        Strategy strategyLogic2 = new StrategyLogic2();
        ContextV1 context2 = new ContextV1(strategyLogic2);
        context2.execute();
    }

    @Test
    @DisplayName("전략 패턴 적용 - 구현해야 될 메서드가 하나라면 람다를 통해 익명 내부 클래스 구현")
    void strategyV2() {
        ContextV1 context1 = new ContextV1(() -> log.info("비즈니스 1 실행"));
        context1.execute();

        ContextV1 context2 = new ContextV1(() -> log.info("비즈니스 2 실행"));
        context2.execute();
    }

    /**
     * 구현한 부분을 메서드 내부에서 호출, 즉 콜백함수를 구현해서 넘겨주는 것
     * - 자바스크립트와는 달리 자바에서는 메서드 자체는 객체가 아니므로 클래스로 감싸서 구현한다.
     */
    @Test
    @DisplayName("전략 패턴 적용 - 파라미터를 전달하는 시점에 익명 내부 클래스를 통해 구현")
    void strategyV3() {
        ContextV2 context = new ContextV2();
        context.execute(() -> log.info("비즈니스 1 실행"));
        context.execute(() -> log.info("비즈니스 2 실행"));
    }
}
