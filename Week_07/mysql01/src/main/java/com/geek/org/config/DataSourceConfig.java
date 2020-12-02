package com.geek.org.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.ha.HighAvailableDataSource;
import com.alibaba.druid.pool.ha.selector.DataSourceSelectorEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Slf4j
@Configuration
public class DataSourceConfig {

    @Value("${druid.datasource.master.url}")
    private String masterUrl;
    @Value("${druid.datasource.slave.url}")
    private String slaveUrl;
    @Value("${druid.datasource.username}")
    private String user;
    @Value("${druid.datasource.password}")
    private String password;
    @Value("${jdbc.driver.name}")
    private String driverName;

    @Bean(name = "masterDataSource", initMethod = "init", destroyMethod = "close")
    @Primary
    public DataSource masterDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setUrl(masterUrl);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "slaveDataSource", initMethod = "init", destroyMethod = "close")
    @Primary
    public DataSource slaveDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverName);
        dataSource.setUrl(slaveUrl);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "highAvailableDataSource")
    public HighAvailableDataSource highAvailableDataSource(@Qualifier("masterDataSource") DataSource master, @Qualifier("slaveDataSource") DataSource slave) {
        HighAvailableDataSource highAvailableDataSource = new HighAvailableDataSource();
        highAvailableDataSource.setSelector(DataSourceSelectorEnum.BY_NAME.getName());

        Map<String, DataSource> dataSource = new ConcurrentHashMap<>();
        dataSource.put("default", master);
        dataSource.put("slave", slave);
        dataSource.put("master", master);
        highAvailableDataSource.setDataSourceMap(dataSource);
        return highAvailableDataSource;
    }
}
