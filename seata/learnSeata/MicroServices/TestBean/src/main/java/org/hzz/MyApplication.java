package org.hzz;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@SpringBootApplication
public class MyApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MyApplication.class);
        AnnotationConfigApplicationContext context = (AnnotationConfigApplicationContext)springApplication.run(args);
        Object bean1 = context.getBean("druidDataSource");
        Object bean2 = context.getBean("dataSourceProxy1");
        System.out.println(bean1 == bean2); // true
    }

    @Bean(name = "druidDataSource")
    @ConfigurationProperties("spring.datasource.druid")
    public DataSource druidDataSource() {
        System.out.println("create druidDataSource");
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    /**
     * 构造datasource代理对象，替换原来的datasource
     * @param druidDataSource
     * @return
     */
    @Primary
    @Bean("dataSourceProxy1")
    public DataSourceProxy dataSourceProxy(DataSource druidDataSource) {
        System.out.println("create dataSourceProxy");
        DataSourceProxy dataSourceProxy = new DataSourceProxy(druidDataSource);
        return dataSourceProxy;
    }

}
