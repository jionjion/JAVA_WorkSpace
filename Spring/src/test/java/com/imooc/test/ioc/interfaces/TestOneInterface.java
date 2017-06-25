package com.imooc.test.ioc.interfaces;
/**
 * 	Spring的IOC的控制反转的单元测试类
 */
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



import com.imooc.ioc.interfaces.OneInterface;
import com.imooc.test.base.UnitTestBase;

@RunWith(BlockJUnit4ClassRunner.class)
//继承自基础类,传入配置文件的路径,Spring容器加载配置文件,并返回对应的bean对象
public class TestOneInterface extends UnitTestBase {

	public TestOneInterface() {
		super("classpath*:spring-ioc.xml");
	}
	
	/**通过传入配置文件中bean的名字完成IOC控制反转*/
	@Test
	public void testSay() {
		OneInterface oneInterface = super.getBean("oneInterface");
		oneInterface.say("This is a test.");
	}

	/**通过传入类型的类类型,完成IOC控制反转*/
	@Test
	public void testSay2() {
		
		OneInterface oneInterface = super.getBean(OneInterface.class);
		oneInterface.say("This is a test.");
	}

}
