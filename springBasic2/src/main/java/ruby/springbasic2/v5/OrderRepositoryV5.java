package ruby.springbasic2.v5;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ruby.springbasic2.trace.callback.TraceTemplate;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV5 {
    private final TraceTemplate template;

    public void save(String itemId) {
        template.execute(this.getClass().getName() + ".save", () -> {
            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생");
            }
            // 저장 - 저장 되는데 걸리는 시간을 가정
            sleep(1000);
            return null;
        });
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
