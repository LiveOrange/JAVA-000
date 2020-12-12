package com.geek.controller;

import com.geek.sharding.dao.AccountDao;
import com.geek.sharding.dao.UserDao;
import com.geek.sharding.entity.AccountDO;
import com.geek.sharding.entity.OrderDO;
import com.geek.sharding.entity.UserDO;
import com.geek.transaction.XAOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
public class ManagerController {
    @Autowired
    private UserDao userDao;
    @Autowired
    private AccountDao accountDao;
    @Autowired
    private XAOrderService orderService;

    @RequestMapping("/queryUser")
    @ResponseBody
    public List<UserDO> queryUser() {
        return userDao.queryUserAll();
    }

    @RequestMapping("/saveUser")
    @ResponseBody
    public Boolean saveUser(UserDO user) {
        return userDao.insertUser(user) == 1;
    }

    @RequestMapping("/updateUser")
    @ResponseBody
    public Boolean updateUser(Long id, String name) {
        return userDao.updateUser(name, id) == 1;
    }

    @RequestMapping("/saveOrder")
    @ResponseBody
    public boolean saveOrder(OrderDO orderDO) {
        log.info("saveOrder OrderInfo={}", orderDO);
        orderService.saveOrder(orderDO);
        return true;
    }

    @RequestMapping("/queryAccount")
    @ResponseBody
    public AccountDO queryAccount(Long consumerId) {
        return accountDao.queryAccountById(consumerId);
    }

}
