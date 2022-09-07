package com.mp.mapper;

import com.mp.dao.UserMapper;
import com.mp.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ARTest {

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Test
    public void insert() {
        User user = new User();
        user.setName("刘花");
        user.setAge(29);
        user.setEmail("lh@baomidou.com");
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        boolean insert = user.insert();
        System.out.println("插入记录是否成功：" + (insert ? "是" : "否"));
    }

    @Test
    public void insertOrUpdate() {
        User user = new User();
        user.setName("刘强");
        user.setAge(25);
        user.setEmail("lq@baomidou.com");
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        boolean insert = user.insertOrUpdate();
        System.out.println("插入记录是否成功：" + (insert ? "是" : "否"));
    }

    @Test
    public void insertOrUpdate2() {
        User user = new User();
        user.setId(1567334028265803777L);
        user.setAge(26);
        boolean insert = user.insertOrUpdate();
        System.out.println("插入记录是否成功：" + (insert ? "是" : "否"));
    }

    @Test
    public void selectById() {
        User user = new User();
        User selectUser = user.selectById(1094590409767661570L);
        System.out.println("是否同一个对象：" + (selectUser == user ? "是" : "否"));
        System.out.println(selectUser);
    }

    @Test
    public void selectById2() {
        User user = new User();
        user.setId(1094590409767661570L);
        User selectUser = user.selectById();
        System.out.println("是否同一个对象：" + (selectUser == user ? "是" : "否"));
        System.out.println(selectUser);
    }

    @Test
    public void deleteById() {
        User user = new User();
        user.setId(1094590409767661570L);
        boolean deleteById = user.deleteById();
        System.out.println("根据id删除数据是否成功：" + (deleteById ? "是" : "否"));
        System.out.println(deleteById);
    }
}
