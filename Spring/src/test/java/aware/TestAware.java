package aware;
/***
 * 	默认使用在IOC创建时,自动加载所有Bean,会默认执行实现了资源接口的方法
 */
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import base.UnitTestBase;


@RunWith(BlockJUnit4ClassRunner.class)
public class TestAware extends UnitTestBase {
	
	public TestAware() {
		super("classpath:spring-aware.xml");
	}
	
	@Test
	public void testMoocApplicationContext() {
		System.out.println("测试获取Bean的上下文对象 : " + super.getBean("moocApplicationContext").hashCode());
	}
	
	@Test
	public void textMoocBeanName() {
		System.out.println("测试获取Bean的名字 : " + super.getBean("moocBeanName").hashCode());
	}
}
