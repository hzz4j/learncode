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
package org.apache.ibatis.builder.xml;

import java.util.List;
import java.util.Locale;

import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;

/**
 * @author Clinton Begin
 */
public class XMLStatementBuilder extends BaseBuilder {

  private final MapperBuilderAssistant builderAssistant;
  private final XNode context;
  private final String requiredDatabaseId;

  public XMLStatementBuilder(Configuration configuration, MapperBuilderAssistant builderAssistant, XNode context) {
    this(configuration, builderAssistant, context, null);
  }

  /**
   * 方法实现说明:用于解析我们的的inset|select|update|delte节点的
   * @author:xsls
   * @param configuration:mybtais的全局配置类
   * @param builderAssistant:MapperStatemnt构建的辅助类
   * @param context:  inset|select|update|delte节点的对象
   * @param databaseId:数据库厂商Id
   * @return:
   * @exception:
   * @date:2019/9/5 21:38
   */
  public XMLStatementBuilder(Configuration configuration, MapperBuilderAssistant builderAssistant, XNode context, String databaseId) {
    super(configuration);
    this.builderAssistant = builderAssistant;
    this.context = context;
    this.requiredDatabaseId = databaseId;
  }

  public void parseStatementNode() {
    /**
     * 我们的insert|delte|update|select 语句的sqlId
     */
    String id = context.getStringAttribute("id");
    /**
     * 判断我们的insert|delte|update|select  节点是否配置了
     * 数据库厂商标注
     */
    String databaseId = context.getStringAttribute("databaseId");

    /**
     * 匹配当前的数据库厂商id是否匹配当前数据源的厂商id
     */
    if (!databaseIdMatchesCurrent(id, databaseId, this.requiredDatabaseId)) {
      return;
    }

    /**
     * 获得节点名称：select|insert|update|delete
     */
    String nodeName = context.getNode().getNodeName();
    /**
     * 根据nodeName 获得 SqlCommandType枚举
     */
    SqlCommandType sqlCommandType = SqlCommandType.valueOf(nodeName.toUpperCase(Locale.ENGLISH));
    /**
     * 判断是不是select语句节点
     */
    boolean isSelect = sqlCommandType == SqlCommandType.SELECT;
    /**
     *  获取flushCache属性
     *  默认值为isSelect的反值：查询：flushCache=false   增删改：flushCache=true
     */
    boolean flushCache = context.getBooleanAttribute("flushCache", !isSelect);
    /**
     * 获取useCache属性
     * 默认值为isSelect：查询：useCache=true   增删改：useCache=false
     */
    boolean useCache = context.getBooleanAttribute("useCache", isSelect);

    /**
     * resultOrdered:  是否需要分组：
     *  select * from user-->User{id=1, name='User1', groups=[1, 2], roles=[1, 2, 3]}
     */
    boolean resultOrdered = context.getBooleanAttribute("resultOrdered", false);

    /**
     * 解析我们的sql公用片段
     *     <select id="qryEmployeeById" resultType="Employee" parameterType="int">
              <include refid="selectInfo"></include>
              employee where id=#{id}
          </select>
        将 <include refid="selectInfo"></include> 解析成sql语句 放在<select>Node的子节点中
     */
    // Include Fragments before parsing
    XMLIncludeTransformer includeParser = new XMLIncludeTransformer(configuration, builderAssistant);
    includeParser.applyIncludes(context.getNode());

    /**
     * 解析我们sql节点的参数类型
     */
    String parameterType = context.getStringAttribute("parameterType");
    // 把参数类型字符串转化为class
    Class<?> parameterTypeClass = resolveClass(parameterType);

    /**
     * 查看sql是否支撑自定义语言
     * <delete id="delEmployeeById" parameterType="int" lang="tulingLang">
     <settings>
          <setting name="defaultScriptingLanguage" value="tulingLang"/>
     </settings>
     */
    String lang = context.getStringAttribute("lang");
    /**
     * 获取自定义sql脚本语言驱动 默认:class org.apache.ibatis.scripting.xmltags.XMLLanguageDriver
     */
    LanguageDriver langDriver = getLanguageDriver(lang);

    // Parse selectKey after includes and remove them.
    /**
     * 解析我们<insert 语句的的selectKey节点, 一般在oracle里面设置自增id
     */
    processSelectKeyNodes(id, parameterTypeClass, langDriver);

    // Parse the SQL (pre: <selectKey> and <include> were parsed and removed)
    /**
     * 我们insert语句 用于主键生成组件
     */
    KeyGenerator keyGenerator;
    /**
     * selectById!selectKey
     * id+!selectKey
     */
    String keyStatementId = id + SelectKeyGenerator.SELECT_KEY_SUFFIX;
    /**
     * 把我们的命名空间拼接到keyStatementId中
     * com.tuling.mapper.Employee.saveEmployee!selectKey
     */
    keyStatementId = builderAssistant.applyCurrentNamespace(keyStatementId, true);
    /**
     *<insert id="saveEmployee" parameterType="com.tuling.entity.Employee" useGeneratedKeys="true" keyProperty="id">
     *判断我们全局的配置类configuration中是否包含以及解析过的主键生成器对象
     */
    if (configuration.hasKeyGenerator(keyStatementId)) {
      keyGenerator = configuration.getKeyGenerator(keyStatementId);
    } else {

      /**
       * 若我们<insert 配置了useGeneratedKeys 那么就取useGeneratedKeys的配置值,
       * 否者就看我们的mybatis-config.xml配置文件中是配置了
       * <setting name="useGeneratedKeys" value="true"></setting> 默认是false
       * 并且判断sql操作类型是否为insert
       * 若是的话,那么使用的生成策略就是Jdbc3KeyGenerator.INSTANCE
       * 否则就是NoKeyGenerator.INSTANCE
       */
      keyGenerator = context.getBooleanAttribute("useGeneratedKeys",
          configuration.isUseGeneratedKeys() && SqlCommandType.INSERT.equals(sqlCommandType))
          ? Jdbc3KeyGenerator.INSTANCE : NoKeyGenerator.INSTANCE;
    }

    /**
     * 通过class org.apache.ibatis.scripting.xmltags.XMLLanguageDriver来解析我们的
     * sql脚本对象  .  解析SqlNode. 注意， 只是解析成一个个的SqlNode， 并不会完全解析sql,因为这个时候参数都没确定，动态sql无法解析
     */
    SqlSource sqlSource = langDriver.createSqlSource(configuration, context, parameterTypeClass);
    /**
     * STATEMENT，PREPARED 或 CALLABLE 中的一个。这会让 MyBatis 分别使用 Statement，PreparedStatement 或 CallableStatement，默认值：PREPARED
     */
    StatementType statementType = StatementType.valueOf(context.getStringAttribute("statementType", StatementType.PREPARED.toString()));
    /**
     * 这是一个给驱动的提示，尝试让驱动程序每次批量返回的结果行数和这个设置值相等。 默认值为未设置（unset）（依赖驱动）
     */
    Integer fetchSize = context.getIntAttribute("fetchSize");
    /**
     * 这个设置是在抛出异常之前，驱动程序等待数据库返回请求结果的秒数。默认值为未设置（unset）（依赖驱动）。
     */
    Integer timeout = context.getIntAttribute("timeout");
    /**
     * 将会传入这条语句的参数类的完全限定名或别名。这个属性是可选的，因为 MyBatis 可以通过类型处理器（TypeHandler） 推断出具体传入语句的参数，默认值为未设置
     */
    String parameterMap = context.getStringAttribute("parameterMap");
    /**
     * 从这条语句中返回的期望类型的类的完全限定名或别名。 注意如果返回的是集合，那应该设置为集合包含的类型，而不是集合本身。
     * 可以使用 resultType 或 resultMap，但不能同时使用
     */
    String resultType = context.getStringAttribute("resultType");
    /**解析我们查询结果集返回的类型     */
    Class<?> resultTypeClass = resolveClass(resultType);
    /**
     * 外部 resultMap 的命名引用。结果集的映射是 MyBatis 最强大的特性，如果你对其理解透彻，许多复杂映射的情形都能迎刃而解。
     * 可以使用 resultMap 或 resultType，但不能同时使用。
     */
    String resultMap = context.getStringAttribute("resultMap");

    String resultSetType = context.getStringAttribute("resultSetType");
    ResultSetType resultSetTypeEnum = resolveResultSetType(resultSetType);
    if (resultSetTypeEnum == null) {
      resultSetTypeEnum = configuration.getDefaultResultSetType();
    }

    /**
     * 解析 keyProperty  keyColumn 仅适用于 insert 和 update
     */
    String keyProperty = context.getStringAttribute("keyProperty");
    String keyColumn = context.getStringAttribute("keyColumn");
    String resultSets = context.getStringAttribute("resultSets");

    /**
     * 为我们的insert|delete|update|select节点构建成我们的mappedStatment对象
     */
    builderAssistant.addMappedStatement(id, sqlSource, statementType, sqlCommandType,
        fetchSize, timeout, parameterMap, parameterTypeClass, resultMap, resultTypeClass,
        resultSetTypeEnum, flushCache, useCache, resultOrdered,
        keyGenerator, keyProperty, keyColumn, databaseId, langDriver, resultSets);
  }

  private void processSelectKeyNodes(String id, Class<?> parameterTypeClass, LanguageDriver langDriver) {
    List<XNode> selectKeyNodes = context.evalNodes("selectKey");
    if (configuration.getDatabaseId() != null) {
      parseSelectKeyNodes(id, selectKeyNodes, parameterTypeClass, langDriver, configuration.getDatabaseId());
    }
    parseSelectKeyNodes(id, selectKeyNodes, parameterTypeClass, langDriver, null);
    removeSelectKeyNodes(selectKeyNodes);
  }

  private void parseSelectKeyNodes(String parentId, List<XNode> list, Class<?> parameterTypeClass, LanguageDriver langDriver, String skRequiredDatabaseId) {
    for (XNode nodeToHandle : list) {
      String id = parentId + SelectKeyGenerator.SELECT_KEY_SUFFIX;
      String databaseId = nodeToHandle.getStringAttribute("databaseId");
      if (databaseIdMatchesCurrent(id, databaseId, skRequiredDatabaseId)) {
        parseSelectKeyNode(id, nodeToHandle, parameterTypeClass, langDriver, databaseId);
      }
    }
  }

  private void parseSelectKeyNode(String id, XNode nodeToHandle, Class<?> parameterTypeClass, LanguageDriver langDriver, String databaseId) {
    String resultType = nodeToHandle.getStringAttribute("resultType");
    Class<?> resultTypeClass = resolveClass(resultType);
    StatementType statementType = StatementType.valueOf(nodeToHandle.getStringAttribute("statementType", StatementType.PREPARED.toString()));
    String keyProperty = nodeToHandle.getStringAttribute("keyProperty");
    String keyColumn = nodeToHandle.getStringAttribute("keyColumn");
    boolean executeBefore = "BEFORE".equals(nodeToHandle.getStringAttribute("order", "AFTER"));

    //defaults
    boolean useCache = false;
    boolean resultOrdered = false;
    KeyGenerator keyGenerator = NoKeyGenerator.INSTANCE;
    Integer fetchSize = null;
    Integer timeout = null;
    boolean flushCache = false;
    String parameterMap = null;
    String resultMap = null;
    ResultSetType resultSetTypeEnum = null;

    SqlSource sqlSource = langDriver.createSqlSource(configuration, nodeToHandle, parameterTypeClass);
    SqlCommandType sqlCommandType = SqlCommandType.SELECT;

    builderAssistant.addMappedStatement(id, sqlSource, statementType, sqlCommandType,
        fetchSize, timeout, parameterMap, parameterTypeClass, resultMap, resultTypeClass,
        resultSetTypeEnum, flushCache, useCache, resultOrdered,
        keyGenerator, keyProperty, keyColumn, databaseId, langDriver, null);

    id = builderAssistant.applyCurrentNamespace(id, false);

    MappedStatement keyStatement = configuration.getMappedStatement(id, false);
    configuration.addKeyGenerator(id, new SelectKeyGenerator(keyStatement, executeBefore));
  }

  private void removeSelectKeyNodes(List<XNode> selectKeyNodes) {
    for (XNode nodeToHandle : selectKeyNodes) {
      nodeToHandle.getParent().getNode().removeChild(nodeToHandle.getNode());
    }
  }

  private boolean databaseIdMatchesCurrent(String id, String databaseId, String requiredDatabaseId) {
    if (requiredDatabaseId != null) {
      return requiredDatabaseId.equals(databaseId);
    }
    if (databaseId != null) {
      return false;
    }
    id = builderAssistant.applyCurrentNamespace(id, false);
    if (!this.configuration.hasStatement(id, false)) {
      return true;
    }
    // skip this statement if there is a previous one with a not null databaseId
    MappedStatement previous = this.configuration.getMappedStatement(id, false); // issue #2
    return previous.getDatabaseId() == null;
  }

  private LanguageDriver getLanguageDriver(String lang) {
    Class<? extends LanguageDriver> langClass = null;
    if (lang != null) {
      /**
       * 解析脚本驱动class类型
       */
      langClass = resolveClass(lang);
    }
    return configuration.getLanguageDriver(langClass);
  }

}
