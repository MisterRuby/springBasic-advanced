package ruby.aop.exam.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ruby.aop.exam.annotation.Timer;

@Slf4j
@Service
public class InternalService {

    @Timer
    public void internal(){
        log.info("call internal");
    }
}
