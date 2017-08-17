package conform;


import java.util.Date;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * 	Spring��ܵĲ����ļ�*/
public class SpringTest {

	private static ApplicationContext applicationContext = null;
	
	/**�������ʱ,��ȡ�����ļ�,��ɼ���*/
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
	}


	
	/**����Spring����Ƿ����,�ܷ���л�ȡһ��bean*/
	@Test
	public void test() {
 		Date date = (Date) applicationContext.getBean("date");		//���Application.xml�ļ���������Date��
		System.out.println(date);
	}
}