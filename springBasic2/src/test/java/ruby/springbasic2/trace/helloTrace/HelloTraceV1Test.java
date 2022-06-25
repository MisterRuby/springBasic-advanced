package ruby.springbasic2.trace.helloTrace;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ruby.springbasic2.trace.TraceStatus;

class HelloTraceV1Test {

    @Test
    @DisplayName("로그 정상 종료")
    void begin_end() {
        // 로그를 추적하기 위해 trace 생성
        HelloTraceV1 trace = new HelloTraceV1();
        // 로그 추적 시작. begin 안에서 traceId 를 생성하고 traceStatus 에 트랜잭션Id와 메시지, 시작 시간을 남긴다.
        TraceStatus status = trace.begin("test");
        // 로그 정상 종료
        trace.end(status);
    }

    @Test
    @DisplayName("로그 예외 종료")
    void begin_exception() {
        HelloTraceV1 trace = new HelloTraceV1();
        TraceStatus status = trace.begin("test");
        trace.exception(status, new IllegalStateException());
    }
}