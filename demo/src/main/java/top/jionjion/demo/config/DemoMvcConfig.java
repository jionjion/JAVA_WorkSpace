package top.jionjion.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *  @author Jion
 *      自定义扩展SpringMVC
 */
@Configuration
public class DemoMvcConfig implements WebMvcConfigurer {

    /** 重写视图映射 */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 针对于请求 /success.html 的URL,交由视图 success
        registry.addViewController("/successXX").setViewName("success");
        // 登录请求
        registry.addViewController("/login").setViewName("login");
    }
}
