package com.tuling;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * 数据访问基类
 * 
 * @author Administrator
 *
 */
public class BaseDao {
	// 驱动
	private static String DRIVER = null;
	// 链接字符串
	private static String URL = null;
	// 用户名
	private static String USERNAME = null;
	// 密码
	private static String PASSWORD = null;

	/**
	 * 初始化
	 */
	static {
		init();
	}

	/**
	 * 初始化
	 */
	private static void init() {
		try {
			// 使用Properties对象读取资源文件属性
			Properties pro = new Properties();
			// 获得资源文件输入流
			InputStream inStream = BaseDao.class.getClassLoader()
					.getResourceAsStream("db.properties");
			// 加载输入流
			pro.load(inStream);
			DRIVER = pro.getProperty("mysql.driverClass");
			URL = pro.getProperty("mysql.jdbcUrl");
			USERNAME = pro.getProperty("mysql.user");
			PASSWORD = pro.getProperty("mysql.password");

			Class.forName(DRIVER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取数据库连接对象
	 * 
	 * @return
	 */
	protected Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭所有链接
	 * 
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	protected void CloseAll(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (conn != null) {
				conn.close();
			}
			if (stmt != null) {
				stmt.close();
			}
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 执行 增、删、 改的公共方法
	 * 
	 * @param sql
	 *            SQL语句
	 * @param prams
	 *            参数
	 * @return 受影响的行数
	 */
	protected int executeUpdate(String sql, Object... prams) {
		// 获得数据库链接对象
		Connection conn = getConnection();
		// 声明SQL执行者
		PreparedStatement pstmt = null;
		try {
			// 获得SQL执行者
			pstmt = conn.prepareStatement(sql);

			// 循环加载参数
			for (int i = 0; i < prams.length; i++) {
				pstmt.setObject(i + 1, prams[i]);
			}
			// 执行executeUpdate 返回受影响行数
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 关闭所有需要关闭的对象
			CloseAll(conn, pstmt, null);
		}

		return 0;
	}

	/**
	 * 执行查询 返回单个值
	 * 
	 * @param sql
	 *            SQL语句
	 * @param prams
	 *            参数
	 * @return OBJECT
	 */
	protected Object executeScaler(String sql, Object... prams) {
		// 获得数据库链接对象
		Connection conn = getConnection();
		// 声明SQL执行者
		PreparedStatement pstmt = null;
		// 声明查询结果集
		ResultSet rs = null;
		// 接收单个值
		Object value = null;
		try {
			// 获得SQL执行者
			pstmt = conn.prepareStatement(sql);

			// 循环加载参数
			for (int i = 0; i < prams.length; i++) {
				pstmt.setObject(i + 1, prams[i]);
			}
			// 执行executeUpdate 返回受影响行数
			rs = pstmt.executeQuery();

			if (rs.next()) {
				value = rs.getObject(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseAll(conn, pstmt, rs);
		}
		return value;
	}

	/**
	 * 执行查询返回list
	 * 
	 * @param sql
	 *            SQL语句
	 * @param clazz
	 *            类的类型
	 * @return List
	 */
	public <T> List<T> executeList(String sql, Class<T> clazz, Object... prams) {
		// 数据集合
		List<T> list = new ArrayList<T>();
		// 获得数据库连接
		Connection conn = getConnection();
		// 声明SQL执行者
		PreparedStatement pstmt = null;
		// 声明查询结果集
		ResultSet rs = null;
		try {

			// 3. 通过链接创建一个SQL执行者
			pstmt = conn.prepareStatement(sql);

			// 循环加载参数
			for (int i = 0; i < prams.length; i++) {
				//内部会通过instance of判断这个参数到底是哪个类型的具体对象
				pstmt.setObject(i + 1, prams[i]);
			}

			// 4 执行查询SQL 返回查询结果
			rs = pstmt.executeQuery();

			// 获得结果集的列信息对象
			ResultSetMetaData rsmd = rs.getMetaData();

			while (rs.next()) {
				// 通过类反射实例化
				T obj = clazz.newInstance();

				// 循环所有的列
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					/* 通过属性名称使用反射给泛型实例赋值 Begin */

					// 获得每一列的列名
					String cloName = rsmd.getColumnName(i);
					// 根据列名反射到类的字段
					Field filed = clazz.getDeclaredField(cloName);
					// 设置私有属性的访问权限
					filed.setAccessible(true);

					// 给泛型实例的某一个属性赋值
					filed.set(obj, rs.getObject(cloName));
					/* 通过属性名称使用反射给泛型实例赋值 End */
				}
				// 将泛型实例添加到 泛型集合中
				list.add(obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 执行查询返回JavaBean
	 * 
	 * @param sql
	 *            SQL语句
	 * @param clazz
	 *            类的类型
	 * @return JavaBean
	 */
	public <T> T executeJavaBean(String sql, Class<T> clazz,
			Object... prams) {
		// 声明数据对象
		T obj=null;
		// 获得数据库连接
		Connection conn = getConnection();
		// 声明SQL执行者
		PreparedStatement pstmt = null;
		// 声明查询结果集
		ResultSet rs = null;
		try {

			// 3. 通过链接创建一个SQL执行者
			pstmt = conn.prepareStatement(sql);

			// 循环加载参数
			for (int i = 0; i < prams.length; i++) {
				pstmt.setObject(i + 1, prams[i]);
			}

			// 4 执行查询SQL 返回查询结果
			rs = pstmt.executeQuery();

			// 获得结果集的列信息对象
			ResultSetMetaData rsmd = rs.getMetaData();

			if (rs.next()) {
				// 通过类反射实例化
				obj = clazz.newInstance();

				// 循环所有的列
				for (int i = 1; i <= rsmd.getColumnCount(); i++) {
					/* 通过属性名称使用反射给泛型实例赋值 Begin */

					// 获得每一列的列名
					String cloName = rsmd.getColumnName(i);
					// 根据列名反射到类的字段
					Field filed = clazz.getDeclaredField(cloName);
					// 设置私有属性的访问权限
					filed.setAccessible(true);

					// 给泛型实例的某一个属性赋值
					filed.set(obj, rs.getObject(cloName));
					/* 通过属性名称使用反射给泛型实例赋值 End */
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return obj;
	}
}
