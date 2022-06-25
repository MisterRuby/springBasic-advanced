package ruby.springbasic2.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ruby.springbasic2.trace.TraceStatus;
import ruby.springbasic2.trace.helloTrace.HelloTraceV1;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV1 {
    private final HelloTraceV1 trace;

    public void save(String itemId) {

        TraceStatus status = trace.begin(this.getClass().getName() + ".save");
        try {
            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생");
            }
            // 저장 - 저장 되는데 걸리는 시간을 가정
            sleep(1000);

            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
