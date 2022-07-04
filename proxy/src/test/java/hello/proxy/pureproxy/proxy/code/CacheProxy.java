package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject{

    // 실제 서버 객체
    private Subject target;
    // 캐시 데이터
    private String cacheValue;

    public CacheProxy(Subject target) {
        this.target = target;
    }

    @Override
    public String operation() {
        log.info("프록시 호출!");

        // 캐시에 데이터가 있다면 operation 을 통해 데이터를 조회하지 않고 이전에 저장되어 있는 데이터를 반환
        if (cacheValue == null) cacheValue = target.operation();

        return cacheValue;
    }
}
