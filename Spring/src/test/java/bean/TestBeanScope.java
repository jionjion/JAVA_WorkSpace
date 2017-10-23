package bean;
/**对Bean的作用范围进行验证
 * 通过两侧的请求IOC,获取到的对象打印其哈希码验证**/
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import base.UnitTestBase;


@RunWith(BlockJUnit4ClassRunner.class)
public class TestBeanScope extends UnitTestBase {
	
	public TestBeanScope() {
		super("classpath*:spring-beanscope.xml");
	}
	
	@Test
	public void testSay() {
		//在方法中请求IOC获得两次对象,对象的哈希码验证
		BeanScope beanScope = super.getBean("beanScope");
		beanScope.say();
		
		BeanScope beanScope2 = super.getBean("beanScope");
		beanScope2.say();
	}
	
	@Test
	public void testSay2() {
		BeanScope beanScope  = super.getBean("beanScope");
		beanScope.say();
	}

}
