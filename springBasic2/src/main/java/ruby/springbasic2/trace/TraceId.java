package ruby.springbasic2.trace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * 로그 추적을 위한 클래스
 */
@AllArgsConstructor
@Getter @Setter
public class TraceId {

    private String id;          // 트랜잭션 id  -  요청 개념의 트랜잭션. DB 의 트랜잭션이 아님
    private int level;          // 깊이

    public TraceId() {
        this.id = createId();
        this.level = 0;
    }

    private String createId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    /**
     * 같은 트랜잭션으로 깊이 증가
     * @return
     */
    public TraceId createNextId() {
        return new TraceId(id, level + 1);
    }

    /**
     * 같은 트랜잭션으로 깊이 감소
     * @return
     */
    public TraceId createPrevId() {
        return new TraceId(id, level - 1);
    }

    /**
     * 트랜잭션의 레벨이 처음 레벨인지 확인
     * @return
     */
    public boolean isFirstLevel() {
        return level == 0;
    }
}
