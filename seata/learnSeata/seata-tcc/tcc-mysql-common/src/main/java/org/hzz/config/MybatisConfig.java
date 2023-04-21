package org.hzz.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("org.hzz.mapper")
public class MybatisConfig {
}
