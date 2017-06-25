package com.imooc.test.aop;
/**对抛出异常的次数进行限制,并打印输出**/
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.imooc.aop.schema.advisors.service.InvokeService;
import com.imooc.test.base.UnitTestBase;

@RunWith(BlockJUnit4ClassRunner.class)
public class TestAOPSchemaAdvisors extends UnitTestBase {
	
	public TestAOPSchemaAdvisors() {
		super("classpath:spring-aop-schema-advisors.xml");
	}
	
	@Test
	public void testSave() {
		InvokeService service = super.getBean("invokeService");
		service.invoke();		//正确执行
		
		System.out.println();	//错误执行
		service.invokeException();
 	}

}
