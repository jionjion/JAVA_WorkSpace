package springJMS.producer;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * 	创建实现类,完成消息机制*/
public class ProducerServiceImpl implements ProducerService{

	
	/**在配置文件中声明消息提供者*/
	@Autowired
	private JmsTemplate jmsTemplate;
	
	/**引入消息的配置目的地,防止有多个目的地*/
//	@Resource(name="queueDestination")	//队列模式的发布节点	
	@Resource(name="topicDestination")	//主题模式的发布节点
	private Destination destination;
	
	/**使用JmsTemplate发送消息*/
	public void sendMessage(final String message) {
		jmsTemplate.send(destination, new MessageCreator() {
			
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(message);
				return textMessage;
			}
		});
		System.out.println("---------发送消息:"+message+"----------");
	}
	
}
