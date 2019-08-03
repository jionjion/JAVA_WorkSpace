package top.jionjion.demo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.jionjion.demo.servlet.DemoFilter;
import top.jionjion.demo.servlet.DemoListener;
import top.jionjion.demo.servlet.DemoServlet;

import java.util.Arrays;

/**
 *  @author Jion
 *      自定义配置内置server容器
 */
@Configuration
public class DemoServerConfig {

    /** 注册一个自定义的Servlet */
    @Bean
    public ServletRegistrationBean<DemoServlet> demoServlet(){

        ServletRegistrationBean<DemoServlet> registrationBean = new ServletRegistrationBean<>();
        registrationBean.setServlet(new DemoServlet());
        registrationBean.addUrlMappings("/demoServlet");

        return registrationBean;
    }

    /** 注册一个自定义的Filter */
    @Bean
    public FilterRegistrationBean<DemoFilter> demoFilter(){
        FilterRegistrationBean<DemoFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new DemoFilter());
        // 拦截请求
        registrationBean.setUrlPatterns(Arrays.asList("/demoServlet"));
        return registrationBean;
    }

    /** 注册一个自定义的Lister */
    @Bean
    public ServletListenerRegistrationBean<DemoListener> demoListener(){
        ServletListenerRegistrationBean<DemoListener> registrationBean = new ServletListenerRegistrationBean<>();
        registrationBean.setListener(new DemoListener());
        return registrationBean;
    }
}