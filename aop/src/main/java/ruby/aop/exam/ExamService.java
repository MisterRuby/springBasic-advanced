package ruby.aop.exam;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ruby.aop.exam.annotation.Timer;
import ruby.aop.exam.annotation.Trace;

@Service
@RequiredArgsConstructor

public class ExamService {

    private final ExamRepository examRepository;

//    @Trace
    @Timer
    public void request(String itemId) {
        examRepository.save(itemId);
    }
}
