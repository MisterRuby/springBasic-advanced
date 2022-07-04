package hello.proxy.app.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 실무에서 컨트롤러를 인터페이스를 별도로 만들고 구현하는 경우는 거의 없다.
 *  - 예제를 위해서 생성
 *
 * @RequestMapping 와 @ResponseBody 애노테이션은 인터페이스에 사용해도 된다.
 */
@RequestMapping         // 스프링은 @Controller 또는 @RequestMapping 이 있어야 스프링 컨트롤러로 인식
@ResponseBody
public interface OrderControllerV1 {

    /**
     * 인터페이스 레벨에서는 @RequestParam 을 생략하지 않을 것은 권장한다.
     *  - 생략할 시 자바 버전, 스프링 버전에 따라 제대로 인식하지 않을 때가 있다.
     */
    @GetMapping("/v1/request")
    String request(@RequestParam("itemId") String itemId);

    @GetMapping("/v1/no-log")
    String noLog();
}
