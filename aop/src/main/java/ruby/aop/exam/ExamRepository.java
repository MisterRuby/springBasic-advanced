package ruby.aop.exam;

import org.springframework.stereotype.Repository;
import ruby.aop.exam.annotation.Retry;
import ruby.aop.exam.annotation.Timer;
import ruby.aop.exam.annotation.Trace;

@Repository
public class ExamRepository {

    private static int seq = 0;

    /**
     * 5번 중 1번은 요청이 실패한다고 가정
     * @param itemId
     * @return
     */
//    @Trace
//    @Retry(maxValue = 4)
    @Timer
    public String save(String itemId) {
        seq++;

        if (seq % 5 == 0) {
            throw new IllegalStateException("저장 실패!");
        }
        return "ok";
    }
}
