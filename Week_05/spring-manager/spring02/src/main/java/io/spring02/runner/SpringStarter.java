package io.spring02.runner;

import io.spring02.bean.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableAutoConfiguration
public class SpringStarter {
    @Autowired
    private Student student;

    @Test
    public void test() throws InterruptedException {
        student.init();
    }
}
