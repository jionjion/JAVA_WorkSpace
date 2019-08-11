package top.jionjion.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *  @author Jion
 *      druid 数据库配置
 */
@Configuration
@PropertySource(value = "classpath:resources/druid.properties", encoding = "UTF-8")
public class DruidConfig {

    /** 配置数据源 */
    @ConfigurationProperties(prefix = "druid")
    @Bean
    public DataSource druid(){
        return new DruidDataSource();
    }

    /* 配置Druid监控 */

    /** 配置一个后台的Servlet */
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");

        Map<String,String> parameters = new HashMap<>();
        parameters.put("loginUsername","admin");
        parameters.put("loginPassword","123456");
        // 允许访问
        parameters.put("allow","localhost");
        // 禁止访问
        parameters.put("deny","192.168.*.*");

        registrationBean.setInitParameters(parameters);
        return registrationBean;
    }

    /** 配置一个监控filter */
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean<WebStatFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.setUrlPatterns(Collections.singletonList("/*"));

        Map<String,String> parameters = new HashMap<>();
        // 排除拦截
        parameters.put("exclusions","*.js,*.css,/druid/*");
        filterRegistrationBean.setInitParameters(parameters);

        return filterRegistrationBean;
    }



}
