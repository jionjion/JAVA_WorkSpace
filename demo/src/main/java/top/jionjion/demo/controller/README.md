
## SpringMVC

### 官方静态资源映射

#### 通过webjars映射
默认配置在`org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration`类下
`addResourceHandlers`方法定义了两种关于资源映射的方式

其中配置类
所有`/webjars/**`路径请求,都会去`classpath:/META-INF/resource/webjars/`找资源;
即通过jar包的方式将静态资源引入
参阅[官网](https://www.webjars.org/)

访问路径`127.0.0.1:8080/webjars/jquery/3.3.1/jquery.js`
```xml
<dependency>
    <groupId>org.webjars</groupId>
    <artifactId>jquery</artifactId>
    <version>3.3.1</version>
</dependency>
```

#### 默认路径映射
其他访问`/**`的资源,会尝试去 根目录,`classpath:/META-INF/resources/`,`classpath:/resources/`,`classpath:/static/`,`classpath:/public/`目录下找

#### 首页映射
类`WebMvcAutoConfiguration`的`welcomePageHandlerMapping`方法,定义了关于首页的映射
即,映射`/**`到静态资源文件夹下`index.html`页面

#### 图标映射
类`WebMvcAutoConfiguration`的内部类`FaviconConfiguration`,配置了关于图标的一些信息
即,映射`**/favicon.ico`到静态资源文件下的`**/favicon.ico`图标


#### 自定义路径访问的映射
通过,`ResourceProperties`设置跟资源有关的参数.注意,会覆盖原有的配置信息
```java
@ConfigurationProperties(prefix = "spring.resources", ignoreUnknownFields = false)
public class ResourceProperties {
    
}
```

## SpringMVC扩展配置
默认SpringBoot进行了大量配置
在`org.springframework.boot.autoconfigure.web`路径下面

### 手动扩展
编写一个配置类 `@Configuration`, `WebMvcConfigurer`接口的扩展实现,不能标注`@EnableWebMvc`
实现原理:容器中所有的`WebMvcConfigurer`实现都会被调用,包括自定义的实现

### 全面接管MVC
添加`@EnableWebMvc`,禁用默认的SpringMVC的自动配置,完全为用户自定义扩展.
实现原理:`@EnableWebMvc`导入了`WebMvcConfigurationSupport`类,而该类存在于容器中时,`WebMvcAutoConfiguration`不会被自动注入


## 模板引擎
使用Thymeleaf.
默认配置为下,只需要将其放在类资源路径`/templates`目录下即可

```java
@ConfigurationProperties(prefix = "spring.thymeleaf")
public class ThymeleafProperties {

	private static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;

	public static final String DEFAULT_PREFIX = "classpath:/templates/";

	public static final String DEFAULT_SUFFIX = ".html";
}	
```

导入命名空间
`<html lang="en" xmlns:th="http://www.thymeleaf.org">`




