package org.hzz.springboot;

import org.hzz.springboot.annotation.HZZConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebServiceAutoConfiguration implements AutoConfiguration{

    @Bean
    @HZZConditionalOnClass("org.apache.catalina.startup.Tomcat")
    public TomcatServer tomcatServer() {
        return new TomcatServer();
    }

    @Bean
    @HZZConditionalOnClass("org.eclipse.jetty.server.Server")
    public JettyServer jettyServer() {
        return new JettyServer();
    }
}
