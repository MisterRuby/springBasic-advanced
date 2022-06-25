package ruby.springbasic2.trace.logTrace;

import ruby.springbasic2.trace.TraceStatus;

public interface LogTrace {

    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);
}
