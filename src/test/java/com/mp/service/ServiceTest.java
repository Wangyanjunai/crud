package com.mp.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceTest {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void getOne() {
        User one = this.userService.getOne(Wrappers.<User>lambdaQuery().gt(User::getAge, 25), false);
        System.out.println(one);
    }
}
