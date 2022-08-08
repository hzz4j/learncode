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

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.FactoryBean;

/**
 * BeanFactory that enables injection of MyBatis mapper interfaces. It can be set up with a SqlSessionFactory or a
 * pre-configured SqlSessionTemplate.
 * <p>
 * Sample configuration:
 *
 * <pre class="code">
 * {@code
 *   <bean id="baseMapper" class="org.mybatis.spring.mapper.MapperFactoryBean" abstract="true" lazy-init="true">
 *     <property name="sqlSessionFactory" ref="sqlSessionFactory" />
 *   </bean>
 *
 *   <bean id="oneMapper" parent="baseMapper">
 *     <property name="mapperInterface" value="my.package.MyMapperInterface" />
 *   </bean>
 *
 *   <bean id="anotherMapper" parent="baseMapper">
 *     <property name="mapperInterface" value="my.package.MyAnotherMapperInterface" />
 *   </bean>
 * }
 * </pre>
 * <p>
 * Note that this factory can only inject <em>interfaces</em>, not concrete classes.
 *
 * @author Eduardo Macarron
 *
 * @see SqlSessionTemplate
 */
/**
 * @vlog: 高于生活，源于生活
 * @desc: 类的描述:这个了就是我们UserMapper的代理类,他也会经过 springIoc容器bean的生命周期,在bean的生命周期方法populate()方法会给属性进行赋值
 *        由于在ClassMapperScan类中已经把当前的bean定义的注入模型给修改了by_type 所以，凡是写了setXXX的方法的,spring ioc在populate() 去进行调用
 * @author: xsls
 * @createDate: 2019/8/22 19:20
 * @version: 1.0
 */
public class MapperFactoryBean<T> extends SqlSessionDaoSupport implements FactoryBean<T> {

  private Class<T> mapperInterface;

  private boolean addToConfig = true;

  public MapperFactoryBean() {
    // intentionally empty
  }

  /**
   * 在这里又是一个牛逼的设计闪光点:我们知道在ClassPathMapperScanner 扫描我们的UserMapper<MapperFactoryBean>的时候做了一个牛逼的事情,
   * 他扫描我们的UserMapper的时候的bean定义是接口类型的，我们知道接口类型是不能够被实例化的 所以在ClassPathMapperScanner扫描之后马上进行来处理UserMapper的bean定义
   * definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName);
   * definition.setBeanClass(this.mapperFactoryBeanClass); 把UserMapper的bean定义给改成我们的MapperFactoryBean,
   * 最终我们实例化UserMapper就是我们的MapperFactoryBean类型,
   * definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName);
   * 就是来指定我们的实例化MapperFactoryBean的构造函数的参数。这么做的目的就是 因为MapperFactoryBean 是我们的Factorybean对象, 最终返回的是getObject()方法放回的对象
   * 而getObject()对象返回的是一个jdk代理对象，我们知道jdk代理对象需要代理接口， 所以这里就是为了保存我们传入进来的接口类型
   *
   * @param mapperInterface
   */
  public MapperFactoryBean(Class<T> mapperInterface) {
    this.mapperInterface = mapperInterface;
  }

  /**
   * 方法实现说明:在UserMapper<MapperFactoryBean> 父类DaoSupport 的bean的生命周期回调
   * InitializingBean.afterPropertiesSet()方法的时候,会进行checkDaoConfig();检查
   *
   * @author:xsls
   * @return:
   * @exception:
   * @date:2019/8/22 20:07
   */
  @Override
  protected void checkDaoConfig() {
    /**
     * 调用父类的SqlSessionDaoSupport的方法来检查我们的SqlSessionFactory 或者sqlSessionTemplate是否为空
     */
    super.checkDaoConfig();

    /**
     * 断言我们的mapperInterface(我们mapper接口class类型是否为空)
     */
    notNull(this.mapperInterface, "Property 'mapperInterface' is required");

    /**
     * 在这里进行了二个操作,第一步:getSqlSession() 是调用父类的获取SqlSession类型（接口）实现类 SqlSessionTemplate 第二步:getConfiuration
     * 是调用sqlSessionTemplate的sqlSessionFactory对象获取他的Configuration属性
     */
    Configuration configuration = getSqlSession().getConfiguration();
    /**
     * 判断我们的mapperRegistry 的knownMappers Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();
     */
    if (this.addToConfig && !configuration.hasMapper(this.mapperInterface)) {
      try {
        /**
         * 把我们的接口类型保存到sqlSessionFactory的属性Configuration对象 的MapperRegistry属性中
         */
        configuration.addMapper(this.mapperInterface);
      } catch (Exception e) {
        logger.error("Error while adding the mapper '" + this.mapperInterface + "' to configuration.", e);
        throw new IllegalArgumentException(e);
      } finally {
        ErrorContext.instance().reset();
      }
    }
  }

  /**
   * 方法实现说明:由于是我们factoryBean,那么我们service中注入我们的UserMapper的时候 就会调用我们的getObject()
   *
   * @author:xsls
   * @return:
   * @exception:
   * @date:2019/8/22 20:35
   */
  @Override
  public T getObject() throws Exception {
    /**
     * 第一步:就是获取我么女的SqlSessionTemplate 第二步:获取我们的SqlSessionTemplate.getMapper(mapperInterface)方法
     */
    return getSqlSession().getMapper(this.mapperInterface);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<T> getObjectType() {
    return this.mapperInterface;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isSingleton() {
    return true;
  }

  // ------------- mutators --------------

  /**
   * Sets the mapper interface of the MyBatis mapper
   *
   * @param mapperInterface
   *          class of the interface
   */
  public void setMapperInterface(Class<T> mapperInterface) {
    this.mapperInterface = mapperInterface;
  }

  /**
   * Return the mapper interface of the MyBatis mapper
   *
   * @return class of the interface
   */
  public Class<T> getMapperInterface() {
    return mapperInterface;
  }

  /**
   * If addToConfig is false the mapper will not be added to MyBatis. This means it must have been included in
   * mybatis-config.xml.
   * <p>
   * If it is true, the mapper will be added to MyBatis in the case it is not already registered.
   * <p>
   * By default addToConfig is true.
   *
   * @param addToConfig
   *          a flag that whether add mapper to MyBatis or not
   */
  public void setAddToConfig(boolean addToConfig) {
    this.addToConfig = addToConfig;
  }

  /**
   * Return the flag for addition into MyBatis config.
   *
   * @return true if the mapper will be added to MyBatis in the case it is not already registered.
   */
  public boolean isAddToConfig() {
    return addToConfig;
  }
}
