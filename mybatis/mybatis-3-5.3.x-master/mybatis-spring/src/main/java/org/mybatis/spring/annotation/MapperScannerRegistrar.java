/**
 * Copyright ${license.git.copyrightYears} the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mybatis.spring.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

/**
 * A {@link ImportBeanDefinitionRegistrar} to allow annotation configuration of MyBatis mapper scanning. Using
 * an @Enable annotation allows beans to be registered via @Component configuration, whereas implementing
 * {@code BeanDefinitionRegistryPostProcessor} will work for XML configuration.
 *
 * @author Michael Lanyon
 * @author Eduardo Macarron
 * @author Putthiphong Boonphong
 *
 * @see MapperFactoryBean
 * @see ClassPathMapperScanner
 * @since 1.2.0
 */
/**
 * @vlog: 高于生活，源于生活
 * @desc: 类的描述:这个类是我们spring整合MyBatis的时候 在我们的主配置类上添加了@MapperScann注解 我们研究这个注解的时候
 * @Import(MapperScannerRegistrar.class) public @interface MapperScan 发现是一个组合注解@Import导入 MapperScannerRegistrar
 *                                       然后我们分析MapperScannerRegistrar组件是实现了ImportBeanDefinitionRegistrar 集合Spring
 *                                       bean定义的扫描源码，我们知道ImportBeanDefinitionRegistrar 在bean定义扫描的时候
 *                                       会调用registerBeanDefinitions()方法往我们的容器中添加bean定义对象到 beanDefinitionMap中
 * @author: xsls
 * @createDate: 2019/8/20 22:06
 * @version: 1.0
 */
public class MapperScannerRegistrar implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

  /**
   * {@inheritDoc}
   *
   * @deprecated Since 2.0.2, this method not used never.
   */
  @Override
  @Deprecated
  public void setResourceLoader(ResourceLoader resourceLoader) {
    // NOP
  }

  /**
   * {@inheritDoc}
   */
  /**
   * 方法实现说明:spring ioc在解析我们的住配置类的时候,会解析@MapperScann注解,然后 调用registerBeanDefinitions方法来进行注册我们的bean定义信息
   * 
   * @author:xsls
   * @param importingClassMetadata
   *          :主配置类的信息(包含了class信息)
   * @param registry
   *          bean定义注册器
   * @return:
   * @exception:
   * @date:2019/8/20 22:13
   */
  @Override
  public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

    /**
     * 从我们传入的配置类中来解析@MapperScan注解信息,然后吧MapperScan注解的属性转化为 AnnotationAttributes类型(Map类型)
     */
    AnnotationAttributes mapperScanAttrs = AnnotationAttributes
        .fromMap(importingClassMetadata.getAnnotationAttributes(MapperScan.class.getName()));
    /**
     * 若上一步解析出来的mapperScanAttrs不为空(说明配置类上加了@MapperScan注解)
     */
    if (mapperScanAttrs != null) {
      /**
       * 调用重写的方法registerBeanDefinitions generateBaseBeanName(importingClassMetadata, 0) 我们即将注册的bean定义的名称
       * com.tuling.config.MyBatisConfig#MapperScannerRegistrar#0
       */
      registerBeanDefinitions(mapperScanAttrs, registry, generateBaseBeanName(importingClassMetadata, 0));
    }
  }

  void registerBeanDefinitions(AnnotationAttributes annoAttrs, BeanDefinitionRegistry registry, String beanName) {

    /**
     * 创建bean定义构造器 通过够构造器来构建出我们的bean定义<MapperScannerConfigurer> 应用到的设计模式[建造者模式]
     */
    BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MapperScannerConfigurer.class);

    /**
     * 手动为我们MapperScannerConfigurer 开启processPropertyPlaceHolders属性为true 我们需要着重研究下MapperScannerConfigurer类的继承结构
     */
    builder.addPropertyValue("processPropertyPlaceHolders", true);

    /**
     * 为我们的MapperScannerConfigurer 解析我们@MapperScanner 指定扫描的的注解类型
     */
    Class<? extends Annotation> annotationClass = annoAttrs.getClass("annotationClass");
    if (!Annotation.class.equals(annotationClass)) {
      builder.addPropertyValue("annotationClass", annotationClass);
    }

    /**
     * 是否配置了标记接口
     */
    Class<?> markerInterface = annoAttrs.getClass("markerInterface");
    if (!Class.class.equals(markerInterface)) {
      builder.addPropertyValue("markerInterface", markerInterface);
    }

    /**
     * 设置MapperScannerConfigurer的beanName 生成器对象
     */
    Class<? extends BeanNameGenerator> generatorClass = annoAttrs.getClass("nameGenerator");
    if (!BeanNameGenerator.class.equals(generatorClass)) {
      builder.addPropertyValue("nameGenerator", BeanUtils.instantiateClass(generatorClass));
    }

    /**
     * 解析@MapperScan注解属性MapperFactoryBean 设置到MapperScannerConfigurer 声明一个自定义的MapperFactoryBean 返回一个代理对象
     */
    Class<? extends MapperFactoryBean> mapperFactoryBeanClass = annoAttrs.getClass("factoryBean");
    if (!MapperFactoryBean.class.equals(mapperFactoryBeanClass)) {
      builder.addPropertyValue("mapperFactoryBeanClass", mapperFactoryBeanClass);
    }

    /**
     * 解析@MapperScan 的sqlSessionTemplateRef到底使用是哪个sqlSessionTemplate 设置到MapperScannerConfigurer 多数据源的情况下需要指定
     */
    String sqlSessionTemplateRef = annoAttrs.getString("sqlSessionTemplateRef");
    if (StringUtils.hasText(sqlSessionTemplateRef)) {
      builder.addPropertyValue("sqlSessionTemplateBeanName", annoAttrs.getString("sqlSessionTemplateRef"));
    }

    /**
     * 解析@MapperScan的sqlSessionFactoryRef属性 设置到 MapperScannerConfigurer 多数据情况下的话 ，需要指定使用哪个 sqlSessionFactory
     */
    String sqlSessionFactoryRef = annoAttrs.getString("sqlSessionFactoryRef");
    if (StringUtils.hasText(sqlSessionFactoryRef)) {
      builder.addPropertyValue("sqlSessionFactoryBeanName", annoAttrs.getString("sqlSessionFactoryRef"));
    }

    /**
     * 解析@MapperScan 扫描的的包或者是class对象
     */
    List<String> basePackages = new ArrayList<>();
    basePackages.addAll(
        Arrays.stream(annoAttrs.getStringArray("value")).filter(StringUtils::hasText).collect(Collectors.toList()));

    basePackages.addAll(Arrays.stream(annoAttrs.getStringArray("basePackages")).filter(StringUtils::hasText)
        .collect(Collectors.toList()));

    basePackages.addAll(Arrays.stream(annoAttrs.getClassArray("basePackageClasses")).map(ClassUtils::getPackageName)
        .collect(Collectors.toList()));

    /**
     * 指定MapperScannerConfigurer 是否为懒加载
     */
    String lazyInitialization = annoAttrs.getString("lazyInitialization");
    if (StringUtils.hasText(lazyInitialization)) {
      builder.addPropertyValue("lazyInitialization", lazyInitialization);
    }

    builder.addPropertyValue("basePackage", StringUtils.collectionToCommaDelimitedString(basePackages));

    /**
     * 为我们的容器中注册了MapperScannerConfigurer的接口
     */
    registry.registerBeanDefinition(beanName, builder.getBeanDefinition());

  }

  /**
   * 方法实现说明:生成我们bean定义的名称
   * 
   * @author:xsls
   * @param importingClassMetadata
   *          传入的配置类
   * @param index
   *          默认传入0
   * @return: com.tuling.config.MyBatisConfig#MapperScannerRegistrar#0
   * @exception:
   * @date:2019/8/20 22:29
   */
  private static String generateBaseBeanName(AnnotationMetadata importingClassMetadata, int index) {
    return importingClassMetadata.getClassName() + "#" + MapperScannerRegistrar.class.getSimpleName() + "#" + index;
  }

  /**
   * A {@link MapperScannerRegistrar} for {@link MapperScans}.
   *
   * @since 2.0.0
   */
  static class RepeatingRegistrar extends MapperScannerRegistrar {
    /**
     * {@inheritDoc}
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
      AnnotationAttributes mapperScansAttrs = AnnotationAttributes
          .fromMap(importingClassMetadata.getAnnotationAttributes(MapperScans.class.getName()));
      if (mapperScansAttrs != null) {
        AnnotationAttributes[] annotations = mapperScansAttrs.getAnnotationArray("value");
        for (int i = 0; i < annotations.length; i++) {
          registerBeanDefinitions(annotations[i], registry, generateBaseBeanName(importingClassMetadata, i));
        }
      }
    }
  }

}
