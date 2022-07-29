package ruby.aop.proxyvs;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import ruby.aop.member.MemberService;
import ruby.aop.member.MemberServiceImpl;

import java.util.Map;

@Slf4j
public class ProxyCastingTest {

    @Test
    @DisplayName("")
    void jdkProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // jdk 동적 프록시 사용
        proxyFactory.setProxyTargetClass(false);

        // 프록시를 인터페이스로 캐스팅
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

        // jdk 동적 프록시를 구현 클래스로 캐스팅 시도 실패
        Assertions.assertThatThrownBy(() -> {
            MemberServiceImpl memberServiceImplCasting = (MemberServiceImpl) memberServiceProxy;
        }).isInstanceOf(ClassCastException.class);
    }

    @Test
    @DisplayName("")
    void cglibProxy() {
        MemberServiceImpl target = new MemberServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // cglib 동적 프록시 사용
        proxyFactory.setProxyTargetClass(true);

        // 프록시를 인터페이스로 캐스팅
        MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();
        // cglib 동적 프록시를 구현 클래스로 캐스팅 성공
        MemberServiceImpl memberServiceImplCasting = (MemberServiceImpl) memberServiceProxy;
    }

}
