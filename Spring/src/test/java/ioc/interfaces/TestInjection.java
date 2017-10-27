package ioc.interfaces;
/**
 * 	Spring的注入方式
 * 	1.设值注入
 * 	2.构造注入
 */
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import base.UnitTestBase;
import ioc.injection.service.InjectionService;


@RunWith(BlockJUnit4ClassRunner.class)
public class TestInjection extends UnitTestBase {
	
	public TestInjection() {
		super("classpath:spring-injection.xml");
	}
	
	/**通过设置注入,完成注入*/
	@Test
	public void testSetter() {
		InjectionService service = super.getBean("injectionService");
		service.save("这是要保存的数据");
	}
	
	/**通过构造注入,完成注入*/
	@Test
	public void testCons() {
		InjectionService service = super.getBean("injectionService");
		service.save("这是要保存的数据");
	}
	
}
