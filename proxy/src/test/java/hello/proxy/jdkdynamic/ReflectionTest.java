package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    @DisplayName("")
    void reflection0() {
        Hello target = new Hello();

        // 공통로직 A 시작
        log.info("start");
        String resultA = target.callA();    // 로그를 찍는 부분은 사실상 동일하고 해당 부분만 다름
        log.info("result={}", resultA);

        // 공통로직 B 시작
        log.info("start");
        String resultB = target.callA();
        log.info("result={}", resultB);
    }

    /**
     * Class.forName
     *  - 지정한 클래스의 메타정보를 획득
     *      - inner class 는 구분을 위해 $를 사용
     *
     * Class.getMethod
     *  - 지정한 메서드의 메타정보를 획득
     *
     * Method.invoke
     *  - Method 의 메타정보로 지정한 인스턴스의 메소드를 호출한다.
     *
     */
    @Test
    @DisplayName("")
    void reflection1() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 클래스 정보
        // - 대상이 inner class 라면 $로 지정한다.
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");

        Hello target = new Hello();
        // callA 메서드 정보
        Method methodCallA = classHello.getMethod("callA");
        Object result1 = methodCallA.invoke(target);    // target 객체의 callA 메서드를 호출
        log.info("result={}", result1);

        // callB 메서드 정보
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target);    // target 객체의 callA 메서드를 호출
        log.info("result={}", result2);
    }

    @Test
    @DisplayName("")
    void reflection2() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");
        Hello target = new Hello();

        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);
    }

    /**
     * reflection0 에서의 공통 로직 A, 공통 로직 B 를 한 번에 처리할 수 있는 통합된 공통 처리 로직
     *  - 기존에는 메서드 이름을 직접 호출했지만 여기서는 Method 라는 메타정보를 통해서 호출할 메서드 정보를 동적으로 제공받아 처리함
     *
     * 리플렉션을 사용해서 클래스와 메서드의 메타정보를 얻어 애플리케이션을 동적으로 유연하게 만들 수 있다.
     *  - 리플렉션 기술은 런타임에 동작
     *      -> 컴파일 시점에 오류를 잡을 수 없다는 단점이 존재함
     *      -> 따라서 리플렉션은 남용해서는 안됨!
     */
    <T> void dynamicCall(Method method, T target) throws InvocationTargetException, IllegalAccessException {
        log.info("start");
        Object result = method.invoke(target);
        log.info("result={}", result);
    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }

        public String callB() {
            log.info("callB");
            return "B";
        }
    }
}
