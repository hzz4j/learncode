package org.hzz;

import org.hzz.usercreation.entity.impl.CommonUser;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.TypeFilter;

@SpringBootApplication
public class Application implements CommandLineRunner , ApplicationContextAware {

    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class);
    }

    @Bean
    public BeanFactoryPostProcessor beanFactoryPostProcessor() {
        return beanFactory -> {
            BeanDefinitionRegistry beanDefinitionRegistry = (BeanDefinitionRegistry)beanFactory;
            genericApplicationContext(beanDefinitionRegistry);
        };
    }

    private void genericApplicationContext(BeanDefinitionRegistry beanDefinitionRegistry){
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry);
        scanner.addIncludeFilter(removeModelAndEntitiesFilter());
        scanner.scan("org.hzz.usercreation");
    }

    static TypeFilter removeModelAndEntitiesFilter(){
        return (metadataReader, metadataReaderFactory) -> {
            String className = metadataReader.getClassMetadata().getClassName();
            return !className.endsWith("Model");
        };
    }

    @Override
    public void run(String... args) throws Exception {
        //  测试扫描到
        System.out.println("command line: "+this.applicationContext);
        System.out.println(applicationContext.getBean("commonUser", CommonUser.class));
//        System.out.println(applicationContext.getBean("userRequestModel", UserRequestModel.class));

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("========================");
    }
}
