package org.hzz.springboot;

import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@ComponentScan
public @interface HZZSpringBootApplication {
}
