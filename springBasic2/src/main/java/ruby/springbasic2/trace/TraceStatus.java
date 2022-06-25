package ruby.springbasic2.trace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 로그의 상태정보를 나타내는 클래스
 */
@AllArgsConstructor
@Getter @Setter
public class TraceStatus {

    private TraceId traceId;        // 트랜잭션 id와 level 정보를 가지고 있는 객체
    private Long startTimeMs;       // 로그의 시작 시간
    private String message;         // 시작시 사용한 메시지. 로그 종료시에도 이 메시지를 사용해서 출력
}
