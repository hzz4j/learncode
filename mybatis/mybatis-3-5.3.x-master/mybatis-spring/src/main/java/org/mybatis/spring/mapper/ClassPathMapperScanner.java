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

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

/**
 * A {@link ClassPathBeanDefinitionScanner} that registers Mappers by {@code basePackage}, {@code annotationClass}, or
 * {@code markerInterface}. If an {@code annotationClass} and/or {@code markerInterface} is specified, only the
 * specified types will be searched (searching for all interfaces will be disabled).
 * <p>
 * This functionality was previously a private class of {@link MapperScannerConfigurer}, but was broken out in version
 * 1.2.0.
 *
 * @author Hunter Presnall
 * @author Eduardo Macarron
 *
 * @see MapperFactoryBean
 * @since 1.2.0
 */
public class ClassPathMapperScanner extends ClassPathBeanDefinitionScanner {

  private static final Logger LOGGER = LoggerFactory.getLogger(ClassPathMapperScanner.class);

  private boolean addToConfig = true;

  private boolean lazyInitialization;

  private SqlSessionFactory sqlSessionFactory;

  private SqlSessionTemplate sqlSessionTemplate;

  private String sqlSessionTemplateBeanName;

  private String sqlSessionFactoryBeanName;

  private Class<? extends Annotation> annotationClass;

  private Class<?> markerInterface;

  private Class<? extends MapperFactoryBean> mapperFactoryBeanClass = MapperFactoryBean.class;

  public ClassPathMapperScanner(BeanDefinitionRegistry registry) {
    super(registry, false);
  }

  public void setAddToConfig(boolean addToConfig) {
    this.addToConfig = addToConfig;
  }

  public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
    this.annotationClass = annotationClass;
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
  public void setLazyInitialization(boolean lazyInitialization) {
    this.lazyInitialization = lazyInitialization;
  }

  public void setMarkerInterface(Class<?> markerInterface) {
    this.markerInterface = markerInterface;
  }

  public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
    this.sqlSessionFactory = sqlSessionFactory;
  }

  public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
    this.sqlSessionTemplate = sqlSessionTemplate;
  }

  public void setSqlSessionTemplateBeanName(String sqlSessionTemplateBeanName) {
    this.sqlSessionTemplateBeanName = sqlSessionTemplateBeanName;
  }

  public void setSqlSessionFactoryBeanName(String sqlSessionFactoryBeanName) {
    this.sqlSessionFactoryBeanName = sqlSessionFactoryBeanName;
  }

  /**
   * @deprecated Since 2.0.1, Please use the {@link #setMapperFactoryBeanClass(Class)}.
   */
  @Deprecated
  public void setMapperFactoryBean(MapperFactoryBean<?> mapperFactoryBean) {
    this.mapperFactoryBeanClass = mapperFactoryBean == null ? MapperFactoryBean.class : mapperFactoryBean.getClass();
  }

  /**
   * Set the {@code MapperFactoryBean} class.
   *
   * @param mapperFactoryBeanClass
   *          the {@code MapperFactoryBean} class
   * @since 2.0.1
   */
  public void setMapperFactoryBeanClass(Class<? extends MapperFactoryBean> mapperFactoryBeanClass) {
    this.mapperFactoryBeanClass = mapperFactoryBeanClass == null ? MapperFactoryBean.class : mapperFactoryBeanClass;
  }

  /**
   * 扫描规则过滤
   */
  public void registerFilters() {
    boolean acceptAllInterfaces = true;

    /**
     * annotationClass 属性处理 如果 annotation Class 不为空，表示用户设置了此属性，那么就要根据此属性生成过滤器以 保证达到用户想要的效果，而封装此属性的过滤器就是
     * AnnotationTypeFiter Annotaton TypeFilter 保证在扫描对应 Java 文件时只接受标记有注解为 annotation Class 的接口
     */
    if (this.annotationClass != null) {
      addIncludeFilter(new AnnotationTypeFilter(this.annotationClass));
      acceptAllInterfaces = false;
    }

    /**
     * 如果 markerlnterface不为空 ，表示用户设置了此属性，那么就要根据此属性生成过滤器以 保证达到用户想要的效果，而封装此属性的过滤器就是实现 AssignableTypeFiter 接口的局部
     * 类,扫描过程中只有实现 markerIntrface 接口的接口才会被接受
     */
    if (this.markerInterface != null) {
      addIncludeFilter(new AssignableTypeFilter(this.markerInterface) {
        @Override
        protected boolean matchClassName(String className) {
          return false;
        }
      });
      acceptAllInterfaces = false;
    }

    /**
     * 如果接受所有接口，则添加自定义 INCLUDE 过滤器 TypeFilter ，全部返回 true
     */
    if (acceptAllInterfaces) {
      // default include filter that accepts all classes
      addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
    }

    /**
     * 对于命名为 package-info Java 文件，默认不作为逻辑实现接口，将其排除掉，使用 TypeFiltler 接口的局部类实现 match 方法
     */
    addExcludeFilter((metadataReader, metadataReaderFactory) -> {
      String className = metadataReader.getClassMetadata().getClassName();
      return className.endsWith("package-info");
    });
  }

  /**
   * 方法实现说明:真正调用扫描我们@MapperScan指定的路径下的mapper包
   *
   * @author:xsls
   * @param basePackages
   *          :路径长度
   * @return:
   * @exception:
   * @date:2019/8/21 17:27
   */
  @Override
  public Set<BeanDefinitionHolder> doScan(String... basePackages) {
    /**
     * 调用父类ClassPathBeanDefinitionScanner 来进行扫描
     */
    Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

    /**
     * 若扫描后 我们mapper包下有接口类,那么扫描bean定义就不会为空
     */
    if (beanDefinitions.isEmpty()) {
      LOGGER.warn(() -> "No MyBatis mapper was found in '" + Arrays.toString(basePackages)
          + "' package. Please check your configuration.");
    } else {
      /**
       * 正是在这里mybaits做了一个很牛逼的功能，将spring的 的bean定义玩到极致(做了偷天换日的操作) 现在我们知道t通过父类扫描出来的mapper是接口类型的
       * 比如我们com.tuling.mapper.UserMapper 他是一个接口 我们有基础的同学可能会知道我们的bean定义最终会被实例化成
       * 对象，但是我们接口是不能实例化的,所以在processBeanDefinitions 来进行偷天换日
       */
      processBeanDefinitions(beanDefinitions);
    }

    return beanDefinitions;
  }

  private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
    GenericBeanDefinition definition;
    /**
     * 循环我们所有扫描出mapper的bean定义出来
     */
    for (BeanDefinitionHolder holder : beanDefinitions) {
      // 获取我们的bean定义
      definition = (GenericBeanDefinition) holder.getBeanDefinition();
      // 获取我们的bean定义的名称
      String beanClassName = definition.getBeanClassName();
      LOGGER.debug(() -> "Creating MapperFactoryBean with name '" + holder.getBeanName() + "' and '" + beanClassName
          + "' mapperInterface");

      /**
       * 进行真的偷天换日操作,也就是这二行代码是最最最最最重要的, 关乎我们 spring整合mybaits的整合 definition.setBeanClass(this.mapperFactoryBeanClass);
       */
      // 设置ConstructorArgumentValues 会通过构造器初始化对象
      definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName); // issue #59
      // 设置成factoryBean
      definition.setBeanClass(this.mapperFactoryBeanClass);

      definition.getPropertyValues().add("addToConfig", this.addToConfig);

      /**
       * 为我们的Mapper对象绑定我们的sqlSessionFactory引用 说白了就是我们的UserMapper(实际上是就是为我们的MapperFactoryBean添加一个sqlSessionFactory的属性)
       * 然后SpringIoc在实例话我们的MapperFactoryBean的时候会经历populate()方法为我么你的UserMapper(MapperFactoryBean)
       * 的sqlSessionFactory赋值(调用set方法)
       */
      boolean explicitFactoryUsed = false;
      if (StringUtils.hasText(this.sqlSessionFactoryBeanName)) {
        definition.getPropertyValues().add("sqlSessionFactory",
            new RuntimeBeanReference(this.sqlSessionFactoryBeanName));
        explicitFactoryUsed = true;
      } else if (this.sqlSessionFactory != null) {
        definition.getPropertyValues().add("sqlSessionFactory", this.sqlSessionFactory);
        explicitFactoryUsed = true;
      }
      /**
       * 为我们的Mapper对象绑定我们的sqlSessionTemplate属性对象
       * 说白了就是我们的UserMapper(实际上是就是为我们的MapperFactoryBean添加一个sqlSessionTemplate的属性)
       * 然后SpringIoc在实例话我们的MapperFactoryBean的时候会经历populate()方法为我么你的UserMapper(MapperFactoryBean)
       * 的sqlSessionTemplate赋值(调用set方法)
       */
      if (StringUtils.hasText(this.sqlSessionTemplateBeanName)) {
        if (explicitFactoryUsed) {
          LOGGER.warn(
              () -> "Cannot use both: sqlSessionTemplate and sqlSessionFactory together. sqlSessionFactory is ignored.");
        }
        definition.getPropertyValues().add("sqlSessionTemplate",
            new RuntimeBeanReference(this.sqlSessionTemplateBeanName));
        explicitFactoryUsed = true;
      } else if (this.sqlSessionTemplate != null) {
        if (explicitFactoryUsed) {
          LOGGER.warn(
              () -> "Cannot use both: sqlSessionTemplate and sqlSessionFactory together. sqlSessionFactory is ignored.");
        }
        // 将sqlSessionTemplate通过AUTOWIRE_BY_TYPE自动装配
        definition.getPropertyValues().add("sqlSessionTemplate", this.sqlSessionTemplate);
        explicitFactoryUsed = true;
      }

      /**
       * 设置UserMapper<MapperFactoryBean>定义的注入模型是通过 包扫描进来的，所有我们的默认注入模型就是
       * AutowireCapableBeanFactory.AUTOWIRE_NO=0注入模型为0的时候,在这种情况下,若我们的MapperFactoryBean
       * 的字段属性是永远自动注入不了值的因为字段上是没有 @AutoWired注解,所以我们需要把UserMapper<MapperFactoryBean> 的bean定义的注入模型给改成我们的 AUTOWIRE_BY_TYPE
       * = 1,指定这个类型就是根据类型装配的话， 第一:我们的字段上不需要写@AutoWired注解，为啥? springioc会把当前UserMapper<MapperFactoryBean>中的setXXX(入参)
       * 都会去解析一次入参,入参的值可定会从ioc容器中获取，然后调用setXXX方法给赋值好. 或 AUTOWIRE_By_Type=1
       * 指定这个类型就是根据类型装配的话，第一:我们的字段上不需要写@AutoWired注解，为啥? springioc会把当前UserMapper<MapperFactoryBean>中的setXXX(入参)都会去解析一次入参,
       * 入参的值可定会从ioc容器中获取，然后调用setXXX方法给赋值好.
       *
       * ??????????????为啥在这里mybaits却要指定AUTOWIRE_BY_TYPE了？ 假设我们指定的是by_name的话， 那么他会通过setXXX(入参)的引用名去ioc容器中获取值，
       * 假设我们自己配置的bean的名称不是相同的那么就会抛出异常
       *
       * public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) { if (this.sqlSessionTemplate == null ||
       * sqlSessionFactory != this.sqlSessionTemplate.getSqlSessionFactory()) { this.sqlSessionTemplate
       * =createSqlSessionTemplate(sqlSessionFactory); } } 所以在IOC实例化我们的UserMapper<MapperFactoryBean>的时候，会调用父类
       * SqlSessionDaoSupport的setSqlSessionFactory(SqlSessionFactory sqlSessionFactory)
       * 方法,而我们的sqlSessionFactory需要去容器中获取（也就是我们自己配置的SqlSessionFactoryBean）
       *
       */
      if (!explicitFactoryUsed) {
        LOGGER.debug(() -> "Enabling autowire by type for MapperFactoryBean with name '" + holder.getBeanName() + "'.");
        definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
      }
      /**
       * 设置bean定义的加载模型(是否为懒加载)
       */
      definition.setLazyInit(lazyInitialization);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
    return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) {
    if (super.checkCandidate(beanName, beanDefinition)) {
      return true;
    } else {
      LOGGER.warn(() -> "Skipping MapperFactoryBean with name '" + beanName + "' and '"
          + beanDefinition.getBeanClassName() + "' mapperInterface" + ". Bean already defined with the same name!");
      return false;
    }
  }

}
