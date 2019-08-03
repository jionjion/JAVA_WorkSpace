package top.jionjion.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Jion
 *  启动类
 */
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

/*

SpringBoot的自动配置原理
1.启动时,加载主配置类.开启@EnableAutoConfiguration

2.@EnableAutoConfiguration的作用
    1.利用AutoConfigurationImportSelector类给容器中导入一些组件.通过实现ImportSelector接口的selectImports()方法.
        其中,getAutoConfigurationEntry()方法中
            List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);
        调用
            SpringFactoriesLoader.loadFactoryNames()
        扫描类路径下所有Jar包的文件 META-INF/spring.factories
        将所有文件内容包装为Properties对象,并从中获得EnableAutoConfiguration的属性的值加入到容器中.
    2.加入容器的每一个类都是自动配置类,其中定义了各个场景启动器所需的各种类,进行自动配置

3.以HttpEncodingAutoConfiguration类为例.根据当前不同的条件进行判断,如果生效,则从当前类中,获得@Bean定义的各种Bean
    // 标示这是一个配置类
    @Configuration
    // 配置类,HttpProperties,将属性文件中的属性映射为类属性,并加入到IOC容器
    @EnableConfigurationProperties(HttpProperties.class)
    // 根据Spring的@Conditional注解,实现Condition接口的方法判断当前应用是否为Web应用,是则将类中定义的方法的返回类注入
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    // 判断当前项目是否有这个类
    @ConditionalOnClass(CharacterEncodingFilter.class)
    // 判断是否含有这个属性
    @ConditionalOnProperty(prefix = "spring.http.encoding", value = "enabled", matchIfMissing = true)

4.  XXXXAutoConfiguration:自动配置类,给容器增加相关组件;
    XXXXProperties:封装对应属性

5.ConditionalOnXXXX:配装的,动态注入Bean的注解.如果判断成功,则注入对应@Bean注解
*/

/*
自定义日志实现
 */

/*
Spring页面国际化步骤
1.编写国际化配置文件
2.使用ResourceBundleMessageSource管理国际化资源文件
3.在页面使用fmt:message取出国际化内容

SpringBoot页面国际化步骤
1.编写国际化配置文件,抽取需要显示的消息
2.在resource目录下,创建属性文件 XX.properties, XX_zh_CN.properties, XX_en_US.properties 等地区标识
注:Idea支持创建多个属性文件,并统一管理
3.SpringBoot通过MessageSourceAutoConfiguration类自动配置好自动管理属性文件.
属性文件的读取路径,通过设置spring.messages进行配置
4.页面中,通过#{}获得信息
5.重写默认的请求信息解析器LocaleResolver类,默认通过请求头信息中的Locale进行国际化

 */


/*
页面要点
    跳转|转发
    静态资源
    包含,引入模板片段
    引入片段间传递参数
    扩展form表单方法
    自定义属性
    错误页面,参考类 ErrorMvcAutoConfiguration
*/

/*
 From表单支持更多请求 put|delete
 1.SpringMVC中配置HiddenHttpMethodFilter过滤器 (SpringBoot默认配置成功)
 2.创建一个post表单
 3.在表单中,埋入_method属性,指定方式
 */

/*
自动错误页面的原理
参考配置类 ErrorMvcAutoConfiguration
默认添加以下Bean
    DefaultErrorAttributes     定了默认错误信息数据.将错误信息放在响应中
    BasicErrorController       处理默认的/error,分别定义了处理浏览器和客户端的请求响应
    ErrorPageCustomizer        定义错误的响应规则,默认错误请求路径 error.path:/error
    DefaultErrorViewResolver   错误页面的视图解析器

处理流程
    - 存在模板引擎,通过设置 /error/状态码 将不同状态码.html的错误页面放在模板资源目录/error目录下
    - 通过4xx和5xx页面,匹配多个页面
    - 不存在模板引擎,直接静态资源文件夹下
    - 以上均不存在时,返回默认显示页面
 */


/*
自定义异常通知页面
    - 通过@ControllerAdvice处理机制,进行处理

    - 重定向到 /error 处理器下
        - 重写 /error 处理拦截器
        - 扩展DefaultErrorAttributes类,通过继承,完善更多异常信息.返回到前台显示更多信息
 */

/*
修改自带的Servlet容器.默认为tomcat

通过修改server有关的属性
和server.tomcat相关的属性

 */

/*
注册Web的组件 Servlet, Filter, Listener
ServletRegistration                 注册Servlet容器
FilterRegistration                  注册Filter过滤器
ServletListenerRegistrationBean     注册Listener容器

*/
