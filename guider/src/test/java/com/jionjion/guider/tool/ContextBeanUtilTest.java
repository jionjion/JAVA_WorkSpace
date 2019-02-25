package com.jionjion.guider.tool;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jionjion.guider.bean.HeaderImage;

/**
 * @author 14345
 *	测试获得容器,bean对象的方法
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContextBeanUtilTest {

	/**
	 * 	获得上下文容器
	 */
	@Test
	public void testGetApplicationContext() {
		
		assertNotNull(ContextBeanUtil.getApplicationContext());
	}

	/**
	 * 	通过bean的名字获得
	 */
	@Test
	public void testGetBeanString() {
		assertNotNull(ContextBeanUtil.getBean("headerImage"));
	}

	/**
	 * 	通过bean的类型获得
	 */
	@Test
	public void testGetBeanClassOfT() {
		assertNotNull(ContextBeanUtil.getBean(HeaderImage.class));
	}

	/**
	 * 	通过bean的类型和子类的名字获得
	 * 	用于接口的不同实现类
	 */
	@Test
	public void testGetBeanStringClassOfT() {
		assertNotNull(ContextBeanUtil.getBean("headerImage", HeaderImage.class));
	}

}
