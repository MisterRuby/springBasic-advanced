package ruby.springbasic2.v2;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ruby.springbasic2.trace.TraceId;
import ruby.springbasic2.trace.TraceStatus;
import ruby.springbasic2.trace.helloTrace.HelloTraceV2;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepository;
    private final HelloTraceV2 trace;

    public void orderItem(TraceId prevTraceId, String itemId) {
//        TraceStatus status = trace.begin(this.getClass().getName() + ".orderItem");
        TraceStatus status = trace.beginSync(prevTraceId, this.getClass().getName() + ".orderItem");
        try {
            orderRepository.save(status.getTraceId(), itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }
    }
}
