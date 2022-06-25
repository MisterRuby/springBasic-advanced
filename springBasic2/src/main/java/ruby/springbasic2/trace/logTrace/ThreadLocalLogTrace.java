package ruby.springbasic2.trace.logTrace;

import lombok.extern.slf4j.Slf4j;
import ruby.springbasic2.trace.TraceId;
import ruby.springbasic2.trace.TraceStatus;

@Slf4j
public class ThreadLocalLogTrace implements LogTrace{

    private static final Object START_PREFIX = "-->";
    private static final Object COMPLETE_PREFIX = "<--";
    private static final Object EX_PREFIX = "<X-";

    private final ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();

    @Override
    public TraceStatus begin(String message) {
        syncTraceID();
        long startTime = System.currentTimeMillis();
        TraceId traceId = traceIdHolder.get();

        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTime, message);
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }


    private String addSpace(Object prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append( i == level - 1 ? "|" + prefix : "|   ");
        }
        return sb.toString();
    }

    private void complete(TraceStatus status, Exception e) {
        long stopTime = System.currentTimeMillis();
        long resultTime = stopTime - status.getStartTimeMs();
        TraceId traceId = status.getTraceId();
        if (e == null) {
            log.info("[{}] {}{} time={}ms", traceId.getId(), addSpace(COMPLETE_PREFIX, traceId.getLevel()), status.getMessage(), resultTime);
        } else {
            log.info("[{}] {}{} time={}ms ex={}", traceId.getId(), addSpace(EX_PREFIX, traceId.getLevel()), status.getMessage(), resultTime, e.toString());
        }

        releaseTraceId();
    }

    private void syncTraceID() {
        TraceId traceId = traceIdHolder.get();
        traceId = traceId == null ? new TraceId() : traceId.createNextId();
        traceIdHolder.set(traceId);
    }

    private void releaseTraceId() {
        TraceId traceId = traceIdHolder.get();
        if (traceId.isFirstLevel()) {
            traceIdHolder.remove();         // destroy. 해당 쓰레드에서 값을 더 이상 사용하지 않는다면 제거해주어야 한다.
        } else {
            traceIdHolder.set(traceId);
        }
    }
}
