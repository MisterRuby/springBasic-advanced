package ruby.springbasic2.trace.logTrace;

import lombok.extern.slf4j.Slf4j;
import ruby.springbasic2.trace.TraceId;
import ruby.springbasic2.trace.TraceStatus;

@Slf4j
public class FieldLogTrace implements LogTrace{

    private static final Object START_PREFIX = "-->";
    private static final Object COMPLETE_PREFIX = "<--";
    private static final Object EX_PREFIX = "<X-";

    // traceId 동기. 동시성 이슈가 발생할 수 있음
    // 웹 애플리케이션에서 싱글톤으로 객체를 사용시 여러 요청이 동시에 들어올 경우 traceId 가 공유되어버림
    // 싱글톤 객체는 무상태성을 지켜야한다. -> 필드에 외부에서 입력받은 값을 저장해서는 안됨
    private TraceId traceIdHolder;

    @Override
    public TraceStatus begin(String message) {
        syncTraceID();
        long startTime = System.currentTimeMillis();
        log.info("[{}] {}{}", traceIdHolder.getId(), addSpace(START_PREFIX, traceIdHolder.getLevel()), message);
        return new TraceStatus(traceIdHolder, startTime, message);
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
        traceIdHolder = traceIdHolder == null ? new TraceId() : traceIdHolder.createNextId();
    }

    private void releaseTraceId() {
        traceIdHolder = traceIdHolder.isFirstLevel() ? null : traceIdHolder.createPrevId();
    }
}
