--- 
title: SpringMVC构建Web应用
tags: JDK8,Eclipse,Tomcat7
---

[TOC]

---
# 简介
MVC:模型层(model),视图层(view),控制器(controller),是一种成熟的Web分层架构,将业务数据的抽取和数据的呈现相分离.
SpringMVC是Spring推出的一系列MVC架构的实现.

| 组成                  | 说明                                  |
| --------------------- | ------------------------------------- |
| DispatcherServlet     | 前端控制器                            |
| Controller            | 控制器                                |
| HandlerAdapter        | 适配器,将控制器适配成为各种适用的控制 |
| HandlerInterceptor    | 拦截器,在控制器调用前后进行操作       |
| HandlerMapping        | 对于不同的请求提供不同的控制器        |
| HandlerExecutionChain | 执行链条                              |
| ModelAndView          | Model或者Map,实现视图                 |
| ViewResolver          | 根据配置,选择视图呈现的方式           |
| View                  | 呈现页面                              |


## 包结构
- `java`
	- `format`                                       数据格式转化器
		- `ConverterTest`
		- `FormatTest`
		- `PropertyEditorTest`
	- `mvcdemo`                                  MVC模式搭建
		- `bean`                                     数据库映射对象
			- `Course`
			- `Teacher`
			- `User`  
		- `common`                               全局数据格式设置
			- `DateConverter`
			- `DateFormatter`
		- `controller`                                控制器
			- `CourseController`
			- `DataBing`
			- `HelloMVCController`          HelloWorld演示
			- `LoginController`
		- `entity`                                       数据模型
			- `UserListForm`
			- `UserMapForm`
			- `UserSetForm`
		- `Interceptor`                                拦截器
			- `AllInterceptor`
			- `LoginInterceptor`
			- `OtherInterceptor`
		- `service`                                  服务层接口
			- `CourseService`
			- `imp`                                   服务层实现类,这里并没有设计Dao层与数据库真正交互
				- `CourseServiceImp`
	- `springData`                               使用SpringData交互数据库
		- `bean`                                         数据库映射对象
			- `Student`
		- `dao`                                          数据库持久化层
			- `TemplateJDBC`
			- `TraditionalJDBC`
- `resources`                                      存放配置文件
- `webapp`                                         Web页面,资源文件存放
	- `temp`                                         上传文件暂存
	- `resource`                                   静态资源
	- `WEB-INF`                                  JSP页面
			- `AddCourse`
			- `Course`
			- `Data`
			- `Error`
			- `HelloWorld`                       HelloWorld显示页面
			- `Login`
			- `Success`
			- `Upload`
		- `spring`             Spring配置文件
		- `web.xml`          站点配置文件
	- `index.jsp`             主页
# 编码实现

## 使用Maven构建环境

1. 创建基于Maven的Web项目
2. 修改容器,或者添加Servlet,EL支持
3. 更改编码格式和JDK编译版本
4. 引入各种依赖Jar
5. 修改Web.xml
6. 创建各种类库

**POM文件**

具体的POM文件间的依赖可以参考[mvnrepository官网][1]
- 这是使用到的Spring版本为4.1.1正式版
	- `spring-core` 核心包
	- `spring-web` Web容器包
	- `spring-webmvc` MVC结构实现
	- `spring-jdbc`  JDBC模板
	- `spring-context` Spring上下文对象
- Web组件的支持
	- `javax.servlet-api` Servlet支持
	- `jstl` 标签库
	- `commons-fileupload` 上传下载组件
	- `jackson-databind` JSON数据格式的绑定
- 日志输出
	- `slf4j-api` slf4j日志实现
- 单元测试工具
	- `Junit`  进行单元测试
- 数据库
	- `mysql-connector-java`  MySQL数据库驱动
- 运行插件
	- `jetty-maven-plugin` 容器插件Jetty


``` xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>SpringMVC</groupId>
  <artifactId>SpringMVC</artifactId>
  <packaging>war</packaging>
  <version>0.0.1-SNAPSHOT</version>
  <name>SpringMVC Maven Webapp</name>
  <url>http://maven.apache.org</url>
 
  <!-- 变量实现 --> 
  <properties>
      <spring.version>4.1.1.RELEASE</spring.version>
  </properties>
  
  <!-- 依赖管理 -->
  <dependencies>
 	<!-- 单元测试 --> 
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>3.8.1</version>
      <scope>test</scope>
    </dependency>
    
    <!-- Spring核心 -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-core</artifactId>
        <version>${spring.version}</version>
    </dependency>
    
    <!-- Spring的web支持 -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-web</artifactId>
        <version>${spring.version}</version>
    </dependency>
    
    <!-- Spring的MVC支持 -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>${spring.version}</version>
    </dependency>
    
    <!-- Servlet的支持 -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>3.1.0</version>
    </dependency>

	<!-- 对JSTL的支持 -->
    <dependency>  
        <groupId>javax.servlet</groupId>  
        <artifactId>jstl</artifactId>  
        <version>1.2</version>  
        <scope>runtime</scope>  
    </dependency>  
    
    <!-- 对上传下载的支持 -->
    <dependency>
	    <groupId>commons-fileupload</groupId>
	    <artifactId>commons-fileupload</artifactId>
	    <version>1.3.1</version>
	</dependency>

    <!-- 日志记录 -->
	<dependency>
	    <groupId>org.slf4j</groupId>
	    <artifactId>slf4j-api</artifactId>
	    <version>1.7.21</version>
	</dependency>
	    
	<!-- 对JSON的支持 -->
	<dependency>
	    <groupId>com.fasterxml.jackson.core</groupId>
	    <artifactId>jackson-databind</artifactId>
	    <version>2.8.5</version>
	</dependency>
		    
	<!-- 对MySQL数据库的支持 -->
	<dependency>
		<groupId>mysql</groupId>
		<artifactId>mysql-connector-java</artifactId>
		<version>5.1.34</version>
	</dependency>
	
	<!-- Spring模板对JDBC的支持 -->
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-jdbc</artifactId>
		<version>${spring.version}</version>
	</dependency>
		
	<!-- Spring模板对上下文对象的支持 -->
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-context</artifactId>
		<version>${spring.version}</version>
	</dependency>
		    
  </dependencies>
  
  <!-- 插件测试 -->
  <build>
    <finalName>HelloSpringMVC</finalName>
    <plugins>
      <plugin>
      	<groupId>org.eclipse.jetty</groupId>
      	<artifactId>jetty-maven-plugin</artifactId>
      	<version>9.2.2.v20140723</version>
      </plugin>
    </plugins>
  </build>
</project>
```

## SpringMVC框架的搭建
### `Web.xml`中配置
1. 配置字符过滤器,避免字符乱码
使用`org.springframework.web.filter.CharacterEncodingFilter.Class`过滤请求转为UTF-8格式
2. 配置SpringMVC的前端控制器
设定其控制器的文件路径为`/WEB-INF/spring/mvc-dispatcher-servlet.xml`,并指定为第一启动顺序

``` xml
<web-app>
  <display-name>Archetype Created Web Application</display-name>
  
  <!-- 字符过滤器 -->
  <filter>
    <filter-name>CharacterEncodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
	  <param-name>encoding</param-name>
	  <param-value>UTF-8</param-value>
	</init-param>
  </filter>
  <filter-mapping>
  	<filter-name>CharacterEncodingFilter</filter-name>
  	<url-pattern>/*</url-pattern>
  </filter-mapping>
  
  <servlet>
 	<servlet-name>SpringMVC</servlet-name>
 	<!-- DispatcherServlet的对应的上下文配置,默认为/WEB-INF/$servlet-name$-servlet.xml -->
 	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class> 
 	<init-param>
 	  <!-- 改变默认配置文件的位置 -->
 	  <param-name>contextConfigLocation</param-name>
 	  <param-value>/WEB-INF/spring/mvc-dispatcher-servlet.xml</param-value>
 	</init-param>
 	<load-on-startup>1</load-on-startup>
  </servlet>
  
  <!-- 拦截所有请求 -->
  <servlet-mapping>
  	<servlet-name>SpringMVC</servlet-name>
  	<url-pattern>/</url-pattern>
  </servlet-mapping>
</web-app>
```

### `mvc-dispatcher-servlet.xml`配置Spring容器
1. 开启注解配置,并扫描`mvcdemo`包下的所有的`@Controller`注解
2. 开启扩展注解,将参数传入Spring的前端控制器,并指定消息的字符格式
3. 设置静态资源的映射目录
4. 将JSTL视图作为默认显示,并设置视图层返回的前缀后缀,与类返回值一同构成路径
5. 开启上传下载,并设定文件大小,属性
6. 开启JSON文件格式,通过JSON格式前后台传参
``` xml
<beans>
	<!-- 配置文件,对SpringMVC进行配置 -->

	<!-- 支持注解形式的配置 -->
	<context:annotation-config/>
	
	<!-- DispatchServlet上下文,只搜索@Controller标注的类,不收索其他标注的类 -->
	<context:component-scan base-package="mvcdemo">
		<!-- 包含此注解 -->
		<context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
	</context:component-scan>
	
	<!-- HandlerMapping 无需配置,SpringMVC可以默认启动 -->
	
	
	<!-- 扩展注解,可以将参数传入控制器,同时指定消息的字符格式 -->
	<mvc:annotation-driven conversion-service="dateConverter">
		<!-- 消息转换器 -->
		<mvc:message-converters register-defaults="true">
			<bean class="org.springframework.http.converter.StringHttpMessageConverter">
				<property name="supportedMediaTypes" value="text/html;charset=UTF-8"/>
			</bean>
		</mvc:message-converters>
	</mvc:annotation-driven>	
	
	<!-- 静态资源获得,对url中含有resources的进行映射到本地目录下 -->
	<mvc:resources location="/resources/**" mapping="/resources"/>
	
	<!-- 对返回映射路径进行修改,获取视图
		可以有多个,并进行order排序
		InternalResourceViewResolver放在最后
	 -->
	<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
		<!-- 注意前缀格式,有没有/ -->
		<property name="prefix" value="/WEB-INF/jsps/"/>
		<!-- 注意后缀格式,有没有. -->
		<property name="suffix" value=".jsp"/>
	</bean>
	
	<!-- 文件上传下载的Bean -->
	<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
		<property name="maxUploadSize" value="1024000"/>
		<property name="defaultEncoding" value="UTF-8"/>
		<!-- 延迟文件解析,避免文件异常 -->
		<property name="resolveLazily" value="true"/>
	</bean>
	
	<!-- 对JSON格式的支持 -->
	<bean class="org.springframework.web.servlet.view.ContentNegotiatingViewResolver">
		<!-- 加载等级 -->
		<property name="order" value="1"/>
		<!-- 忽略请求头 -->
		<property name="ignoreAcceptHeader" value="true"/>
		<!-- 媒体格式 -->
		<property name="mediaTypes">
			<map>
				<entry key="json" value="application/json"/>
				<entry key="xml" value="application/xml"/>
				<entry key="html" value="text/html"/>
			</map> 
		</property>
		<!-- 默认视图 -->
		<property name="defaultViews">
			<list>
				<bean class="org.springframework.web.servlet.view.json.MappingJackson2JsonView"/>
			</list>
		</property>
	</bean>	
</beans>	
```

### HelloWorld

**前端控制器层编写**
`@Controller`注解在Controller类上,标识该类为前端控制器,负责分发来自客户端的请求.
`@RequestMapping`既可以在类上注解,标识该类映射根路径下的该模块,也可以在方法上注解,结合前者,共同构成处理请求的方法.

访问:`http://localhost:8080/HelloSpringMVC/hello/mvc`
``` java
@Controller
@RequestMapping("/hello")
public class HelloMVCController {

	@RequestMapping("/mvc")
	public String helloMVC() {
		System.out.println("进入控制器");
		return "HelloWorld";
	}
}
```

**对应JSP页面**

``` html
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hello World</title>
</head>
<body>
	<h1>Hello world,这是一个模型</h1>
</body>
</html>
```

### 案例:课程管理模块

**前端控制器实现**
- 使用`org.slf4j.LoggerFactory`作为日志输出类
- 私有`CourseService`接口,自总注入实现类
- `@RequestMapping`注解映射请求路径,并指定请求的方式
- `@RequestParam`注解标注方法中的传入参数,映射从URL中获得的参数
- `Model`类传入,作为容器放置业务数据,进行前后台数据交换
- `Map<String, Object>`类也可以作为容器传入方法汇总,进行前后台数据交换
- `HttpServletRequest`类也可以作为方法参数,实现对Servlet底层的访问
- 每一个方法都会返回一个字符串,结合配置文件中的前后缀,共同构成视图文件的位置
- `@ModelAttribute`标注方法中传入的参数对象,自动从前台传入的`name`属性映射为类对象属性
- `return "redirect:viewTwo"`和`return "forward:viewTwo"`分别对应重定向和转发请求
- `@RequestParam(value="file") MultipartFile file`通过映射文件对象到前台传入的二进制流,完成文件的上传工作
- 可以使用`{}`占位参数,构成REST风格的URL,使用`@PathVariable`标注传入参数,进而获得参数值.
``` java
@Controller
@RequestMapping("/course")
public class CourseController {

	//日志记录
	private static Logger log = LoggerFactory.getLogger(CourseController.class);
	
	//服务类
	@Autowired
	private CourseService courseService;

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}
	
	/**	本方法对URL:http://localhost:8080/SpringMVC/course/viewOne?id=1
	 * 	只用GET请求
	 * 	使用Model的形式传递参数*/
	@RequestMapping(value="/viewOne",method=RequestMethod.GET)
	public String viewCourseOne(@RequestParam("id") int id,Model model) {
		System.out.println("收到参数"+id);
		Course course = courseService.getCourseById(id);
		model.addAttribute(course);	//属性名称与设置时一致
		return "Course";
	}
	
	/**	本方法对URL:http://localhost:8080/SpringMVC/course/viewTwo/253
	 * 	只用GET请求
	 * 	使用Map的形式传递参数*/
	@RequestMapping(value="/viewTwo/{id}",method=RequestMethod.GET)
	public String viewCourseTwo(@PathVariable("id") int id,Map<String, Object> model) {
		System.out.println("收到参数"+id);
		Course course = courseService.getCourseById(id);
		model.put("course",course);
		return "Course";
	}
	
	/**	本方法对URL:http://localhost:8080/SpringMVC/course/viewThree?id=266
	 * 	只用GET请求
	 * 	使用Request的形式传递参数*/
	@RequestMapping(value="/viewThree",method=RequestMethod.GET)
	public String viewCourseThree(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println("收到参数"+id);
		Course course = courseService.getCourseById(id);
		request.setAttribute("course", course);
		return "Course";
	}
	
	/**跳转到添加页面,add为参数,可以不写	URL:http://localhost:8080/SpringMVC/course/admin?add
	 * */
	@RequestMapping(value="/admin",method=RequestMethod.GET,params="add")
	public String createCourse() {
		
		//转发
		return "AddCourse";
		
	}
	
	/**进行保存的页面*/
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String saveCourse(@ModelAttribute Course course ) {
		
		System.out.println("从表单获取对象"+course.toString());
		//进行保存....
		course.setId(123);
		//重定向中,注意不需要再写类上的映射
		return "redirect:viewTwo/"+course.getId();
		//转发请求
//		return "forward:viewTwo/"+course.getId();
	}
	
	/**跳转进入文件上传下载**/
	@RequestMapping(value="/showUpload",method=RequestMethod.GET)
	public String showUploadPage() {
		return "Upload";
	}
	
	/**	进行文件上传下载 URL:http://localhost:8080/SpringMVC/course/showUpload
	 * 	使用Spring提供的类
	 *  **/
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public String UploadPage(@RequestParam(value="file") MultipartFile file) throws IOException {
		String path = "F:\\JAVA_WorkSpace\\SpringMVC\\src\\main\\webapp\\temp\\"
						+System.currentTimeMillis()+file.getOriginalFilename();
		if( !file.isEmpty()){
			System.out.println("获取并处理文件"+file.getOriginalFilename());
		}
		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
		return "Success";
	}
	
	/**对JSON格式的进行支持
	 * URL:http://localhost:8080/SpringMVC/course/jsonOne/2
	 * **/
	@RequestMapping(value="/jsonOne/{id}",method=RequestMethod.GET)
	public @ResponseBody Course getCourseByJsonOne(@PathVariable int id){
		return courseService.getCourseById(id);
	}
	
	/**	对JSON格式进行支持
	 * 	URL:http://localhost:8080/SpringMVC/course/jsonTwo/2
	 * **/
	@RequestMapping(value="/jsonTwo/{id}")
	public ResponseEntity<Course> getCourseByJsonTwo(@PathVariable int id) {
		Course course = courseService.getCourseById(id);
		//返回一个泛型实体类,以及Http的状态
		return new ResponseEntity<Course>(course,HttpStatus.OK);
	}
}
```

**服务层实现**
- 接口类

``` java
public interface CourseService {

	public Course getCourseById(int id);
}
```

- 实现类

``` java
@Service("courseService")	//使用注解创建Bean,并指定名称;缺省则自动创建
public class CourseServiceImp implements CourseService {
	/**根据ID查询课程*/
	public Course getCourseById(int id) {
		Course course = new Course();
		course.setId(1);
		course.setName("课程一");
		return course;
	}
}
```

  [1]: http://mvnrepository.com/