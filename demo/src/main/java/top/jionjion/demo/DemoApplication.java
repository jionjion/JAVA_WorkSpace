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

