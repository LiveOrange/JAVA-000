package io.spring01.runner;

import io.spring01.bean.Student;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:applicationContext.xml")
public class StudentConfiguration {

    @Bean(name = "student02")
    public Student student02() {
        Student student = new Student();
        student.setId(2);
        student.setName("KK02");
        return student;
    }
}
