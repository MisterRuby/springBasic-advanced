package hello.proxy.config.v5_autoproxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

    /**
     * AOP 의 의존성 주입으로 인해 빈 후처리기는 이미 스프링 빈으로 등록되어 있는 상태 (build.gradle 확인)
     *  - 등록된 빈 후처리기가 해당 어드바이저를 적용하여 프록시 객체를 생성 및 빈으로 등록
     * @param logTrace
     * @return
     */
//    @Bean
    public Advisor advisor1(LogTrace logTrace) {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        // 프록시 객체를 생성할 때 타켓 객체에 지정한 MappedNames 에 포함된 메서드가 하나라도 있을 때에만 프록시 객체를 생성하여 빈으로 등록
        // -> 하나도 포함되지 않을 경우에는 프록시 객체가 아닌 기존 객체를 빈으로 등록
        //      -> 프록시 객체가 필요한 경우에만 생성하기 위함
        pointcut.setMappedNames("request*", "order*", "save*");

        return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(logTrace));
    }

//    @Bean
    public Advisor advisor2(LogTrace logTrace) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        // AspectJ 포인트컷 표현식으로 포인트컷 필터 범위를 설정
        // * : 모든 반환 타입
        // hello.proxy.app.. : 해당 패키지와 그 하위 패키지
        // *(..)
        //  -> * : 모든 메서드 이름을 허용
        //  -> (..) : 파라미터에 상관없이 적용
        pointcut.setExpression("execution(* hello.proxy.app..*(..))");

        return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(logTrace));
    }

    @Bean
    public Advisor advisor3(LogTrace logTrace) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        // AspectJ 포인트컷 표현식 조합
        pointcut.setExpression("execution(* hello.proxy.app..*(..)) && !execution(* hello.proxy.app..noLog(..))");

        return new DefaultPointcutAdvisor(pointcut, new LogTraceAdvice(logTrace));
    }
}
