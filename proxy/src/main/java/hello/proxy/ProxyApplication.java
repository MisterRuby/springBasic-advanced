package hello.proxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @Import
 * 	- 해당 클래스를 스프링 빈으로 등록
 *
 * @SpringBootApplication
 *  - 기본적으로 해당 클래스가 있는 패키지 및 하위 패키지를 대상으로 컴포넌트 스캔을 한다.
 *  - 예제에서는 config 내의 클래스들을 @Import 를 통해 개별적으로 스프링 빈으로 등록하여 테스트 할 예정이므로
 *    scanBasePackages 값을 별도로 지정하여 config 패키지를 컴포넌트 스캔 대상에 포함시키지 않음
 */
//@Import(AppV1Config.class)
@Import({AppV1Config.class, AppV2Config.class})
@SpringBootApplication(scanBasePackages = "hello.proxy.app") //주의
public class ProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyApplication.class, args);
	}

}
