package beanannotation;
/**对类的初始化注解和销毁注解**/
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import base.UnitTestBase;
import beanannotation.jsr.JsrServie;


@RunWith(BlockJUnit4ClassRunner.class)
public class TestJsr extends UnitTestBase {
	
	public TestJsr() {
		super("classpath:spring-beanannotation.xml");
	}
	
	@Test
	public void testSave() {
		JsrServie service = getBean("jsrServie");
		service.save();
	}
	
}
