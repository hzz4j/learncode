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

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ParamNameResolver;
import org.apache.ibatis.reflection.TypeParameterResolver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

/**
 * @author Clinton Begin
 * @author Eduardo Macarron
 * @author Lasse Voss
 * @author Kazuki Shimizu
 */
/**
* @vlog: 高于生活，源于生活
* @desc: 类的描述:用于封装我们的Mapper接口中的方法对象
* @author: xsls
* @createDate: 2019/9/6 21:46
* @version: 1.0
*/
public class MapperMethod {

  /**
   * 用于保存我们Mapper接口方法信息
   */
  private final SqlCommand command;
  private final MethodSignature method;

  public MapperMethod(Class<?> mapperInterface, Method method, Configuration config) {
    /**
     * 创建我们的SqlCommand对象
     */
    this.command = new SqlCommand(config, mapperInterface, method);
    /**
     * 创建我们的方法签名对象
     */
    this.method = new MethodSignature(config, mapperInterface, method);
  }

  /**
   * 方法实现说明:执行我们的目标方法
   * @author:sqlSession:我们的sqlSessionTemplate
   * @param args:方法参数
   * @return:Object
   * @exception:
   * @date:2019/9/8 15:43
   */
  public Object execute(SqlSession sqlSession, Object[] args) {
    Object result;
    /**
     * 判断我们执行sql命令的类型
     */
    switch (command.getType()) {
      //insert操作
      case INSERT: {
        Object param = method.convertArgsToSqlCommandParam(args);
        result = rowCountResult(sqlSession.insert(command.getName(), param));
        break;
      }
      //update操作
      case UPDATE: {
        Object param = method.convertArgsToSqlCommandParam(args);
        result = rowCountResult(sqlSession.update(command.getName(), param));
        break;
      }
      //delete操作
      case DELETE: {
        Object param = method.convertArgsToSqlCommandParam(args);
        result = rowCountResult(sqlSession.delete(command.getName(), param));
        break;
      }
      //select操作
      case SELECT:
        //返回值为空
        if (method.returnsVoid() && method.hasResultHandler()) {
          executeWithResultHandler(sqlSession, args);
          result = null;
        } else if (method.returnsMany()) {
          //返回值是一个List
          result = executeForMany(sqlSession, args);
        } else if (method.returnsMap()) {
          //返回值是一个map
          result = executeForMap(sqlSession, args);
        } else if (method.returnsCursor()) {
          //返回游标
          result = executeForCursor(sqlSession, args);
        } else {
          //查询返回单个

          /**
           * 解析我们的参数
           */
          Object param = method.convertArgsToSqlCommandParam(args);
          /**
           * 通过调用sqlSessionTemplate来执行我们的sql
           * 第一步:获取我们的statmentName(com.tuling.mapper.EmployeeMapper.findOne)
           * 然后我们就需要重点研究下SqlSessionTemplate是怎么来的?
           * 在mybatis和spring整合的时候，我们偷天换日了我们mapper接口包下的所有的
           * beandefinition改成了MapperFactoryBean类型的
           * MapperFactoryBean<T> extends SqlSessionDaoSupport的类实现了SqlSessionDaoSupport
           * 那么就会调用他的setXXX方法为我们的sqlSessionTemplate赋值
           *
           */
          result = sqlSession.selectOne(command.getName(), param);
          if (method.returnsOptional()
              && (result == null || !method.getReturnType().equals(result.getClass()))) {
            result = Optional.ofNullable(result);
          }
        }
        break;
      case FLUSH:
        result = sqlSession.flushStatements();
        break;
      default:
        throw new BindingException("Unknown execution method for: " + command.getName());
    }
    if (result == null && method.getReturnType().isPrimitive() && !method.returnsVoid()) {
      throw new BindingException("Mapper method '" + command.getName()
          + " attempted to return null from a method with a primitive return type (" + method.getReturnType() + ").");
    }
    return result;
  }

  private Object rowCountResult(int rowCount) {
    final Object result;
    if (method.returnsVoid()) {
      result = null;
    } else if (Integer.class.equals(method.getReturnType()) || Integer.TYPE.equals(method.getReturnType())) {
      result = rowCount;
    } else if (Long.class.equals(method.getReturnType()) || Long.TYPE.equals(method.getReturnType())) {
      result = (long)rowCount;
    } else if (Boolean.class.equals(method.getReturnType()) || Boolean.TYPE.equals(method.getReturnType())) {
      result = rowCount > 0;
    } else {
      throw new BindingException("Mapper method '" + command.getName() + "' has an unsupported return type: " + method.getReturnType());
    }
    return result;
  }

  private void executeWithResultHandler(SqlSession sqlSession, Object[] args) {
    MappedStatement ms = sqlSession.getConfiguration().getMappedStatement(command.getName());
    if (!StatementType.CALLABLE.equals(ms.getStatementType())
        && void.class.equals(ms.getResultMaps().get(0).getType())) {
      throw new BindingException("method " + command.getName()
          + " needs either a @ResultMap annotation, a @ResultType annotation,"
          + " or a resultType attribute in XML so a ResultHandler can be used as a parameter.");
    }
    Object param = method.convertArgsToSqlCommandParam(args);
    if (method.hasRowBounds()) {
      RowBounds rowBounds = method.extractRowBounds(args);
      sqlSession.select(command.getName(), param, rowBounds, method.extractResultHandler(args));
    } else {
      sqlSession.select(command.getName(), param, method.extractResultHandler(args));
    }
  }

  private <E> Object executeForMany(SqlSession sqlSession, Object[] args) {
    List<E> result;
    Object param = method.convertArgsToSqlCommandParam(args);
    if (method.hasRowBounds()) {
      RowBounds rowBounds = method.extractRowBounds(args);
      result = sqlSession.selectList(command.getName(), param, rowBounds);
    } else {
      result = sqlSession.selectList(command.getName(), param);
    }
    // issue #510 Collections & arrays support
    if (!method.getReturnType().isAssignableFrom(result.getClass())) {
      if (method.getReturnType().isArray()) {
        return convertToArray(result);
      } else {
        return convertToDeclaredCollection(sqlSession.getConfiguration(), result);
      }
    }
    return result;
  }

  private <T> Cursor<T> executeForCursor(SqlSession sqlSession, Object[] args) {
    Cursor<T> result;
    Object param = method.convertArgsToSqlCommandParam(args);
    if (method.hasRowBounds()) {
      RowBounds rowBounds = method.extractRowBounds(args);
      result = sqlSession.selectCursor(command.getName(), param, rowBounds);
    } else {
      result = sqlSession.selectCursor(command.getName(), param);
    }
    return result;
  }

  private <E> Object convertToDeclaredCollection(Configuration config, List<E> list) {
    Object collection = config.getObjectFactory().create(method.getReturnType());
    MetaObject metaObject = config.newMetaObject(collection);
    metaObject.addAll(list);
    return collection;
  }

  @SuppressWarnings("unchecked")
  private <E> Object convertToArray(List<E> list) {
    Class<?> arrayComponentType = method.getReturnType().getComponentType();
    Object array = Array.newInstance(arrayComponentType, list.size());
    if (arrayComponentType.isPrimitive()) {
      for (int i = 0; i < list.size(); i++) {
        Array.set(array, i, list.get(i));
      }
      return array;
    } else {
      return list.toArray((E[])array);
    }
  }

  private <K, V> Map<K, V> executeForMap(SqlSession sqlSession, Object[] args) {
    Map<K, V> result;
    Object param = method.convertArgsToSqlCommandParam(args);
    if (method.hasRowBounds()) {
      RowBounds rowBounds = method.extractRowBounds(args);
      result = sqlSession.selectMap(command.getName(), param, method.getMapKey(), rowBounds);
    } else {
      result = sqlSession.selectMap(command.getName(), param, method.getMapKey());
    }
    return result;
  }

  public static class ParamMap<V> extends HashMap<String, V> {

    private static final long serialVersionUID = -2212268410512043556L;

    @Override
    public V get(Object key) {
      if (!super.containsKey(key)) {
        throw new BindingException("Parameter '" + key + "' not found. Available parameters are " + keySet());
      }
      return super.get(key);
    }

  }

  /**
   * 用户保存我们Mapper接口方法信息
   */
  public static class SqlCommand {
    /**
     * 接口的方法名全路径比如:com.tuling.mapper.DeptMapper.findDepts
     */
    private final String name;
    /**
     * 对应接口方法操作的sql类型(是insert|update|delte|select)
     */
    private final SqlCommandType type;

    /**
     * 方法实现说明:创建我们的SqlCommand
     * @author:xsls
     * @param configuration：mybatis的全局配置
     * @param mapperInterface:我们Mapper接口的class类型
     * @param method:方法对象
     * @return:
     * @exception:
     * @date:2019/9/6 21:51
     */
    public SqlCommand(Configuration configuration, Class<?> mapperInterface, Method method) {
      //获取我们的方法的名称
      final String methodName = method.getName();
      //方法所在接口的类型
      final Class<?> declaringClass = method.getDeclaringClass();
      /**
       * 根据接口,方法名称解析出我们对应的mapperStatment对象
       */
      MappedStatement ms = resolveMappedStatement(mapperInterface, methodName, declaringClass,
          configuration);
      if (ms == null) {
        if (method.getAnnotation(Flush.class) != null) {
          name = null;
          type = SqlCommandType.FLUSH;
        } else {
          throw new BindingException("Invalid bound statement (not found): "
              + mapperInterface.getName() + "." + methodName);
        }
      } else {
        //把我们的mappedStatmentID（com.tuling.mapper.EmpMapper.findEmp）
        name = ms.getId();
        //sql操作的类型(比如insert|delete|update|select)
        type = ms.getSqlCommandType();
        if (type == SqlCommandType.UNKNOWN) {
          throw new BindingException("Unknown execution method for: " + name);
        }
      }
    }

    public String getName() {
      return name;
    }

    public SqlCommandType getType() {
      return type;
    }

    /**
     * 方法实现说明:解析我们的mappedStatment对象
     * @author:xsls
     * @param mapperInterface:我们mapper接口的class类型
     * @param methodName :方法名称
     * @param declaringClass:方法所在类的接口
     * @param configuration:mybatis的全局配置
     * @return: MappedStatement
     * @exception:
     * @date:2019/9/8 13:29
     */
    private MappedStatement resolveMappedStatement(Class<?> mapperInterface, String methodName,
        Class<?> declaringClass, Configuration configuration) {
      //获取我们的sql对应的statmentId(com.tuling.mapper.DeptMapper.findDepts)
      String statementId = mapperInterface.getName() + "." + methodName;
      //根据我们的statmentId判断我们的主配置类是否包含 了我们的mapperStatment对象
      if (configuration.hasStatement(statementId)) {
        //存在通过key获取对应的mapperStatment对象返回
        return configuration.getMappedStatement(statementId);
      } else if (mapperInterface.equals(declaringClass)) {
        return null;
      }
      /**
       * 获取我们mapper接口的父类接口
       */
      for (Class<?> superInterface : mapperInterface.getInterfaces()) {
        //判断方法所在的类是否实现了superInterface
        if (declaringClass.isAssignableFrom(superInterface)) {
          //解析我们父类的MappedStatment对象
          MappedStatement ms = resolveMappedStatement(superInterface, methodName,
              declaringClass, configuration);
          if (ms != null) {
            return ms;
          }
        }
      }
      return null;
    }
  }

  public static class MethodSignature {

    private final boolean returnsMany;
    private final boolean returnsMap;
    private final boolean returnsVoid;
    private final boolean returnsCursor;
    private final boolean returnsOptional;
    private final Class<?> returnType;
    private final String mapKey;
    private final Integer resultHandlerIndex;
    private final Integer rowBoundsIndex;
    private final ParamNameResolver paramNameResolver;

    /**
     * 方法实现说明:方法签名对象
     * @author:xsls
     * @param configuration:mybaits的全局配置类
     * @param mapperInterface:我们mapper接口的class
     * @param method:接口方法调用对象
     * @return:
     * @exception:
     * @date:2019/9/8 13:45
     */
    public MethodSignature(Configuration configuration, Class<?> mapperInterface, Method method) {
      /**
       * 解析方法的返回值类型
       */
      Type resolvedReturnType = TypeParameterResolver.resolveReturnType(method, mapperInterface);
      //判断返回值是不是class类型的
      if (resolvedReturnType instanceof Class<?>) {
        this.returnType = (Class<?>) resolvedReturnType;
      } else if (resolvedReturnType instanceof ParameterizedType) {
        //是不是参数泛型的
        this.returnType = (Class<?>) ((ParameterizedType) resolvedReturnType).getRawType();
      } else {
        //普通的
        this.returnType = method.getReturnType();
      }
      //返回值是不是为空
      this.returnsVoid = void.class.equals(this.returnType);
      //返回是是不是集合类型
      this.returnsMany = configuration.getObjectFactory().isCollection(this.returnType) || this.returnType.isArray();
      //返回值是不是游标
      this.returnsCursor = Cursor.class.equals(this.returnType);
      //返回值是不是optionnal类型的
      this.returnsOptional = Optional.class.equals(this.returnType);
      this.mapKey = getMapKey(method);
      this.returnsMap = this.mapKey != null;
      this.rowBoundsIndex = getUniqueParamIndex(method, RowBounds.class);
      this.resultHandlerIndex = getUniqueParamIndex(method, ResultHandler.class);
      /**
       * 初始化我们参数解析器对象
       */
      this.paramNameResolver = new ParamNameResolver(configuration, method);
    }

    /**
     * 方法实现说明:通过我们的参数解析器解析方法的参数
     * @author:xsls
     * @param args:参数数组
     * @return: Object处理后的参数
     * @exception:
     * @date:2019/9/8 17:15
     */
    public Object convertArgsToSqlCommandParam(Object[] args) {
      return paramNameResolver.getNamedParams(args);
    }

    public boolean hasRowBounds() {
      return rowBoundsIndex != null;
    }

    public RowBounds extractRowBounds(Object[] args) {
      return hasRowBounds() ? (RowBounds) args[rowBoundsIndex] : null;
    }

    public boolean hasResultHandler() {
      return resultHandlerIndex != null;
    }

    public ResultHandler extractResultHandler(Object[] args) {
      return hasResultHandler() ? (ResultHandler) args[resultHandlerIndex] : null;
    }

    public String getMapKey() {
      return mapKey;
    }

    public Class<?> getReturnType() {
      return returnType;
    }

    public boolean returnsMany() {
      return returnsMany;
    }

    public boolean returnsMap() {
      return returnsMap;
    }

    public boolean returnsVoid() {
      return returnsVoid;
    }

    public boolean returnsCursor() {
      return returnsCursor;
    }

    /**
     * return whether return type is {@code java.util.Optional}.
     * @return return {@code true}, if return type is {@code java.util.Optional}
     * @since 3.5.0
     */
    public boolean returnsOptional() {
      return returnsOptional;
    }

    private Integer getUniqueParamIndex(Method method, Class<?> paramType) {
      Integer index = null;
      final Class<?>[] argTypes = method.getParameterTypes();
      for (int i = 0; i < argTypes.length; i++) {
        if (paramType.isAssignableFrom(argTypes[i])) {
          if (index == null) {
            index = i;
          } else {
            throw new BindingException(method.getName() + " cannot have multiple " + paramType.getSimpleName() + " parameters");
          }
        }
      }
      return index;
    }

    private String getMapKey(Method method) {
      String mapKey = null;
      if (Map.class.isAssignableFrom(method.getReturnType())) {
        final MapKey mapKeyAnnotation = method.getAnnotation(MapKey.class);
        if (mapKeyAnnotation != null) {
          mapKey = mapKeyAnnotation.value();
        }
      }
      return mapKey;
    }
  }

}
