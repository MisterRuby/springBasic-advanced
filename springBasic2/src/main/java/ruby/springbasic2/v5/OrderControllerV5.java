package ruby.springbasic2.v5;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ruby.springbasic2.trace.callback.TraceTemplate;

@RestController
@RequiredArgsConstructor
public class OrderControllerV5 {

    private final OrderServiceV5 orderService;
    private final TraceTemplate template;

    @GetMapping("/v5/request")
    public String request(String itemId) {
        return template.execute(this.getClass().getName() + ".request", () -> {
            orderService.orderItem(itemId);
            return "ok";
        });
    }
}
