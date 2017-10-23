package beanannotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import base.UnitTestBase;
import beanannotation.injection.service.InjectionService;
import beanannotation.multibean.BeanInvoker;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestInjection extends UnitTestBase {
	
	public TestInjection() {
		super("classpath:spring-beanannotation.xml");
	}
	
	/**对自动注入进行验证*/
	@Test
	public void testAutowired() {
		InjectionService service = super.getBean("injectionServiceImpl");
		service.save("使用注解自动注入");
	}
	
	/**对带有泛型的类实现对泛型的自动注入*/
	@Test
	public void testMultiBean() {
		BeanInvoker invoker = super.getBean("beanInvoker");
		invoker.say();
	}
	
}
