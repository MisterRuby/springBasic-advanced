package ruby.springbasic2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ruby.springbasic2.trace.callback.TraceTemplate;
import ruby.springbasic2.trace.logTrace.LogTrace;
import ruby.springbasic2.trace.logTrace.ThreadLocalLogTrace;

@Configuration
public class LogTraceConfig {

//    @Bean
//    public LogTrace logTrace() {
//        return new FieldLogTrace();
//    }

    @Bean
    public LogTrace logTrace() {
        return new ThreadLocalLogTrace();
    }

    @Bean
    public TraceTemplate traceTemplate() {
        return new TraceTemplate(logTrace());
    }
}