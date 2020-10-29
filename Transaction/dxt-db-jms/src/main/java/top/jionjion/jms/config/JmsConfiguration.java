package top.jionjion.jms.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.TransactionAwareConnectionFactoryProxy;
import org.springframework.jms.core.JmsMessageOperations;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

/**
 * @author Jion
 */
@Configuration
public class JmsConfiguration {


    /** JMS连接工厂 */
    @Bean
    public ConnectionFactory connectionFactory(){
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        TransactionAwareConnectionFactoryProxy proxy = new TransactionAwareConnectionFactoryProxy();
        proxy.setTargetConnectionFactory(connectionFactory);
        proxy.setSynchedLocalTransactionAllowed(true);
        return proxy;
    }

    /** 重新配置模板工厂,启用事务 */
    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        // 由事务控制Session连接
        jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;
    }
}