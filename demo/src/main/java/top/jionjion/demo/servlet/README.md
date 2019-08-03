
## 自定义容器
默认为内嵌的Servlet容器


## 注入Servlet


## 注入Filter

## 注入Listen


## 切换其他容器
Tomcat      默认
Jetty       长连接
Undertow    异步并发


1.首先排除默认的`spring-boot-starter-tomcat`依赖
2.引入其他的容器starter
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jetty</artifactId>
</dependency>
```

## 原理

## 使用外部的Tomcat容器
1.修改Pom文件为War包启动,修改tomcat依赖的作用环境为provided
2.修改项目结构,增加web.xml文件,资源文件目录
3.编写一个SpringBootServletInitializer的子类 ,配置应用属性的视图解析的前后缀
4.打成War包启动.