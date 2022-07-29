package ruby.aop.exam.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class CallServiceV3Test {

    @Autowired
    CallServiceV3 callServiceV3;

    @Test
    @DisplayName("")
    void external() {
        callServiceV3.external();
    }
}