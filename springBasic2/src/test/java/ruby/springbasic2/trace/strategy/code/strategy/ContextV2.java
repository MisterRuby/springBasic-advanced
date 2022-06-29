package ruby.springbasic2.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 전략을 파라미터로 전달받는 방식
 * - 익명 구현 클래스를 통해 파라미터를 전달하는 시점에서 strategy 를 구현
 */
@Slf4j
public class ContextV2 {

    public void execute(Strategy strategy) {
        long startTime = System.currentTimeMillis();

        strategy.call();

        long endTime = System.currentTimeMillis();
        log.info("resultTime={}", endTime - startTime);
    }
}
