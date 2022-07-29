package ruby.aop.exam.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ruby.aop.exam.annotation.Timer;

@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1;

    // setter 를 통해 프록시로 등록된 빈을 주입받는다.
    // 스프링부튼 2.6부터 순환참조를 기본적으로 금지하도록 변경됨 -> properties 파일에 설정을 추가해야함
    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1) {
        this.callServiceV1 = callServiceV1;
    }

    @Timer
    public void external() {
        log.info("call external");

        // 실제 타겟 객체가 아닌 setter 로 주입받는 프록시에서 호출
        callServiceV1.internal();
    }

    @Timer
    public void internal(){
        log.info("call internal");
    }
}
