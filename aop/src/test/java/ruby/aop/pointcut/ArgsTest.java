package ruby.aop.pointcut;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import ruby.aop.member.MemberServiceImpl;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class ArgsTest {

    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    private AspectJExpressionPointcut pointcut(String expression){
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        return pointcut;
    }

    @Test
    @DisplayName("파라미터 타입으로 매칭 -> 파라미터의 부모타입을 허용함")
    void args() {
        assertThat(pointcut("args(String)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args(Object)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args()").matches(helloMethod, MemberServiceImpl.class)).isFalse();
        assertThat(pointcut("args(*)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args(..)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args(String, ..)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args(Object, ..)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("파라미터 타입으로 매칭 -> 파라미터의 부모타입을 허용함")
    void argsVsExecution() {
        assertThat(pointcut("args(String)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("args(Object)").matches(helloMethod, MemberServiceImpl.class)).isTrue();

        // execution 은 파라미터의 부모타입을 허용하지 않음
        assertThat(pointcut("execution(* *(String))").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        assertThat(pointcut("execution(* *(Object))").matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }
}
