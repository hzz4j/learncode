package org.hzz;

import org.hzz.interfaces.ApplicationContextInitializer;
import org.hzz.interfaces.DependsOnDatabaseInitializationDetector;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ReadFile {
    public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";
    static final Map<ClassLoader, Map<String, List<String>>> cache = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        List<String> results = loadFactoryNames(ApplicationContextInitializer.class,
                ReadFile.class.getClassLoader());
        results.forEach(System.out::println);

        System.out.println("==================================");

        results = loadFactoryNames(DependsOnDatabaseInitializationDetector.class,
                ReadFile.class.getClassLoader());
        results.forEach(System.out::println);
    }


    /**
     * 找到所有的实现类
     */
    public static List<String> loadFactoryNames(Class<?> factoryType, ClassLoader classLoader) {
        String factoryTypeName = factoryType.getName();
        return loadSpringFactories(classLoader).getOrDefault(factoryTypeName, Collections.emptyList());
    }

    /**
     * 读取spring.factories文件
     */
    private static Map<String, List<String>> loadSpringFactories(ClassLoader classLoader) {
        Map<String,List<String>> results = cache.get(classLoader);
        if (results != null) {
            return results;
        }

        results = new HashMap<>();
        try {
            Enumeration<URL> resources = classLoader.getResources(FACTORIES_RESOURCE_LOCATION);
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                Properties properties = loadProperties(url);
                for (Map.Entry<?, ?> entry : properties.entrySet()) {
                    String factoryTypeName = ((String) entry.getKey()).trim();
                    for (String factoryImplementationName : ((String) entry.getValue()).split(",")) {
                        String factoryName = factoryImplementationName.trim();
                        results.computeIfAbsent(factoryTypeName, k -> new ArrayList<>())
                                .add(factoryName);
                    }
                }
            }
        }catch (IOException ex){
            throw new IllegalArgumentException("Unable to load factories from location [" +
                    FACTORIES_RESOURCE_LOCATION + "]", ex);
        }
        cache.put(classLoader, results);
        return results;
    }

    public static Properties loadProperties(URL url) throws IOException{
        Properties properties = new Properties();
        properties.load(url.openStream());
        return properties;
    }
}
