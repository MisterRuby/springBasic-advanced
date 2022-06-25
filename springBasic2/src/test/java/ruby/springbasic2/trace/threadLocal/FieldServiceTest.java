package ruby.springbasic2.trace.threadLocal;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ruby.springbasic2.trace.threadLocal.code.FieldService;

@Slf4j
public class FieldServiceTest {

    private FieldService fieldService = new FieldService();

    @Test
    @DisplayName("동시성 문제가 앖는 경우")
    void field() {
        log.info("main start");

        // Runnable 의 run 추상메서드를 람다로 구현
        // Runnable 은 run 메서드 하나를 가진 FunctionalInterface
        Runnable userA = () -> fieldService.logic("userA");
        Runnable userB = () -> fieldService.logic("userB");

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        // run 메서드를 호출, 실행
        threadA.start();

        // threadB 가 시작되기 전에 threadA 가 완전히 끝날 수 있도록 함. 동시성 문제 X
        sleep(3000);

        threadB.start();

        sleep(3000);
        log.info("main end");
    }

    @Test
    @DisplayName("동시성 문제가 발생하는 경우")
    void field2() {
        log.info("main start");

        Runnable userA = () -> fieldService.logic("userA");
        Runnable userB = () -> fieldService.logic("userB");

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
        sleep(500);
        threadB.start();

        sleep(3000);
        log.info("main end");
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
