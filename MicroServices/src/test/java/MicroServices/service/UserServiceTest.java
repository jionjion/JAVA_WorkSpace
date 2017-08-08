package MicroServices.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import MicroServices.bean.User;

/**测试Service层的方法*/
@RunWith(SpringRunner.class)
@SpringBootTest				//启动SpringBoot
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Test			
	public void testFindOne() {			//测试查找一个
		User user = userService.findOne(1);	//查找第一个
		Assert.assertEquals(new String("123456"), user.getPassword());	//断言数据库的密码和查询中的是否一致
	}
}
