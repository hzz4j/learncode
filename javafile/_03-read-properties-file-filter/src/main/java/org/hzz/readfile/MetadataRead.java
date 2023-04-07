package org.hzz.readfile;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class MetadataRead {
    protected static final String PATH = "META-INF/spring-autoconfigure-metadata.properties";
    /**
     * 读取spring.factories文件
     */
    public static Properties loadSpringFactories(ClassLoader classLoader) {
        Properties properties = new Properties();
        try {
            Enumeration<URL> resources = classLoader.getResources(PATH);
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                properties.putAll(loadProperties(url));
            }
        }catch (IOException ex){
            throw new IllegalArgumentException("Unable to load factories from location [" +
                    PATH + "]", ex);
        }
        return properties;
    }

    public static Properties loadProperties(URL url) throws IOException {
        Properties properties = new Properties();
        properties.load(url.openStream());
        return properties;
    }
}
