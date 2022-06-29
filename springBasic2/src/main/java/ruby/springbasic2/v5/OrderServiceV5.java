package ruby.springbasic2.v5;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ruby.springbasic2.trace.callback.TraceTemplate;

@Service
@RequiredArgsConstructor
public class OrderServiceV5 {

    private final OrderRepositoryV5 orderRepository;
    private final TraceTemplate template;

    public void orderItem(String itemId) {
        template.execute(this.getClass().getName() + ".orderItem", () -> {
            orderRepository.save(itemId);
            return null;
        });
    }
}
