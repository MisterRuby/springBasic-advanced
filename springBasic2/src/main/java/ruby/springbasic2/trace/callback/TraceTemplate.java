package ruby.springbasic2.trace.callback;

import lombok.extern.slf4j.Slf4j;
import ruby.springbasic2.trace.TraceStatus;
import ruby.springbasic2.trace.logTrace.LogTrace;

@Slf4j
public class TraceTemplate {

    private final LogTrace trace;

    public TraceTemplate(LogTrace trace) {
        this.trace = trace;
    }

    public <T> T execute(String message, TraceCallback<T> callback) {
        TraceStatus status = null;
        try {
            status = trace.begin(message);
            T result = callback.call();
            trace.end(status);
            return result;
        }catch (Exception e) {
            trace.exception(status, e);
            throw e;
        }


    }
}
