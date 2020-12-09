package com.geek.sharding.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.io.File;

@Data
@Slf4j
@Configuration
public class DataSourceConfig {
    @Bean
    public DataSource dataSource() {
        DataSource dataSource = null;
        try {
            File file = new ClassPathResource("config-sharding.yml").getFile();
            dataSource = YamlShardingSphereDataSourceFactory.createDataSource(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }
}
