package top.jionjion.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import top.jionjion.demo.handler.LoginHandlerInterceptor;

/**
 *  @author Jion
 *      自定义扩展SpringMVC
 *      指定配置映射路径
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
        // 主页面
        registry.addViewController("/dashboard").setViewName("dashboard");
    }

    /** 注册拦截器 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录拦截器配置,配置映射路径和排除路径
        registry.addInterceptor(new LoginHandlerInterceptor())
                .addPathPatterns("/**")
                // 排除登录页面,登录请求页面,跳转登录页面
                .excludePathPatterns("/login.html","/user/login","/login");
    }
}
