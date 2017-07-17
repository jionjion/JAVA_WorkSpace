package springJMS.consumer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**	监听者的类.启动监听服务*/
public class AppConsumer {

	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
		
	}
}
