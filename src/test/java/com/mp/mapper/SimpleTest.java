package com.mp.mapper;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mp.dao.UserMapper;
import com.mp.entity.User;


@SpringBootTest
@RunWith(SpringRunner.class)
public class SimpleTest {

	private UserMapper userMapper;

	@Autowired
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}
	
	@Test
	public void select() {
		List<User> list = this.userMapper.selectList(null);
		Assert.assertEquals(5, list.size());
		list.forEach(System.out::println);
	}
	
}
