package beanannotation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import base.UnitTestBase;
import beanannotation.multibean.BeanInvoker;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestMulti extends UnitTestBase {

	public TestMulti() {
		super("classpath:spring-beanannotation.xml");
	}
	
	/**对带有泛型的类实现对泛型的自动注入*/
	@Test
	public void testMultiBean() {
		BeanInvoker invoker = super.getBean("beanInvoker");
		invoker.say();
	}
	
}
