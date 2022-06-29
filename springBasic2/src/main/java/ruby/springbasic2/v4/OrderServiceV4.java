package ruby.springbasic2.v4;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ruby.springbasic2.trace.logTrace.LogTrace;
import ruby.springbasic2.trace.template.AbstractTemplate;

@Service
@RequiredArgsConstructor
public class OrderServiceV4 {

    private final OrderRepositoryV4 orderRepository;
    private final LogTrace trace;

    public void orderItem(String itemId) {
        AbstractTemplate<Void> template = new AbstractTemplate<>(trace) {
            @Override
            protected Void call() {
                orderRepository.save(itemId);
                return null;
            }
        };
        template.execute(this.getClass().getName() + ".orderItem");
    }
}
