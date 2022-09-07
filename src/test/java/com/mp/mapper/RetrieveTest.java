package com.mp.mapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.mp.dao.UserMapper;
import com.mp.entity.User;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RetrieveTest {

	private UserMapper userMapper;

	@Autowired
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Test
	public void selectById() {
		User user = this.userMapper.selectById(1094590409767661570L);
		System.out.println(user);
	}

	@Test
	public void selectIds() {
		List<Long> idsList = Arrays.asList(1565586074026827777L, 1088250446457389058L, 1094590409767661570L);
		List<User> users = this.userMapper.selectBatchIds(idsList);
		users.forEach(System.out::println);
	}
	
	@Test
	public void selectByMap() {
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("name", "王天风");
		columnMap.put("age", 25);
		List<User> users = this.userMapper.selectByMap(columnMap);
		users.forEach(System.out::println);
	}
	
	/**
	 * 查询
	 * 需求1、名字中包含雨并且年龄小于40
	 *      name like '%雨%' and age < 40
	 */
	@Test
	public void selectByWrapper1() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.like("name", "雨").lt("age", 40);
		List<User> users = this.userMapper.selectList(queryWrapper );
		users.forEach(System.out::println);
	}
	
	/**
	 * 查询
	 * 需求2、名字中包含雨年并且龄大于等于20且小于等于40并且email不为空
   	 *      name like '%雨%' and age between 20 and 40 and email is not null
	 */
	@Test
	public void selectByWrapper2() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.like("name", "雨").between("age", 20, 40).isNotNull("email");
		List<User> users = this.userMapper.selectList(queryWrapper );
		users.forEach(System.out::println);
	}	
	
	/**
	 * 查询
	 * 需求3、名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列
	 *      name like '王%' or age>=25 order by age desc,id asc
	 */
	@Test
	public void selectByWrapper3() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.likeRight("name", "王").or().ge("age", 25).orderByDesc("age").orderByAsc("id");
		List<User> users = this.userMapper.selectList(queryWrapper );
		users.forEach(System.out::println);
	}	
	
	/**
	 * 查询
	 * 需求4、创建日期为2019年2月14日并且直属上级为名字为王姓
	 *      date_format(create_time,'%Y-%m-%d')='2019-02-14' and manager_id in (select id from user where name like '王%')
	 */
	@Test
	public void selectByWrapper4() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.apply("date_format(create_time,'%Y-%m-%d') = {0}", "2019-02-14").inSql("manager_id", "select id from user where name like '王%'");
		List<User> users = this.userMapper.selectList(queryWrapper );
		users.forEach(System.out::println);
	}	
	
	/**
	 * 查询
	 * 需求5、名字为王姓并且（年龄小于40或邮箱不为空）
	 *      name like '王%' and (age<40 or email is not null)
	 */
	@Test
	public void selectByWrapper5() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.likeRight("name", "王").and(wq -> wq.lt("age", 40).or().isNotNull("email"));
		List<User> users = this.userMapper.selectList(queryWrapper );
		users.forEach(System.out::println);
	}	
	
	/**
	 * 查询
	 * 需求6、名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）
	 *      name like '王%' or (age < 40 and age > 20 and email is not null)
	 */
	@Test
	public void selectByWrapper6() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.likeRight("name", "王").or(wq -> wq.lt("age", 40).gt("age", 20).isNotNull("email"));
		List<User> users = this.userMapper.selectList(queryWrapper );
		users.forEach(System.out::println);
	}	
	
	/**
	 * 查询
	 * 需求7、（年龄小于40或邮箱不为空）并且名字为王姓
	 *      (age < 40 or email is not null) and name like '王%'
	 */
	@Test
	public void selectByWrapper7() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.and(wq -> wq.lt("age", 40).or().isNotNull("email")).likeRight("name", "王");
		List<User> users = this.userMapper.selectList(queryWrapper );
		users.forEach(System.out::println);
	}
	
	/**
	 * 查询
	 * 需求8、年龄为30、31、34、35
	 *      age in (30、31、34、35) 
	 */
	@Test
	public void selectByWrapper8() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		List<Integer> ageList = Arrays.asList(30, 31, 34, 35);
		queryWrapper.in("age", ageList);
		List<User> users = this.userMapper.selectList(queryWrapper);
		users.forEach(System.out::println);
	}
	
	/**
	 * 查询
	 * 需求9、只返回满足条件的其中一条语句即可
	 *      limit 1
	 */
	@Test
	public void selectByWrapper9() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		List<Integer> ageList = Arrays.asList(30, 31, 34, 35);
		queryWrapper.in("age", ageList).last("limit 1");
		List<User> users = this.userMapper.selectList(queryWrapper);
		users.forEach(System.out::println);
	}
	
	/**
	 * 查询
	 * 需求10、名字中包含雨并且年龄小于40(需求1加强版)
	 *       第一种情况：select id, name from user where name like '%雨%' and age < 40
	 */
	@Test
	public void selectByWrapperSupper1() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.select("id", "name").like("name", "雨").lt("age", 40);
		List<User> users = this.userMapper.selectList(queryWrapper);
		users.forEach(System.out::println);
	}
	
	/**
	 * 查询
	 * 需求10、名字中包含雨并且年龄小于40(需求1加强版)
	 *       第二种情况：select id, name, age, email from user where name like '%雨%' and age < 40
	 */
	@Test
	public void selectByWrapperSupper2() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.like("name", "雨").lt("age", 40).select(User.class, info -> !info.getColumn().equals("create_time") && !info.getColumn().equals("manager_id"));
		List<User> users = this.userMapper.selectList(queryWrapper);
		users.forEach(System.out::println);
	}
	
	@Test
	public void testCondition() {
		String name = "";
		String email = "x";
		this.condition(name, email);
	}
	
	/**
	 * 实体作为条件构造器构造方法
	 */
	@Test
	public void selectByWrapperEntity() {
		User whereUser = new User();
		whereUser.setName("刘红雨");
		whereUser.setAge(32);
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>(whereUser);
		List<User> userList = this.userMapper.selectList(queryWrapper);
		userList.forEach(System.out::println);
	}
	
	/**
	 * AllEq用法
	 */
	@Test
	public void selectByWrapperAllEq() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("name", "王天风");
		paramsMap.put("age", null);
		queryWrapper.allEq(paramsMap, false);
		List<User> userList = this.userMapper.selectList(queryWrapper);
		userList.forEach(System.out::println);
	}
	
	/**
	 * 其他使用条件构造器的方法
	 */
	@Test
	public void selectByWrapperMaps() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.like("name", "雨").lt("age", 40);
		List<Map<String, Object>> userList = this.userMapper.selectMaps(queryWrapper);
		userList.forEach(System.out::println);
	}
	
	/**
	 * 查询
	 * 需求11、按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄。并且只取年龄总和小于500的组。
     * select avg(age) avg_age, min(age) min_age, max(age) max_age from user group by manager_id having sum(age) < 500
	 */
	@Test
	public void selectByWrapperMaps2() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.select("avg(age) avg_age", "min(age) min_age", "max(age) max_age").groupBy("manager_id").having("sum(age) < {0}", 500);
		List<Map<String, Object>> userList = this.userMapper.selectMaps(queryWrapper);
		userList.forEach(System.out::println);
	}
	
	
	/**
	 * 其他使用条件构造器的方法
	 */
	@Test
	public void selectByWrapperObjs() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.like("name", "雨").lt("age", 40);
		List<Object> userList = this.userMapper.selectObjs(queryWrapper);
		userList.forEach(System.out::println);
	}
	
	/**
	 * 查询记录数
	 */
	@Test
	public void selectByWrapperCount() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.like("name", "雨").lt("age", 40);
		Integer count = this.userMapper.selectCount(queryWrapper);
		System.out.println("总记录数：" + count);
	}
	
	/**
	 * 查询一条记录
	 */
	@Test
	public void selectByWrapperOne() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.like("name", "刘红雨").lt("age", 40);
		User user = this.userMapper.selectOne(queryWrapper);
		System.out.println(user);
	}
	
	/**
	 * lambda条件构造器
	 */
	@Test
	public void selectLambda1() {
		//LambdaQueryWrapper<User> lambdaQueryWrapper01 = new QueryWrapper<User>().lambda();
		//LambdaQueryWrapper<User> lambdaQueryWrapper02 = new LambdaQueryWrapper<User>();
		LambdaQueryWrapper<User> lambdaQueryWrapper03 = Wrappers.<User>lambdaQuery();
		lambdaQueryWrapper03.like(User::getName, "雨").lt(User::getAge, 40);
		//where name like '%雨%' and age < 40
		List<User> userList = this.userMapper.selectList(lambdaQueryWrapper03);
		userList.forEach(System.out::println);
	}
	
	/**
	 * 查询
	 * 需求5、名字为王姓并且（年龄小于40或邮箱不为空）
	 *      name like '王%'  and (age<40 or email is not null)
	 */
	@Test
	public void selectLambda2() {
		//LambdaQueryWrapper<User> lambdaQueryWrapper01 = new QueryWrapper<User>().lambda();
		//LambdaQueryWrapper<User> lambdaQueryWrapper02 = new LambdaQueryWrapper<User>();
		LambdaQueryWrapper<User> lambdaQueryWrapper03 = Wrappers.<User>lambdaQuery();
		lambdaQueryWrapper03.likeRight(User::getName, "王").and(lwq -> lwq.lt(User::getAge, 40).or().isNotNull(User::getEmail));
		//where name like '%雨%' and age < 40
		List<User> userList = this.userMapper.selectList(lambdaQueryWrapper03);
		userList.forEach(System.out::println);
	}
	
	/**
	 * 查询
	 * 需求5、名字为王姓并且（年龄小于40或邮箱不为空）
	 *      name like '王%'  and (age<40 or email is not null)
	 * @param <T>
	 */
	@Test
	public void selectLambda3() {
		List<User> userList = new LambdaQueryChainWrapper<User>(userMapper).like(User::getName, "雨").ge(User::getAge, 20).list();
		userList.forEach(System.out::println);
	}
	
	
	/**
	 * 查询 自定义sql
	 * 需求5、名字为王姓并且（年龄小于40或邮箱不为空）
	 *      name like '王%'  and (age<40 or email is not null)
	 */
	@Test
	public void selectCustomSql() {
		//LambdaQueryWrapper<User> lambdaQueryWrapper01 = new QueryWrapper<User>().lambda();
		//LambdaQueryWrapper<User> lambdaQueryWrapper02 = new LambdaQueryWrapper<User>();
		LambdaQueryWrapper<User> lambdaQueryWrapper03 = Wrappers.<User>lambdaQuery();
		lambdaQueryWrapper03.likeRight(User::getName, "王").and(lwq -> lwq.lt(User::getAge, 40).or().isNotNull(User::getEmail));
		//where name like '%雨%' and age < 40
		List<User> userList = this.userMapper.selectAll(lambdaQueryWrapper03);
		userList.forEach(System.out::println);
	}
	
	/**
	 * 查询 自定义sql
	 * 需求5、名字为王姓并且（年龄小于40或邮箱不为空）
	 *      name like '王%'  and (age<40 or email is not null)
	 */
	@Test
	public void selectCustomSql2() {
		//LambdaQueryWrapper<User> lambdaQueryWrapper01 = new QueryWrapper<User>().lambda();
		//LambdaQueryWrapper<User> lambdaQueryWrapper02 = new LambdaQueryWrapper<User>();
		LambdaQueryWrapper<User> lambdaQueryWrapper03 = Wrappers.<User>lambdaQuery();
		lambdaQueryWrapper03.likeRight(User::getName, "王").and(lwq -> lwq.lt(User::getAge, 40).or().isNotNull(User::getEmail));
		//where name like '%雨%' and age < 40
		List<User> userList = this.userMapper.selectAll2(lambdaQueryWrapper03);
		userList.forEach(System.out::println);
	}
	
	/**
	 * 查询
	 * 需求1、名字中包含雨并且年龄小于40
	 *      name like '%雨%' and age < 40
	 */
	@Test
	public void selectPage() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.lt("age", 40);
		Page<User> page = new Page<User>(1, 2, false);
		/*
		 * IPage<User> pageUser = this.userMapper.selectPage(page, queryWrapper);
		 * System.out.println("总页数：" + pageUser.getPages()); 
		 * System.out.println("总记录数：" + pageUser.getTotal()); 
		 * List<User> userList = pageUser.getRecords();
		 */
		IPage<Map<String, Object>> pageUser = this.userMapper.selectMapsPage(page, queryWrapper);
		System.out.println("总页数：" + pageUser.getPages()); 
		System.out.println("总记录数：" + pageUser.getTotal()); 
		List<Map<String, Object>> userList = pageUser.getRecords();
		userList.forEach(System.out::println);
	}
	
	/**
	 * 查询
	 * 需求1、名字中包含雨并且年龄小于40
	 *      name like '%雨%' and age < 40
	 */
	@Test
	public void selectCustomPage() {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.lt("age", 40);
		Page<User> page = new Page<User>(1, 2);
		/*
		 * IPage<User> pageUser = this.userMapper.selectPage(page, queryWrapper);
		 * System.out.println("总页数：" + pageUser.getPages()); 
		 * System.out.println("总记录数：" + pageUser.getTotal()); 
		 * List<User> userList = pageUser.getRecords();
		 */
		IPage<User> pageUser = this.userMapper.selectUserPage(page, queryWrapper);
		System.out.println("总页数：" + pageUser.getPages()); 
		System.out.println("总记录数：" + pageUser.getTotal()); 
		List<User> userList = pageUser.getRecords();
		userList.forEach(System.out::println);
	}
	
	
	private void condition(String name, String email) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//		if (StringUtils.isNotEmpty(name)) {
//			queryWrapper.like("name", name);
//		}
//		if (StringUtils.isNotEmpty(email)) {
//			queryWrapper.like("email", email);
//		}
		queryWrapper.like(StringUtils.isNotEmpty(name), "name", name).like(StringUtils.isNotEmpty(email), "email", email);
		List<User> userList = this.userMapper.selectList(queryWrapper);
		userList.forEach(System.out::println);
	}
}
