package org.hzz.springboot;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class HZZSpringApplication {
    public static void run(Class<?> clazz) {
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(clazz);
        context.refresh();

        startTomcat(context);
    }

    public static void startTomcat(WebApplicationContext webApplicationContext) {

        Tomcat tomcat = new Tomcat();

        Server server = tomcat.getServer();
        Service service = server.findService("Tomcat");

        Connector connector = new Connector("HTTP/1.1");
        connector.setPort(8081);



        StandardEngine engine = new StandardEngine();
        engine.setDefaultHost("localhost");

        StandardHost host = new StandardHost();
        host.setName("localhost");

        String contextPath = "";
        Context context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());


        host.addChild(context);
        engine.addChild(host);
        service.setContainer(engine);
        service.addConnector(connector);

        tomcat.addServlet(contextPath, "dispatcher", new DispatcherServlet(webApplicationContext));
        context.addServletMappingDecoded("/*", "dispatcher");

        try {
            tomcat.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
