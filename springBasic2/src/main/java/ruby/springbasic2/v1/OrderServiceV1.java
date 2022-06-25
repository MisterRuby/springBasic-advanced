package ruby.springbasic2.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ruby.springbasic2.trace.TraceStatus;
import ruby.springbasic2.trace.helloTrace.HelloTraceV1;

@Service
@RequiredArgsConstructor
public class OrderServiceV1 {

    private final OrderRepositoryV1 orderRepository;
    private final HelloTraceV1 trace;

    public void orderItem(String itemId) {
        TraceStatus status = trace.begin(this.getClass().getName() + ".orderItem");
        try {
            orderRepository.save(itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
