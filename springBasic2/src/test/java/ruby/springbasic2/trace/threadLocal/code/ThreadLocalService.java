package ruby.springbasic2.trace.threadLocal.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalService {

    private ThreadLocal<String> nameStore = new ThreadLocal<>();

    public String logic(String name) {
        log.info("์ ์ฅ name={} -> nameStore={}", name, nameStore.get());
        nameStore.set(name);
        sleep(1000);
        String savedValue = nameStore.get();
        log.info("์กฐํ nameStore={}", savedValue);
        nameStore.remove();
        return savedValue;
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
