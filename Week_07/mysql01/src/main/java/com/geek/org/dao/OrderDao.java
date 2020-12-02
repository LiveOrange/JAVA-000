package com.geek.org.dao;

import com.geek.org.aop.DataSource;
import com.geek.org.entity.Order;

import java.util.List;

public interface OrderDao {
    @DataSource
    List<Order> selectByPrimaryKey();
}
