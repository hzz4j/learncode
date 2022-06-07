package org.hzz.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceProxyConfig {

    @Bean("originOrder")
    @ConfigurationProperties(prefix = "spring.datasource.order")
    public DataSource dataSourceOrder(){
        return new DruidDataSource();
    }

    @Bean("originStorage")
    @ConfigurationProperties(prefix = "spring.datasource.storage")
    public DataSource dataSourceStorage(){
        return new DruidDataSource();
    }

    @Bean("originAccount")
    @ConfigurationProperties(prefix = "spring.datasource.account")
    public DataSource dataSourceAccount(){
        return new DruidDataSource();
    }


    @Bean("order")
    public DataSourceProxy masterDataSourceProxy(@Qualifier("originOrder") DataSource dataSource){
        return new DataSourceProxy(dataSource);
    }

    @Bean(name = "storage")
    public DataSourceProxy storageDataSourceProxy(@Qualifier("originStorage") DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    @Bean(name = "account")
    public DataSourceProxy payDataSourceProxy(@Qualifier("originAccount") DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }

    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource(@Qualifier("order") DataSource dataSourceOrder,
                                        @Qualifier("storage") DataSource dataSourceStorage,
                                        @Qualifier("account") DataSource dataSourcePay) {

        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();

        // 数据源的集合
        Map<Object, Object> dataSourceMap = new HashMap<>(3);
        dataSourceMap.put(DataSourceKey.ORDER.name(), dataSourceOrder);
        dataSourceMap.put(DataSourceKey.STORAGE.name(), dataSourceStorage);
        dataSourceMap.put(DataSourceKey.ACCOUNT.name(), dataSourcePay);

        dynamicRoutingDataSource.setDefaultTargetDataSource(dataSourceOrder);
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);

        DynamicDataSourceContextHolder.getDataSourceKeys().addAll(dataSourceMap.keySet());

        return dynamicRoutingDataSource;
    }

    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("dynamicDataSource") DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        org.apache.ibatis.session.Configuration configuration=new org.apache.ibatis.session.Configuration();
        //使用jdbc的getGeneratedKeys获取数据库自增主键值
        configuration.setUseGeneratedKeys(true);
        //使用列别名替换列名
        configuration.setUseColumnLabel(true);
        //自动使用驼峰命名属性映射字段，如userId ---> user_id
        configuration.setMapUnderscoreToCamelCase(true);
        sqlSessionFactoryBean.setConfiguration(configuration);

        return sqlSessionFactoryBean;
    }

}
