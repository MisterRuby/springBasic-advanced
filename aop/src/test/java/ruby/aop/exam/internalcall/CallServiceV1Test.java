package ruby.aop.exam.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class CallServiceV1Test {

    @Autowired
    CallServiceV1 callServiceV1;

    @Test
    @DisplayName("")
    void external() {
        callServiceV1.external();
    }
}