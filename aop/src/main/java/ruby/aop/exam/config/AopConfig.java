package ruby.aop.exam.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ruby.aop.exam.aop.RetryAspect;
import ruby.aop.exam.aop.TimerAspect;
import ruby.aop.exam.aop.TraceAspect;

@Configuration
public class AopConfig {

//    @Bean
//    public TraceAspect traceAspect() {
//        return new TraceAspect();
//    }
//
//    @Bean
//    public RetryAspect retryAspect() {
//        return new RetryAspect();
//    }

    @Bean
    public TimerAspect timerAspect() {
        return new TimerAspect();
    }
}
