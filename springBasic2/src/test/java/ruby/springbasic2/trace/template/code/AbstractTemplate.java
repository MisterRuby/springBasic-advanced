package ruby.springbasic2.trace.template.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTemplate {

    public void execute() {
        long startTime = System.currentTimeMillis();

        // 템플릿에서 변하는 부분 - 자식에서 구성
        call();

        long endTime = System.currentTimeMillis();
        log.info("resultTime={}", endTime - startTime);
    }

    protected abstract void call();
}
