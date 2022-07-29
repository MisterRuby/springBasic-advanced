package ruby.aop.exam;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class ExamServiceTest {
    @Autowired
    ExamService examService;

    @Test
    void test() {
        for (int i = 0; i < 5; i++) {
            log.info("시도횟수 = {}회", i + 1);
            examService.request("data" + i);
        }
    }

    @Test
    void test2() {
        examService.request("data");
    }
}