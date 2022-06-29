package ruby.springbasic2.trace.strategy.code.strategy;

import lombok.extern.slf4j.Slf4j;

/**
 * 템플릿 역할을 하는 클래스
 * - 전략패턴에서는 컨텍스트(문맥) 이라고 함.
 * - 스프링에서 의존관계 주입에서 사용하는 방식 또한 전략 패턴
 */
@Slf4j
public class ContextV1 {

    private Strategy strategy;

    public ContextV1(Strategy strategy) {
        this.strategy = strategy;
    }

    public void execute() {
        long startTime = System.currentTimeMillis();

        strategy.call();

        long endTime = System.currentTimeMillis();
        log.info("resultTime={}", endTime - startTime);
    }
}
