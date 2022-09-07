package com.mp.mapper;

import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mp.dao.UserMapper;
import com.mp.entity.User;

@SpringBootTest
@RunWith(SpringRunner.class)
public class InsertTest {

	private UserMapper userMapper;

	@Autowired
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Test
	public void insert() {
		User user = new User();
		user.setName("向后");
		user.setAge(25);
		user.setEmail("xh@baomidou.com");
		user.setManagerId(1088248166370832385L);
		user.setCreateTime(LocalDateTime.now());
		int rows = this.userMapper.insert(user);
		System.out.println("影响记录数：" + rows);
	}
}
