package ruby.aop.exam.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;
import ruby.aop.exam.annotation.Timer;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallServiceV3 {

    private final InternalService internalService;

    @Timer
    public void external() {
        log.info("call external");
        internalService.internal();
    }

    // 별도의 클래스에 분리하여 호출하도록 변경
//    @Timer
//    public void internal(){
//        log.info("call internal");
//    }
}
