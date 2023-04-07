package org.hzz.springboot;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@ComponentScan
@Import(HZZImportSelector.class)
public @interface HZZSpringBootApplication {
}
