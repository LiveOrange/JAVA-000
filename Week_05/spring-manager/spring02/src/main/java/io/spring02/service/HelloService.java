package io.spring02.service;

import io.spring02.aop.MyCache;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    @MyCache(2)
    public String queryByName(String name) {
        System.out.println("queryByName query" + name);
        return "query" + name;
    }

    @MyCache
    public String queryById(int id) {
        System.out.println("queryById query" + id);
        return "query" + id;
    }
}
