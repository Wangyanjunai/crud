package com.mp.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mp.dao.UserMapper;
import com.mp.entity.User;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UpdateTest {

	private UserMapper userMapper;

	@Autowired
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Test
	public void updateById() {
		User user = new User();
		user.setId(1088248166370832385L);
		user.setAge(26);
		user.setEmail("wtf2@baomidou.com");
		int rows = this.userMapper.updateById(user);
		System.out.println("影响记录数：" + rows);
	}
	
	@Test
	public void updateByWrapper() {
		UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("name", "李艺伟").eq("age", 28);
		User user = new User();
		user.setEmail("lyw2022@baomidou.com");
		user.setAge(29);
		int rows = this.userMapper.update(user, updateWrapper);
		System.out.println("影响记录数：" + rows);
	}
}
