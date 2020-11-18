package io.spring02.runner;


import io.spring02.config.CacheConfiguration;
import io.spring02.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CacheConfiguration.class)
public class SpringMyCache {
    @Autowired
    private HelloService helloService;

    @Test
    public void test() throws InterruptedException {
       System.out.println(helloService.queryByName("xmw1"));
        System.out.println(helloService.queryByName("xmw1"));
        Thread.sleep(50);
        /*System.out.println(helloService.queryById(15));
        System.out.println(helloService.queryById(15));
        System.out.println(helloService.queryById(15));*/
        Thread.sleep(2000);
        System.out.println(helloService.queryByName("xmw1"));
        System.out.println(helloService.queryByName("xmw1"));
        System.out.println(helloService.queryByName("xmw1"));
        System.out.println(helloService.queryByName("xmw1"));
    }
}
