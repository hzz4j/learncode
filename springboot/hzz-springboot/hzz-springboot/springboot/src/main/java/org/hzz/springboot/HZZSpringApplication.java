package org.hzz.springboot;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import java.util.Map;

public class HZZSpringApplication {
    public static void run(Class<?> clazz) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(clazz);
        context.refresh();

        WebServer webServer = getWebServer(context);
        webServer.start();
    }


    public static WebServer getWebServer(ApplicationContext context) {
        // key为beanName, value为Bean对象
        Map<String, WebServer>  webServers = context.getBeansOfType(WebServer.class);

        if(webServers.isEmpty()){
            throw new RuntimeException("没有找到WebServer的实现类");
        }else if(webServers.size() > 1) {
            throw new RuntimeException("找到多个WebServer的实现类");
        }else {
            return webServers.values().iterator().next();
        }
    }
}
