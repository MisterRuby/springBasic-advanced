package ruby.aop.pointcut;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import ruby.aop.member.MemberServiceImpl;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void printMethod() {
        //
        /**
         *  execution 표현식
         *   - execution(접근제어자 반환타입 선언타입.메서드명(파라미터) 예외)
         *      - public java.lang.String ruby.aop.member.MemberServiceImpl.hello(java.lang.String)
         *          - 접근제어자: public
         *          - 반환타입: java.lang.String
         *          - 선언타입: ruby.aop.member.MemberServiceImpl
         *          - 메서드명: hello
         *          - 파라미터: java.lang.String
         *          - 예외: x
         */
        log.info("helloMethod= {}", helloMethod);
    }

    @Test
    @DisplayName("잘못된 접근제어자로 execution 표현식 match 테스트")
    void exactMatchByWrongModifier() {
        pointcut.setExpression("execution(private String ruby.aop.member.MemberServiceImpl.hello(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("잘못된 매개변수타입으로 execution 표현식 match 테스트")
    void exactMatchByWrongParameter() {
        pointcut.setExpression("execution(public String ruby.aop.member.MemberServiceImpl.hello(Integer))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    @DisplayName("execution 표현식 match 테스트")
    void exactMatch() {
        pointcut.setExpression("execution(public String ruby.aop.member.MemberServiceImpl.hello(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("최대로 생략한 포인트컷 표현식 - 모든 포인트컷과 match")
    void allMatch() {
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("접근제어자, 선언타입 생략. 반환타입 * 로 표시. 파라미터 ..으로 표시")
    void nameMatch() {
        pointcut.setExpression("execution(* hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("접근제어자, 선언타입 생략. 반환타입 * 로 표시. 메서드명 패턴으로 표시. 파라미터 ..으로 표시")
    void namePatternMatch() {
        pointcut.setExpression("execution(* *el*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


    @Test
    @DisplayName("접근제어자 생략. 반환타입 * 로 표시. 파라미터 ..으로 표시")
    void packageExactMatch1() {
        pointcut.setExpression("execution(* ruby.aop.member.MemberServiceImpl.hello(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("접근제어자 생략. 선언타입 일부, 반환타입, 메서드명 * 로 표시. 파라미터 ..으로 표시")
    void packageExactMatch2() {
        pointcut.setExpression("execution(* ruby.aop.member.*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     *  ..* 은 여러 단계를 한번에 표시
     *      - . : 정확하게 해당 위치의 패키지
     *      - .. : 해당 위치의 패키지와 그 하위 패키지도 포함
     */
    @Test
    @DisplayName("접근제어자 생략. 선언타입 일부, 반환타입, 메서드명 * 로 표시. 파라미터 ..으로 표시")
    void packageExactMatch3() {
        pointcut.setExpression("execution(* ruby.aop..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("접근제어자 생략. 선언타입 일부, 반환타입, 메서드명 * 로 표시. 파라미터 ..으로 표시")
    void packageExactMatch4() {
        pointcut.setExpression("execution(* ruby..*.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("부모타입으로 match")
    void typeSuperMatch() {
        pointcut.setExpression("execution(* ruby.aop.member.MemberService.*(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    /**
     * 부모타입으로 match 할 때는 부모타입에 선언된 메소드만 가능
     * @throws NoSuchMethodException
     */
    @Test
    @DisplayName("부모타입에 존재하지않는 메서드를 부모타입으로 match")
    void typeSuperMatchInternal() throws NoSuchMethodException {
        Method internalMethod = MemberServiceImpl.class.getMethod("hello", String.class);

        pointcut.setExpression("execution(* ruby.aop.member.MemberService.internal(..))");
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }


    @Test
    @DisplayName("파라미터만 match")
    void argsMatch() {
        pointcut.setExpression("execution(* *(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("하나의 파라미터 허용. 모든 타입 match")
    void argsMatchByOneParamAllType() {
        pointcut.setExpression("execution(* *(*))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


    @Test
    @DisplayName("여러개의 파라미터 허용. 모든 타입 match")
    void argsMatchByAllParamAllType() {
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("첫 파라미터 타입은 String, 그 뒤의 파라미터들은 개수, 타입 모두 허용")
    void argsMatchComplex() {
        pointcut.setExpression("execution(* *(String, ..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }
}
