package io.spring02.config;

import io.spring02.bean.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutoConfiguration {
    @Bean
    public Student student02() {
        return new Student();
    }
}
