package com.geek.sharding.controller;

import com.geek.sharding.dao.UserDao;
import com.geek.sharding.entity.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ManagerController {
    @Autowired
    private UserDao userDao;

    @RequestMapping("/queryUser")
    @ResponseBody
    public List<UserDO> queryUser() {
        return userDao.queryUserAll();
    }

    @RequestMapping("/saveUser")
    @ResponseBody
    public Boolean saveUser(String name) {
        return userDao.insertUser(name) == 1;
    }

    @RequestMapping("/updateUser")
    @ResponseBody
    public Boolean updateUser(Long id, String name) {
        return userDao.updateUser(name, id) == 1;
    }
}
