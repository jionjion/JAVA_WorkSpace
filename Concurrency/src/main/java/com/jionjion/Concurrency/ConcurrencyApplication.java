package com.jionjion.concurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.jionjion.concurrency.filter.HttpFilter;
import com.jionjion.concurrency.filter.HttpInterceptor;

@SpringBootApplication
public class ConcurrencyApplication extends WebMvcConfigurationSupport{

	public static void main(String[] args) {
		SpringApplication.run(ConcurrencyApplication.class, args);
	}
	
	
	/**
	 * 	将自定义的Filter注册到Spring中
	 * 	FilterRegistrationBean类,注册过滤器的类
	 * 	返回类为FilterRegistrationBean,Bean的名字为httpFilter
	 */
	@Bean
	public FilterRegistrationBean<HttpFilter> httpFilter(){
		FilterRegistrationBean<HttpFilter> registrationBean = new FilterRegistrationBean<HttpFilter>();
		//加入filter
		registrationBean.setFilter(new HttpFilter());
		//设定URL匹配规则
		registrationBean.addUrlPatterns("/threadLocal/*");
		return registrationBean;
	}
	
	/**
	 * 	方法调用拦截器注册项目中
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new HttpInterceptor()).addPathPatterns("/threadLocal/**");
	}
}
