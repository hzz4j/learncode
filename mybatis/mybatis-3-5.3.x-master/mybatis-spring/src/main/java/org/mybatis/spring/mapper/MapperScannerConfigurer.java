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
package org.mybatis.spring.mapper;

import static org.springframework.util.Assert.notNull;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

/**
 * BeanDefinitionRegistryPostProcessor that searches recursively starting from a base package for interfaces and
 * registers them as {@code MapperFactoryBean}. Note that only interfaces with at least one method will be registered;
 * concrete classes will be ignored.
 * <p>
 * This class was a {code BeanFactoryPostProcessor} until 1.0.1 version. It changed to
 * {@code BeanDefinitionRegistryPostProcessor} in 1.0.2. See https://jira.springsource.org/browse/SPR-8269 for the
 * details.
 * <p>
 * The {@code basePackage} property can contain more than one package name, separated by either commas or semicolons.
 * <p>
 * This class supports filtering the mappers created by either specifying a marker interface or an annotation. The
 * {@code annotationClass} property specifies an annotation to search for. The {@code markerInterface} property
 * specifies a parent interface to search for. If both properties are specified, mappers are added for interfaces that
 * match <em>either</em> criteria. By default, these two properties are null, so all interfaces in the given
 * {@code basePackage} are added as mappers.
 * <p>
 * This configurer enables autowire for all the beans that it creates so that they are automatically autowired with the
 * proper {@code SqlSessionFactory} or {@code SqlSessionTemplate}. If there is more than one {@code SqlSessionFactory}
 * in the application, however, autowiring cannot be used. In this case you must explicitly specify either an
 * {@code SqlSessionFactory} or an {@code SqlSessionTemplate} to use via the <em>bean name</em> properties. Bean names
 * are used rather than actual objects because Spring does not initialize property placeholders until after this class
 * is processed.
 * <p>
 * Passing in an actual object which may require placeholders (i.e. DB user password) will fail. Using bean names defers
 * actual object creation until later in the startup process, after all placeholder substitution is completed. However,
 * note that this configurer does support property placeholders of its <em>own</em> properties. The
 * <code>basePackage</code> and bean name properties all support <code>${property}</code> style substitution.
 * <p>
 * Configuration sample:
 *
 * <pre class="code">
 * {@code
 *   <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
 *       <property name="basePackage" value="org.mybatis.spring.sample.mapper" />
 *       <!-- optional unless there are multiple session factories defined -->
 *       <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
 *   </bean>
 * }
 * </pre>
 *
 * @author Hunter Presnall
 * @author Eduardo Macarron
 *
 * @see MapperFactoryBean
 * @see ClassPathMapperScanner
 */
/**
 * @vlog: 高于生活，源于生活
 * @desc: 类的描述:我们发现MapperScanerConfigurer实现了我们的 BeanDefinitionRegistryPostProcessor 这个类会在MapperScannerConfirurer
 *        ioc容器org.springframework.context.support.AbstractApplicationContext#refresh
 *        方法中的invokeBeanFactoryPostProcessors 进行调用其postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
 *        方法来注册我们的bean定义信息 且BeanDefinitionRegistryPostProcessor调用顺序有限于BeanFactoryPostProcessor类型接口
 *        1:BeanDefinitionRegistryPostProcessor:所有的bean定义信息将要被加载到容器中，Bean实例还没有被初始化 2:所有的Bean定义信息已经加载到容器中，但是Bean实例还没有被初始化
 * @author: xsls
 * @createDate: 2019/8/21 13:50
 * @version: 1.0
 */
public class MapperScannerConfigurer
    implements BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware, BeanNameAware {

  private String basePackage;

  private boolean addToConfig = true;

  private String lazyInitialization;

  private SqlSessionFactory sqlSessionFactory;

  private SqlSessionTemplate sqlSessionTemplate;

  private String sqlSessionFactoryBeanName;

  private String sqlSessionTemplateBeanName;

  private Class<? extends Annotation> annotationClass;

  private Class<?> markerInterface;

  private Class<? extends MapperFactoryBean> mapperFactoryBeanClass;

  private ApplicationContext applicationContext;

  private String beanName;

  private boolean processPropertyPlaceHolders;

  private BeanNameGenerator nameGenerator;

  /**
   * This property lets you set the base package for your mapper interface files.
   * <p>
   * You can set more than one package by using a semicolon or comma as a separator.
   * <p>
   * Mappers will be searched for recursively starting in the specified package(s).
   *
   * @param basePackage
   *          base package name
   */
  public void setBasePackage(String basePackage) {
    this.basePackage = basePackage;
  }

  /**
   * Same as {@code MapperFactoryBean#setAddToConfig(boolean)}.
   *
   * @param addToConfig
   *          a flag that whether add mapper to MyBatis or not
   * @see MapperFactoryBean#setAddToConfig(boolean)
   */
  public void setAddToConfig(boolean addToConfig) {
    this.addToConfig = addToConfig;
  }

  /**
   * Set whether enable lazy initialization for mapper bean.
   * <p>
   * Default is {@code false}.
   * </p>
   *
   * @param lazyInitialization
   *          Set the @{code true} to enable
   * @since 2.0.2
   */
  public void setLazyInitialization(String lazyInitialization) {
    this.lazyInitialization = lazyInitialization;
  }

  /**
   * This property specifies the annotation that the scanner will search for.
   * <p>
   * The scanner will register all interfaces in the base package that also have the specified annotation.
   * <p>
   * Note this can be combined with markerInterface.
   *
   * @param annotationClass
   *          annotation class
   */
  public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
    this.annotationClass = annotationClass;
  }

  /**
   * This property specifies the parent that the scanner will search for.
   * <p>
   * The scanner will register all interfaces in the base package that also have the specified interface class as a
   * parent.
   * <p>
   * Note this can be combined with annotationClass.
   *
   * @param superClass
   *          parent class
   */
  public void setMarkerInterface(Class<?> superClass) {
    this.markerInterface = superClass;
  }

  /**
   * Specifies which {@code SqlSessionTemplate} to use in the case that there is more than one in the spring context.
   * Usually this is only needed when you have more than one datasource.
   * <p>
   *
   * @deprecated Use {@link #setSqlSessionTemplateBeanName(String)} instead
   *
   * @param sqlSessionTemplate
   *          a template of SqlSession
   */
  @Deprecated
  public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
    this.sqlSessionTemplate = sqlSessionTemplate;
  }

  /**
   * Specifies which {@code SqlSessionTemplate} to use in the case that there is more than one in the spring context.
   * Usually this is only needed when you have more than one datasource.
   * <p>
   * Note bean names are used, not bean references. This is because the scanner loads early during the start process and
   * it is too early to build mybatis object instances.
   *
   * @since 1.1.0
   *
   * @param sqlSessionTemplateName
   *          Bean name of the {@code SqlSessionTemplate}
   */
  public void setSqlSessionTemplateBeanName(String sqlSessionTemplateName) {
    this.sqlSessionTemplateBeanName = sqlSessionTemplateName;
  }

  /**
   * Specifies which {@code SqlSessionFactory} to use in the case that there is more than one in the spring context.
   * Usually this is only needed when you have more than one datasource.
   * <p>
   *
   * @deprecated Use {@link #setSqlSessionFactoryBeanName(String)} instead.
   *
   * @param sqlSessionFactory
   *          a factory of SqlSession
   */
  @Deprecated
  public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  /**
   * Specifies which {@code SqlSessionFactory} to use in the case that there is more than one in the spring context.
   * Usually this is only needed when you have more than one datasource.
   * <p>
   * Note bean names are used, not bean references. This is because the scanner loads early during the start process and
   * it is too early to build mybatis object instances.
   *
   * @since 1.1.0
   *
   * @param sqlSessionFactoryName
   *          Bean name of the {@code SqlSessionFactory}
   */
  public void setSqlSessionFactoryBeanName(String sqlSessionFactoryName) {
    this.sqlSessionFactoryBeanName = sqlSessionFactoryName;
  }

  /**
   * Specifies a flag that whether execute a property placeholder processing or not.
   * <p>
   * The default is {@literal false}. This means that a property placeholder processing does not execute.
   *
   * @since 1.1.1
   *
   * @param processPropertyPlaceHolders
   *          a flag that whether execute a property placeholder processing or not
   */
  public void setProcessPropertyPlaceHolders(boolean processPropertyPlaceHolders) {
    this.processPropertyPlaceHolders = processPropertyPlaceHolders;
  }

  /**
   * The class of the {@link MapperFactoryBean} to return a mybatis proxy as spring bean.
   *
   * @param mapperFactoryBeanClass
   *          The class of the MapperFactoryBean
   * @since 2.0.1
   */
  public void setMapperFactoryBeanClass(Class<? extends MapperFactoryBean> mapperFactoryBeanClass) {
    this.mapperFactoryBeanClass = mapperFactoryBeanClass;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setBeanName(String name) {
    this.beanName = name;
  }

  /**
   * Gets beanNameGenerator to be used while running the scanner.
   *
   * @return the beanNameGenerator BeanNameGenerator that has been configured
   * @since 1.2.0
   */
  public BeanNameGenerator getNameGenerator() {
    return nameGenerator;
  }

  /**
   * Sets beanNameGenerator to be used while running the scanner.
   *
   * @param nameGenerator
   *          the beanNameGenerator to set
   * @since 1.2.0
   */
  public void setNameGenerator(BeanNameGenerator nameGenerator) {
    this.nameGenerator = nameGenerator;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void afterPropertiesSet() throws Exception {
    notNull(this.basePackage, "Property 'basePackage' is required");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
    // left intentionally blank
  }

  /**
   * {@inheritDoc}
   *
   * @since 1.0.2
   */
  /**
   * 方法实现说明: 我为我们IOC容器添加@MapperScan 注解扫描的所有的 mapper包下的bean定义
   *
   * @MapperScan=====>为我们容器中添加MapperScannerConfigurer bean定义 然后我们发现MapperScannerConfigurer
   * 实现了BeanDefinitionRegistryPostProcessor 所以在ioc容器refresh方法的时候会调用postProcessBeanDefinitionRegistry 来为我们容器
   * 中注册 @MapperScan 扫描的mapper包下的bean定义到容器中
   *
   * @author:xsls
   * @param registry
   *          bean定义注册器对象
   * @return:
   * @exception:
   * @date:2019/8/21 13:55
   */
  @Override
  public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
    /**
     * 若MapperScannerConfigurer属性的processPropertyPlaceHolders 为ture的时候,就进行 processPropertyPlaceHolders();
     */
    if (this.processPropertyPlaceHolders) {
      processPropertyPlaceHolders();
    }

    /**
     * 显示的new 一个ClassPathMapperScanner 包扫描器对象 这个对象是mybaits继承了spring的 ClassPathBeanDefinitionScanner
     * 为我们扫描器指定@MapperScan属性
     */
    ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);
    scanner.setAddToConfig(this.addToConfig);
    scanner.setAnnotationClass(this.annotationClass);
    scanner.setMarkerInterface(this.markerInterface);
    scanner.setSqlSessionFactory(this.sqlSessionFactory);
    scanner.setSqlSessionTemplate(this.sqlSessionTemplate);
    scanner.setSqlSessionFactoryBeanName(this.sqlSessionFactoryBeanName);
    scanner.setSqlSessionTemplateBeanName(this.sqlSessionTemplateBeanName);
    scanner.setResourceLoader(this.applicationContext);
    scanner.setBeanNameGenerator(this.nameGenerator);
    scanner.setMapperFactoryBeanClass(this.mapperFactoryBeanClass);
    if (StringUtils.hasText(lazyInitialization)) {
      scanner.setLazyInitialization(Boolean.valueOf(lazyInitialization));
    }

    /**
     * 扫描规则过滤
     */
    scanner.registerFilters();
    /**
     * 真正的去扫描我们@MapperScan指定的路径下的bean定义信息 先会去调用ClassPathMapperScanner.scan()方法
     */
    scanner.scan(
        StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
  }

  /**
   * 方法实现说明:BeanDefinitionRegistries 接口早于BeanFactoryPostProcessors BeanFactoryPostProcessors：注册我们的bean定义
   * BeanFactoryPostProcessors:修改我们的bean定义
   *
   * @author:xsls
   * @return:
   * @exception:
   * @date:2019/8/21 14:00
   */
  private void processPropertyPlaceHolders() {
    /**
     * 获取我们容器中的所有PropertyResourceConfigurer的组件 <bean id='propertyPlaceholderConfigurer' class=
     * "org.springframework.beans.factory.config.PropertyPlaceholderConfigurer"> <properties name='locations'> <list>
     * <value>config/mybaits.properties</value> </list> </properties> </bean>
     *
     * <bean class="org.mybatis.Spring.mapper.MapperScannerConfigurer">
     * <property name="basePackage" value="${basePackage}"/> <property name="processPropertyPlaceHolders" value="true">
     * 若这个为false的话,就不能解析${basePackage} </bean>
     *
     * 终极奥义:因为postProcessBeanDefinitionRegistry 是为我们注册bean定义的,但是注册bean定义的是偶 需要解析${basepackaage}
     * 但是我们PropertyResourceConfigurer类型的bean定义还没有实例化成bean对象 ，所以还不能提供解析${basepackaage}这个的能力，
     * 所有显示的设置processPropertyPlaceHolders为ture,就是想通过applicationContext.getBeansOfType(PropertyResourceConfigurer.class);
     * 提前吧PropertyResourceConfigurer组件实例化出来，从而来解析${basepackaage}
     *
     */
    Map<String, PropertyResourceConfigurer> prcs = applicationContext.getBeansOfType(PropertyResourceConfigurer.class);

    /**
     * 判断PropertyResourceConfigurer的集合不为空,而且applicationContext是ConfigurableApplicationContext
     *
     */
    if (!prcs.isEmpty() && applicationContext instanceof ConfigurableApplicationContext) {
      // 去容器中获取 MapperScannerConfiguere组件的名称
      BeanDefinition mapperScannerBean = ((ConfigurableApplicationContext) applicationContext).getBeanFactory()
          .getBeanDefinition(beanName);

      /**
       * 因为PropertyResourceConfigurer类没有暴露任务的方法来处理我们的property placeholder substitution
       * 说白了就是${basepackaage},有且只有提供一个BeanFactoryPostProcessor接口来处理我们的bean定义
       * 但是调用BeanFactoryPostProcessor.postProcessBeanFactory()方法需要一个beanFactory ,有些同学可以想到
       * 从外面传入一个Ioc的容器进来，但是这样会提前破坏我们ioc容器 所以在这里就进行局部的new 出一个ioc容器,然后把mapperScannerBean注册到临时的ioc容器中
       */
      DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
      factory.registerBeanDefinition(beanName, mapperScannerBean);

      /**
       * 这个时候，我们就可以大胆放心的处理临时的ioc容器factory 中的bean定义(说白了就是这个mapperScannerBean)
       */
      for (PropertyResourceConfigurer prc : prcs.values()) {
        prc.postProcessBeanFactory(factory);
      }

      /**
       * 处理完之后重新获取通过PropertyResourceConfigurer解析后的mapperScannerBean的属性
       */
      PropertyValues values = mapperScannerBean.getPropertyValues();

      // 更新MapperScannerBean 属性可能有${}包裹的字段
      this.basePackage = updatePropertyValue("basePackage", values);
      this.sqlSessionFactoryBeanName = updatePropertyValue("sqlSessionFactoryBeanName", values);
      this.sqlSessionTemplateBeanName = updatePropertyValue("sqlSessionTemplateBeanName", values);
      this.lazyInitialization = updatePropertyValue("lazyInitialization", values);
    }
    this.basePackage = Optional.ofNullable(this.basePackage).map(getEnvironment()::resolvePlaceholders).orElse(null);
    this.sqlSessionFactoryBeanName = Optional.ofNullable(this.sqlSessionFactoryBeanName)
        .map(getEnvironment()::resolvePlaceholders).orElse(null);
    this.sqlSessionTemplateBeanName = Optional.ofNullable(this.sqlSessionTemplateBeanName)
        .map(getEnvironment()::resolvePlaceholders).orElse(null);
    this.lazyInitialization = Optional.ofNullable(this.lazyInitialization).map(getEnvironment()::resolvePlaceholders)
        .orElse(null);
  }

  private Environment getEnvironment() {
    return this.applicationContext.getEnvironment();
  }

  private String updatePropertyValue(String propertyName, PropertyValues values) {
    PropertyValue property = values.getPropertyValue(propertyName);

    if (property == null) {
      return null;
    }

    Object value = property.getValue();

    if (value == null) {
      return null;
    } else if (value instanceof String) {
      return value.toString();
    } else if (value instanceof TypedStringValue) {
      return ((TypedStringValue) value).getValue();
    } else {
      return null;
    }
  }

}
