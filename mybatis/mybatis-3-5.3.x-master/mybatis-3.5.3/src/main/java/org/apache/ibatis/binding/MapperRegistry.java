/**
 *    Copyright 2009-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.binding;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.builder.annotation.MapperAnnotationBuilder;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;

/**
 * @author Clinton Begin
 * @author Eduardo Macarron
 * @author Lasse Voss
 */
public class MapperRegistry {

  private final Configuration config;
  private final Map<Class<?>, MapperProxyFactory<?>> knownMappers = new HashMap<>();

  public MapperRegistry(Configuration config) {
    this.config = config;
  }

  /**
   * 方法实现说明:通过class类型和sqlSessionTemplate获取我们的Mapper(代理对象)
   * @author:xsls
   * @param type:Mapper的接口类型
   * @param sqlSession:接口类型实际上是我们的sqlSessionTemplate类型
   * @return:
   * @exception:
   * @date:2019/8/22 20:41
   */
  @SuppressWarnings("unchecked")
  public <T> T getMapper(Class<T> type, SqlSession sqlSession) {
    /**
     * 直接去缓存knownMappers中通过Mapper的class类型去找我们的mapperProxyFactory
     */
    final MapperProxyFactory<T> mapperProxyFactory = (MapperProxyFactory<T>) knownMappers.get(type);
    /**
     * 缓存中没有获取到 直接抛出异常
     */
    if (mapperProxyFactory == null) {
      throw new BindingException("Type " + type + " is not known to the MapperRegistry.");
    }
    try {
      /**
       * 通过MapperProxyFactory来创建我们的实例
       */
      return mapperProxyFactory.newInstance(sqlSession);
    } catch (Exception e) {
      throw new BindingException("Error getting mapper instance. Cause: " + e, e);
    }
  }

  /**
   * 方法实现说明:我们的Mapper接口是否保存在knownMappers  Map集合中
   * @author:xsls
   * @param type:我们的Mapper接口
   * @return: true or false
   * @exception:
   * @date:2019/8/22 20:28
   */
  public <T> boolean hasMapper(Class<T> type) {
    return knownMappers.containsKey(type);
  }

  /**
   * 方法实现说明:把我们的Mapper class保存到我们的knownMappers map 中
   * @author:xsls
   * @param type:我们的Mapper接口
   * @return:
   * @exception:
   * @date:2019/8/22 20:29
   */
  public <T> void addMapper(Class<T> type) {
    /**
     * 判断我们传入进来的type类型是不是接口
     */
    if (type.isInterface()) {
      /**
       * 判断我们的缓存中有没有该类型
       */
      if (hasMapper(type)) {
        throw new BindingException("Type " + type + " is already known to the MapperRegistry.");
      }
      boolean loadCompleted = false;
      try {
        /**
         * 创建一个MapperProxyFactory 把我们的Mapper接口保存到工厂类中， 该工厂用于创建 MapperProxy
         */
        knownMappers.put(type, new MapperProxyFactory<>(type));
        // It's important that the type is added before the parser is run
        // otherwise the binding may automatically be attempted by the
        // mapper parser. If the type is already known, it won't try.    mapper注解构造器
        MapperAnnotationBuilder parser = new MapperAnnotationBuilder(config, type);
        /**
         * 进行解析, 将接口完整限定名作为xml文件地址去解析
         */
        parser.parse();
        loadCompleted = true;
      } finally {
        if (!loadCompleted) {
          knownMappers.remove(type);
        }
      }
    }
  }

  /**
   * @since 3.2.2
   */
  public Collection<Class<?>> getMappers() {
    return Collections.unmodifiableCollection(knownMappers.keySet());
  }

  /**
   * @since 3.2.2
   */
  public void addMappers(String packageName, Class<?> superType) {
    // 根据包找到所有类
    ResolverUtil<Class<?>> resolverUtil = new ResolverUtil<>();
    resolverUtil.find(new ResolverUtil.IsA(superType), packageName);
    Set<Class<? extends Class<?>>> mapperSet = resolverUtil.getClasses();
    // 循环所有的类
    for (Class<?> mapperClass : mapperSet) {
      addMapper(mapperClass);
    }
  }

  /**
   * @since 3.2.2
   */
  public void addMappers(String packageName) {
    addMappers(packageName, Object.class);
  }

}
