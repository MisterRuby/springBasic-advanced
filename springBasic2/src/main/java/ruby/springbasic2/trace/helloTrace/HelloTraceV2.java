package ruby.springbasic2.trace.helloTrace;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ruby.springbasic2.trace.TraceId;
import ruby.springbasic2.trace.TraceStatus;

@Slf4j
@Component
public class HelloTraceV2 {

    private static final Object START_PREFIX = "-->";
    private static final Object COMPLETE_PREFIX = "<--";
    private static final Object EX_PREFIX = "<X-";

    /**
     * 로그를 시작
     * @param message
     * @return
     */
    public TraceStatus begin(String message) {
        TraceId traceId = new TraceId();
        long startTime = System.currentTimeMillis();

        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTime, message);
    }


    public TraceStatus beginSync(TraceId prevTraceId, String message) {
        TraceId traceId = prevTraceId.createNextId();
        long startTime = System.currentTimeMillis();

        log.info("[{}] {}{}", traceId.getId(), addSpace(START_PREFIX, traceId.getLevel()), message);
        return new TraceStatus(traceId, startTime, message);
    }

    /**
     * 로그를 정상 종료
     * @param status
     */
    public void end (TraceStatus status) {
        complete(status, null);
    }

    /**
     * 로그를 예외 상황으로 종료
     * @param status
     * @param e
     */
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

    }

}
