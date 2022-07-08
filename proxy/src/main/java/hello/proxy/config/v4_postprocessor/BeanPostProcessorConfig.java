package hello.proxy.config.v4_postprocessor;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.config.v4_postprocessor.postprocessor.PackageLogTracePostProcessor;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Slf4j
@Configuration
// v1 과 v2는 AppV1Config 와 AppV2Config 에서 빈으로 등록하므로 import
// -> v1 과 v2 의 클래스도 @Component 를 통해 빈으로 등록한다면 AppV1Config.class, AppV2Config.class 를 제거해도 됨 (v3 가 이에 해당함)
@Import({AppV1Config.class, AppV2Config.class})
public class BeanPostProcessorConfig {

    /**
     * 빈 후처리기를 빈으로 등록
     *  - 지정한 패키지 안의 클래스 타입의 빈을 등록할 때 빈 후처리기를 통해 프록시 객체를 대신 빈으로 등록
     * @param logTrace
     * @return
     */
    @Bean
    public PackageLogTracePostProcessor logTracePostProcessor(LogTrace logTrace) {
        return new PackageLogTracePostProcessor("hello.proxy.app", getAdvisor(logTrace));
    }

    private Advisor getAdvisor(LogTrace logTrace) {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(logTrace));
    }
}
