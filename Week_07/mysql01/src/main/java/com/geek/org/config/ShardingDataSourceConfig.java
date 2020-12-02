package com.geek.org.config;

import com.alibaba.druid.pool.ha.HighAvailableDataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.ShardingSphereAlgorithmConfiguration;
import org.apache.shardingsphere.replicaquery.api.config.ReplicaQueryRuleConfiguration;
import org.apache.shardingsphere.replicaquery.api.config.rule.ReplicaQueryDataSourceRuleConfiguration;
import org.assertj.core.util.Lists;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Slf4j
@MapperScan(basePackages = ShardingDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "masterSqlSessionFactory")
@Configuration
public class ShardingDataSourceConfig {
    static final String PACKAGE = "com.geek.org.dao";
    static final String MAPPER_LOCATION = "classpath:mapping/*.xml";

    @Bean(name = "shardingSphereDataSource")
    public DataSource shardingSphereDataSource(@Qualifier("masterDataSource") DataSource master, @Qualifier("slaveDataSource") DataSource slave) throws SQLException {
        //连接池
        Map<String, DataSource> dataSources = new ConcurrentHashMap<>();
        dataSources.put("slave", slave);
        dataSources.put("master", master);

        //读写分离规则
        List<ReplicaQueryDataSourceRuleConfiguration> configurations = new ArrayList<>();
        configurations.add(new ReplicaQueryDataSourceRuleConfiguration("dataSource", "master", Arrays.asList("slave", "master"), "random"));
        Map<String, ShardingSphereAlgorithmConfiguration> loadBalancer = new ConcurrentHashMap<>();
        loadBalancer.put("random", new ShardingSphereAlgorithmConfiguration("random", new Properties()));
        ReplicaQueryRuleConfiguration ruleConfiguration = new ReplicaQueryRuleConfiguration(configurations, loadBalancer);
        return ShardingSphereDataSourceFactory.createDataSource(dataSources, Collections.singleton(ruleConfiguration), new Properties());
    }

    @Bean
    @Primary
    public DataSourceTransactionManager masterTransactionManager(@Qualifier("shardingSphereDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("shardingSphereDataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage(PACKAGE);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
