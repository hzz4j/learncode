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
package org.apache.ibatis.executor;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;


/**
* @vlog: 高于生活，源于生活
* @desc: 类的描述:sql执行器接口,主要用于维护一级缓存和二级缓存,并且提供事务管理功能
 *        Executor
 *          --BaseExecutor(一级缓存)
 *            --batchExecutor(批量执行器)
 *            --ReUseExecutor(可重用的)
 *            --SimpleExecutor简单的
 *          --CacheExecutor(加入了二级缓存)
* @author: xsls
* @createDate: 2019/9/9 19:40
* @version: 1.0
*/
public interface Executor {

  //ResultHandler 对象的枚举
  ResultHandler NO_RESULT_HANDLER = null;

  /**
   * 更新 or 插入 or 删除，由传入的 MappedStatement 的 SQL 所决定
   * @param ms 我们的执行sql包装对象（MappedStatement）
   * @param parameter 执行的参数
   * @return
   * @throws SQLException
   */
  int update(MappedStatement ms, Object parameter) throws SQLException;

  /**
   * 查询带缓存key查询
   * @param ms 我们的执行sql包装对象（MappedStatement）
   * @param parameter:参数
   * @param rowBounds 逻辑分页参数
   * @param resultHandler:返回结果处理器
   * @param cacheKey:缓存key
   * @param boundSql：我们的sql对象
   * @return 查询结果集list
   * @throws SQLException
   */
  <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException;

  /**
   * 不走缓存查询
   * @param ms 我们的执行sql包装对象（MappedStatement）
   * @param parameter:参数
   * @param rowBounds 逻辑分页参数
   * @param resultHandler:返回结果处理器
   * @return 结果集list
   * @throws SQLException
   */
  <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException;

  /**
   * 调用存过查询返回游标对象
   * @param ms 我们的执行sql包装对象（MappedStatement）
   * @param parameter:参数
   * @param rowBounds 逻辑分页参数
   * @return Cursor数据库游标
   * @throws SQLException
   */
  <E> Cursor<E> queryCursor(MappedStatement ms, Object parameter, RowBounds rowBounds) throws SQLException;

  /**
   * 刷入批处理语句
   * @return List<BatchResult>
   * @throws SQLException
   */
  List<BatchResult> flushStatements() throws SQLException;

  //提交事务
  void commit(boolean required) throws SQLException;

  //回滚事务
  void rollback(boolean required) throws SQLException;

  //创建缓存key
  CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql);

  // 判断是否缓存
  boolean isCached(MappedStatement ms, CacheKey key);
  // 清除本地缓存
  void clearLocalCache();

  // 延迟加载
  void deferLoad(MappedStatement ms, MetaObject resultObject, String property, CacheKey key, Class<?> targetType);

  //获取一个事务
  Transaction getTransaction();
  // 关闭事务
  void close(boolean forceRollback);

  //判断是否关闭
  boolean isClosed();

  // 设置包装的 Executor 对象
  void setExecutorWrapper(Executor executor);

}
