package com.jionjion.guider.dao.japRepository;

import static org.junit.Assert.*;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jionjion.guider.bean.User;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Transactional // 当在测试中添加事务时,会在测试结束自动回滚事务
public class UserRepositoryTest {

	
	@Autowired
	UserRepository repository; 
	
	/** 通过uuid查询 */
	@Test
	public void testFindByUuid() {
		String uuid = "402881816929f363016929f3890d0000";
		User user = repository.findByUuid(uuid);
		assertNotNull(user);
	}

	/** 通过id查询 */
	@Test
	public void testFindById() {
		
		User user = repository.findById(1);
		assertNotNull(user);
	}
	
	/** 用户名查询 */
	@Test
	public void testFindByUsername() {
		User user = repository.findByUsername("JionJion");
		assertNotNull(user);
	}

	/** 查询全部 */
	@Test
	public void testFindAll() {
		List<User> list = repository.findAll();
		assertNotNull(list);
	}

	/** 保存 */
	@Test
	public void testSave() {
		User user = new User();
		user.setUsername("JionJion");
		user.setPassword("123456");
		user.setRuleCode("ADMIN");
		user.setPhone("15516559772");
		user.setEmail("1434501783@qq.com");
		user.setAddress("上海");
		user = repository.save(user);
		log.info("保存成功:" + user);
	}

	/** 删除单条 */
	@Test
	public void testDeleteById() {
		int deleteCount = repository.deleteById(1);
		assertEquals("是否删除为1条", 1, deleteCount);
	}

}
