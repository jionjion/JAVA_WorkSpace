package beanannotation;
/**测试使用Bean的注解*/
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import base.UnitTestBase;


@RunWith(BlockJUnit4ClassRunner.class)
public class TestBeanAnnotation extends UnitTestBase {
	
	public TestBeanAnnotation() {
		super("classpath*:spring-beanannotation.xml");
	}
	
	/**通过在注解创建bean时指定名称*/
	@Test
	public void testSay() {
		BeanAnnotation bean = super.getBean("beanAnnotation");
		bean.say("这是测试.");
		
		//在创建bean的时候,在注解中指定bean的名字
//		BeanAnnotation bean = super.getBean("bean");
//		bean.say("这是测试.");
	}
	
	/**测试bean的作用范围*/
	@Test
	public void testScpoe() {			//bean的默认名称为首字母小写的类名
		BeanAnnotation bean = super.getBean("beanAnnotation");
		bean.myHashCode();
		
		bean = super.getBean("beanAnnotation");
		bean.myHashCode();
	}
	
}
