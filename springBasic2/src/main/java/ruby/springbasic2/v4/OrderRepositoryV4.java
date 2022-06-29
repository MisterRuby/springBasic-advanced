package ruby.springbasic2.v4;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ruby.springbasic2.trace.logTrace.LogTrace;
import ruby.springbasic2.trace.template.AbstractTemplate;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV4 {
    private final LogTrace trace;

    public void save(String itemId) {
        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                if (itemId.equals("ex")) {
                    throw new IllegalStateException("예외 발생");
                }
                // 저장 - 저장 되는데 걸리는 시간을 가정
                sleep(1000);
                return null;
            }
        };
        template.execute(this.getClass().getName() + ".save");
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
