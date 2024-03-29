package jms.topic;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 	创建消息主题,生产100条消息记录
 * 	URL:http://127.0.0.1:8161/admin/queues.jsp
 * 	验证:	在消费者监听发布后,发送消息被每一个消费者监听
 * */
public class AppProducer {

	private static final String URI = "tcp://127.0.0.1:61616";
	private static final String topicName = "topic-demo";
	
	public static void main(String[] args) throws Exception {
		//1.创建connectionFactory
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URI);
		//2.创建connection
		Connection connection = connectionFactory.createConnection();
		//3.启动连接
		connection.start();
		//4.创建会话		不使用事务,同时消息为自动应答
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.创建一个目标
		Destination destination = session.createTopic(topicName);
		//6.创建生产者
		MessageProducer messageProducer = session.createProducer(destination);
		//循环
		for (int i = 0; i < 100	; i++) {
			//7.创建消息体
			TextMessage textMessage = session.createTextMessage("文本消息"+i);
			//8.发送消息
			messageProducer.send(textMessage);
			System.out.println("-----发送消息"+textMessage.getText()+"--------");
		}
		//9.关闭连接
		connection.close();
	}
}
