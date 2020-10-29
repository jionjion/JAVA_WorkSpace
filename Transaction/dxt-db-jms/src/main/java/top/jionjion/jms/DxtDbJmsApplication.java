package top.jionjion.jms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *  数据库和消息中间件的使用保证事务一致
 *
 *  ActiveMQ页面
 *      http://127.0.0.1:8161/index.html  admin / admin
 *  
 */
@SpringBootApplication
public class DxtDbJmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(DxtDbJmsApplication.class, args);
    }

}
