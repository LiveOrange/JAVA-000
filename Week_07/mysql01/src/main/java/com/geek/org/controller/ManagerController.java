package com.geek.org.controller;


import com.geek.org.dao.OrderDao;
import com.geek.org.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ManagerController {
    @Autowired
    private OrderDao orderDao;

    @RequestMapping("/test")
    @ResponseBody
    public List<Order> getRequest() {
        System.out.println("get Request!!");
        return orderDao.selectByPrimaryKey();
    }
}
