package io.kimmking.dubbo.demo.provider;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Data
@Slf4j
@Configuration
@MapperScan("io.kimmking.dubbo.demo.provider.dao")
public class DataSourceConfig {
    static final String PACKAGE = "io.kimmking.dubbo.demo.provider.dao";

    @Value("${druid.datasource.url}")
    private String url;
    @Value("${druid.datasource.username}")
    private String user;
    @Value("${druid.datasource.password}")
    private String password;

    @Bean(name = "dataSource", initMethod = "init", destroyMethod = "close")
    @Primary
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    @Primary
    public DataSourceTransactionManager txManager(final @Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Primary
    public SqlSessionFactory sqlSessionFactory(final @Qualifier("dataSource") DataSource dataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage(PACKAGE);
        return sessionFactory.getObject();
    }
}
