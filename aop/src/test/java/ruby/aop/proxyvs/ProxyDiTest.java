package ruby.aop.proxyvs;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import ruby.aop.member.MemberService;
import ruby.aop.member.MemberServiceImpl;
import ruby.aop.proxyvs.code.ProxyDiAspect;

@Slf4j
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"})  // jdk 동적 프록시
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=true"})   // cglib 동적 프록시(기본설정)
@SpringBootTest
@Import(ProxyDiAspect.class)
public class ProxyDiTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberServiceImpl memberServiceImpl;

    @Test
    void testMethod() {
        log.info("memberService class={}", memberService.getClass());
        log.info("memberServiceImpl class={}", memberServiceImpl.getClass());
    }
}
