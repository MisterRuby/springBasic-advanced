package ruby.aop.exam.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ruby.aop.exam.annotation.Timer;

@Slf4j
@Service
public class CallServiceV0 {

    @Timer
    public void external() {
        log.info("call external");
        internal();     // 프록시가 호출하는 것이 아닌 실제 타겟 객체에서 호출하기 때문에 해당 호출은 어드바이스를 적용되지 않는다.
    }

    @Timer
    public void internal(){
        log.info("call internal");
    }
}
