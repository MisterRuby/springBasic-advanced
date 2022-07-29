package ruby.aop.exam.internalcall;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ruby.aop.exam.annotation.Timer;

@Slf4j
@Service
@RequiredArgsConstructor
public class CallServiceV2 {

//    private final ApplicationContext applicationContext;
    private final ObjectProvider<CallServiceV2> callServiceProvider;

    @Timer
    public void external() {
        log.info("call external");

//        CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
        CallServiceV2 callServiceV2 = callServiceProvider.getObject();
        callServiceV2.internal();
    }

    @Timer
    public void internal(){
        log.info("call internal");
    }
}
