package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanPostProcessorTest {

    @Test
    @DisplayName("빈 등록 테스트")
    void beanPostProcessorConfig() {
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanPostProcessorConfig.class);

        // beanA 이름으로 A 객체가 아닌 B 객체가 등록됨
        B beanB = context.getBean("beanA", B.class);
        beanB.helloB();

        // 빈으로 등록하지 않은 A
        Assertions.assertThrows(NoSuchBeanDefinitionException.class,
                () -> context.getBean(A.class));
    }

    @Slf4j
    @Configuration
    static class BeanPostProcessorConfig {

        @Bean(name = "beanA")
        public A a () {
            return new A();
        }

        @Bean
        public AtoBPostProcessor processor() {
            return new AtoBPostProcessor();
        }
    }

    @Slf4j
    static class AtoBPostProcessor implements BeanPostProcessor {

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName={} bean={}", beanName, bean);

            // 빈으로 등록하려는 객체의 타입이 A 일경우 B 객체를 생성하여 대신 등록. A 객체는 빈으로 등록되지 않음
            // -> 빈으로 등록하려는 객체를 프록시 객체로 교체하여 등록이 가능
            if (bean instanceof A) {
                return new B();
            }
            return bean;
        }
    }


    @Slf4j
    static class A {
        public void helloA() {
            log.info("hello A");
        }
    }

    @Slf4j
    static class B {
        public void helloB() {
            log.info("hello B");
        }
    }
}
