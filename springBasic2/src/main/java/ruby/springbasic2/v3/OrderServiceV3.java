package ruby.springbasic2.v3;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ruby.springbasic2.trace.TraceId;
import ruby.springbasic2.trace.TraceStatus;
import ruby.springbasic2.trace.helloTrace.HelloTraceV2;
import ruby.springbasic2.trace.logTrace.LogTrace;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {

    private final OrderRepositoryV3 orderRepository;
    private final LogTrace trace;

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
