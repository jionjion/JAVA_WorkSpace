package conform;


import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 	Spring框架的测试文件*/
public class SpringTest {

	private static ApplicationContext applicationContext = null;
	
	/**在类加载时,读取配置文件,完成加载*/
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
	}


	
	/**测试Spring框架是否搭建完成,能否从中获取一个bean*/
	@Test
	public void test() {
 		Date date = (Date) applicationContext.getBean("date");		//获得Application.xml文件中声明的Date类
		System.out.println(date);
	}
}
