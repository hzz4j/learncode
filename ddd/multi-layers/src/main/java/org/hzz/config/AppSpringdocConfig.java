package org.hzz.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppSpringdocConfig {

    @Bean
    public OpenAPI applicationOpenApi() {
        Info info = new Info()
                .title("分层架构模型")
                .description("springboot+mybatis-plus+swagger3")
                .version("1.0.0")
                .license(new License().name("Apache 2.0").url("https://q10viking.github.io"))
                .contact(new Contact().name("q10viking").email("1193094618@qq.com"));
        return new OpenAPI()
                .info(info);
    }
}
