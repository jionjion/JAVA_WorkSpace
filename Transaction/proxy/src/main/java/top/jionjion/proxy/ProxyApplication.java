package top.jionjion.proxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 网关
 *      http://127.0.0.1:8888/
 *
 *  监控
 *      http://127.0.0.1:8888/hystrix
 * @author Jion
 */
@EnableZuulProxy
@EnableEurekaClient
@SpringBootApplication
@EnableHystrixDashboard
public class ProxyApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProxyApplication.class, args);
    }

}
