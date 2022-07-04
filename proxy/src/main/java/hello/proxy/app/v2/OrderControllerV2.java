package hello.proxy.app.v2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ResponseBody
 *  - 컨트롤러를 스프링 빈으로 수동 등록 할 때에는 반드시 @ResponseBody 를 붙여주어야 한다.
 *  - @ResponseBody 를 붙이지 않으면 스프링 컨트롤러로서 역할을 수행할 수 없다.
 */
@Slf4j
@RequestMapping
@ResponseBody
@RequiredArgsConstructor
public class OrderControllerV2 {

    private final OrderServiceV2 orderService;

    @GetMapping("/v2/request")
    public String request(String itemId) {
        orderService.orderItem(itemId);
        return "ok";
    }

    @GetMapping("/v2/no-log")
    public String noLog() {
        return "ok";
    }
}
