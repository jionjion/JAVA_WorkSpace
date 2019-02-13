package com.jionjion.guider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
public class GuiderApplication extends SpringBootServletInitializer{

	
	/** 启动入口 */
	public static void main(String[] args) {
		SpringApplication.run(GuiderApplication.class, args);
	}
	
	/** 入口,继承重写 */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    	// Jar包
//        return builder.sources(this.getClass());
        // War
        return builder.sources(this.getClass());
    }
}
