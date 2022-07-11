package ruby.aop.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public String orderItem(String itemId) {
        log.info("[OrderService] 실행");
        return orderRepository.save(itemId);
    }
}
