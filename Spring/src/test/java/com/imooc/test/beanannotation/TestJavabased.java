package com.imooc.test.beanannotation;
/***
 * 	对使用注解方式生成的bean进行测试
 */
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.imooc.beanannotation.javabased.MyDriverManager;
import com.imooc.beanannotation.javabased.Store;
import com.imooc.beanannotation.javabased.StringStore;
import com.imooc.test.base.UnitTestBase;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestJavabased extends UnitTestBase {
	
	public TestJavabased() {
		super("classpath*:spring-beanannotation.xml");
	}
	
	/**测试,获取子类的名称接口Bean的实现,注意bean的名称的定义*/
	@Test
	public void test() {
		Store store = super.getBean("stringStore");
		System.out.println(store.getClass().getName());
	}
	
	/**测试,对配置文件中的属性值的获取**/
	@Test
	public void testMyDriverManager() {
		MyDriverManager manager = super.getBean("myDriverManager");
		System.out.println("读取配置文件成功,创建自定义JDBC连接:"+manager.getClass().getName());
	}
	
	/**测试,对Bean的范围和代理方式的测试**/
	@Test
	public void testScope() {
		Store store = super.getBean("stringStore");
		System.out.println(store.hashCode());
		
		store = super.getBean("stringStore");
		System.out.println(store.hashCode());
	}
	
	/**测试,对store接口容器的不同实现类进行验证**/
	@Test
	public void testG() {
		StringStore store = super.getBean("stringStoreTest");
	}
	
}
