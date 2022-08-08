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
package org.apache.ibatis.scripting;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;

/**
 * 类功能描述:语言解析接口,用来解析我们的sql脚本语言的
 * 默认实现就是class org.apache.ibatis.scripting.xmltags.XMLLanguageDriver
 * 我们自定义的sql脚本语言驱动可以实现该接口
 * @author:xsls
 * @return:
 * @exception:
 * @date:2019/9/6 15:30
 */
public interface LanguageDriver {

  /**
   * Creates a {@link ParameterHandler} that passes the actual parameters to the the JDBC statement.
   *
   * @param mappedStatement The mapped statement that is being executed
   * @param parameterObject The input parameter object (can be null)
   * @param boundSql The resulting SQL once the dynamic language has been executed.
   * @return
   * @author Frank D. Martinez [mnesarco]
   * @see DefaultParameterHandler
   */

  /**
   * 方法实现说明:mybatis的参数处理器对象
   * @author:xsls
   * @param mappedStatement:我们的sql节点对象
   * @param parameterObject:参数对象
   * @param boundSql:原生的sql
   * @return: ParameterHandler参数处理器对象
   * @exception:
   * @date:2019/9/6 15:32
   */
  ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql);

  /**
   * Creates an {@link SqlSource} that will hold the statement read from a mapper xml file.
   * It is called during startup, when the mapped statement is read from a class or an xml file.
   *
   * @param configuration The MyBatis configuration
   * @param script XNode parsed from a XML file
   * @param parameterType input parameter type got from a mapper method or specified in the parameterType xml attribute. Can be null.
   * @return
   */
  /**
   * 方法实现说明:创建我们的原生sql对象
   * @author:xsls
   * @param configuration:mybaits的全局配置类
   * @param script:sql脚本
   * @param parameterType:参数类型
   * @return:SqlSource:用于保存的sql资源脚本对象
   * @exception:
   * @date:2019/9/6 15:33
   */
  SqlSource createSqlSource(Configuration configuration, XNode script, Class<?> parameterType);

  /**
   * Creates an {@link SqlSource} that will hold the statement read from an annotation.
   * It is called during startup, when the mapped statement is read from a class or an xml file.
   *
   * @param configuration The MyBatis configuration
   * @param script The content of the annotation
   * @param parameterType input parameter type got from a mapper method or specified in the parameterType xml attribute. Can be null.
   * @return
   */
  /**
   * 方法实现说明:创建我们的原生sql对象
   * @author:xsls
   * @param configuration:mybaits的全局配置类
   * @param script:sql脚本
   * @param parameterType:参数类型
   * @return:SqlSource:用于保存的sql资源脚本对象
   * @exception:
   * @date:2019/9/6 15:33
   */
  SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType);

}
