package springJMS.producer;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**	启动生产者*/
public class AppProducer {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("producer.xml");
		ProducerService service =context.getBean(ProducerService.class);
		for(int i=0 ; i<100 ; i++){
			service.sendMessage("消息内容:"+i);
		}
		context.close();	//关闭连接
	}
}
