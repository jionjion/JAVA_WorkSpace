package conform;

import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HibernateTest{

	private static ApplicationContext applicationContext = null;
	
	/**�������ʱ,��ȡ�����ļ�,��ɼ���*/
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	@Test
	public void test() {
		//��ȡSessionFactory����
		SessionFactory sessionFactory = (SessionFactory) applicationContext.getBean("sessionFactory");
		//����鿴
		System.out.println(sessionFactory);
	}

}