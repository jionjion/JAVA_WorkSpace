package springJMS.consumer;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**	消息监听者
 * 	实现MessageListener接口
 * 	
 * 	队列模式和主题模式的切换
 * 	1.在consumer.xml中配置消息监听容器的destination属性的监听位置
 * 	2.在ProducerServiceImpl.java在生产者服务中,指明@Resource的监听节点
 * */
public class ConsumerMessageListener implements MessageListener{

	/**监听接收消息*/
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		
		try {
			System.out.println("接收到消息:"+textMessage.getText());
		} catch (JMSException e) {
			e.printStackTrace();
			System.err.println("监听时发生意外........");
		}
	}
}
