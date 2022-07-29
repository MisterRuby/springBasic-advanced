package ruby.aop.pointcut;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import ruby.aop.member.MemberServiceImpl;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

public class WithinTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    @DisplayName("정확한 타입 지정")
    void withinExact() {
        pointcut.setExpression("within(ruby.aop.member.MemberServiceImpl)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("* 를 활용한 패턴 타입 지정")
    void withinStar() {
        pointcut.setExpression("within(ruby.aop.member.*Service*)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("특정 패키지 하위에 있는 모든 타입을 지정")
    void withinSubPackage() {
        pointcut.setExpression("within(ruby.aop..*)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("부모타입을 대상으로 지정 -> 부모 타압으로 지정할 수 없음")
    void withinSuperType() {
        pointcut.setExpression("within(ruby.aop.member.MemberService)");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }
}
