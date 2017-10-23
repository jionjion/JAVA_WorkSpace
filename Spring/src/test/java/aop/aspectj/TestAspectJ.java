package aop.aspectj;
/***
 * 	对使用aspectJ的注解进行测试
 */
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import aop.aspectj.biz.MoocBiz;
import base.UnitTestBase;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestAspectJ extends UnitTestBase {
	
	public TestAspectJ() {
		super("classpath:spring-aop-aspectj.xml");
	}
	
	@Test
	public void test() {
		MoocBiz biz = getBean("moocBiz");
		biz.save("通过注解保存这个参数.");
	}
	
}
