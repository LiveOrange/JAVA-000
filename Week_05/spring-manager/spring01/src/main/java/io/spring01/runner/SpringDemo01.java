package io.spring01.runner;

import io.spring01.bean.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = StudentConfiguration.class)
public class SpringDemo01 {

    @Autowired
    private ApplicationContext context;

    @Autowired
    @Qualifier("student03")
    private Student student03;

    @Test
    public void test() {
        Student student = (Student) context.getBean("student01");
        System.out.println(student.toString());

        Student student02 = (Student) context.getBean("student02");
        System.out.println(student02.toString());

        System.out.println(student03.toString());
    }
}
