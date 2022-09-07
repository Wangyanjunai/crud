package com.mp.mapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mp.dao.UserMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DeleteTest {

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Test
    public void deleteById() {
        int rows = this.userMapper.deleteById(1567046313645359105L);
        System.out.println("删除记录数：" + rows);
    }

    @Test
    public void deleteByMap() {
        Map<String, Object> columnMap = new HashMap<String, Object>();
        columnMap.put("name", "向后");
        columnMap.put("age", 25);
        int rows = this.userMapper.deleteByMap(columnMap);
        System.out.println("删除记录数：" + rows);
    }

    @Test
    public void deleteBatchIds() {
        int rows = this.userMapper.deleteBatchIds(Arrays.asList(1567047841382866945L, 1567046313645359105L, 1566607437147578369L));
        System.out.println("删除记录数：" + rows);
    }

    @Test
    public void deleteByWrapper() {
        LambdaQueryWrapper<User> lambdaQueryWrapper = Wrappers.<User>lambdaQuery();
        lambdaQueryWrapper.eq(User::getAge, 26).or().gt(User::getAge, 40);
        int rows = this.userMapper.delete(lambdaQueryWrapper);
        System.out.println("删除记录数：" + rows);
    }
}
