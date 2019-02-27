package com.jionjion.guider.service;

import static org.junit.Assert.*;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jionjion.guider.bean.User;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 14345
 *	测试用户服务类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
//@Transactional
public class UserServiceTest {

	@Autowired
	private UserService service;
	
	@Test
	public void testFindByUuid() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindById() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testSave() {
		User user = new User();
		user.setUsername("JionJion");
		user.setPassword("123456");
		user.setRuleCode("ADMIN");
		user.setPhone("15516559772");
		user.setEmail("1434501783@qq.com");
		user.setAddress("上海");
		user = service.save(user);
		log.info("保存成功:" + user);
	}

	@Test
	public void testDeleteById() {
		fail("Not yet implemented");
	}

}
