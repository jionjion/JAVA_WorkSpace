package com.jionjion.guider.conf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 14345
 *	配置文件测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QQConnectTest {

	@Autowired
	private QQConnect connect;
	
	@Test
	public void test() {
		System.out.println(connect.toString());
	}

}