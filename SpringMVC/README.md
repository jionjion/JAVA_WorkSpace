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
		- `FormatTest`                           浮点型与金融单位转换测试
		- `PropertyEditorTest`                自定义字符串转为对象
	- `mvcdemo`                                  MVC模式搭建
		- `bean`                                     数据库映射对象
			- `Course`                              课程信息类
			- `Teacher`                             教师信息类
			- `User`                                  用户信息类
		- `common`                               全局数据格式设置
			- `DateConverter`                  实现`Converter`接口的日期转换类
			- `DateFormatter`                  实现`Formatter`接口的日期转换类
		- `controller`                                控制器
			- `CourseController`               课程模块进行前端控制器的各种访问
			- `DataBing`                           数据绑定前端控制器
			- `HelloMVCController`          HelloWorld演示
			- `LoginController`                  登录验证模块进行拦截器的拦截
		- `entity`                                       数据模型
			- `UserListForm`                    List格式数据的容器
			- `UserMapForm`                   Map格式数据的容器
			- `UserSetForm`                    Set格式数据的容器
		- `Interceptor`                             拦截器
			- `AllInterceptor`                    实现`HandlerInterceptor`接口的拦截器
			- `LoginInterceptor`               实现`WebRequestInterceptor`接口的拦截器
			- `OtherInterceptor`              登录验证拦截器
		- `service`                                  服务层接口
			- `CourseService`                 课程模块的服务接口
			- `imp`                                   服务层实现类,这里并没有设计Dao层与数据库真正交互
				- `CourseServiceImp`       课程模块的服务接口实现类
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
	- `WEB-INF`                                  安全目录                       
		- JSP                                         存放页面
			- `AddCourse`                       增加课程页面
			- `Course`                             课程信息页面
			- `Data`                                 数据绑定模块的数据传入页面
			- `Error`                                 错误页面
			- `HelloWorld`                       HelloWorld显示页面
			- `Login`                                登录界面
			- `Success`                           登录成功界面
			- `Upload`                             上传页面
		- `spring`             Spring配置文件
		- `web.xml`          站点配置文件
	- `index.jsp`             主页

## 请求流程

![请求过程][1]

# 编码实现

## 使用Maven构建环境

1. 创建基于Maven的Web项目
2. 修改容器,或者添加Servlet,EL支持
3. 更改编码格式和JDK编译版本
4. 引入各种依赖Jar
5. 修改Web.xml
6. 创建各种类库

**POM文件**

具体的POM文件间的依赖可以参考[mvnrepository官网][2]
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

### 案例:登录拦截器
拦截器在用户与请求与前端控制器之间添加拦截,使用`preHandle()`过滤控制器前的执行,`postHandle()`和`afterCompletion()`过滤控制器后的执行,多个连接器间按照注册顺序,构成拦截器链.

![拦截器流程][3]

**拦截器和过滤器的区别**
过滤器`Filter`依赖Servlet容器,基于回调函数,过滤范围大
拦截器`Interceptor`依赖于容器框架,基于反射机制,只过滤请求


**拦截器类创建**

- 最常用的方式实现`HandlerInterceptor`接口
- 编写`preHandle()`,`postHandle()`,`afterCompletion()`分别在到达前端控制器前,控制执行后,返回响应前进行拦截.

``` java
public class AllInterceptor implements HandlerInterceptor {

	/**拦截器执行前方法
	 * true:继续执行
	 * false:停止执行
	 * Object:表示被拦截的对象*/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		request.setCharacterEncoding("UTF-8");	//编码处理
		response.setContentType("text/html;charset=UTF-8");	
		System.out.println("执行拦截器前的方法");
		System.out.println();
		return true;
	}

	/**拦截器执行方法
	 * ModelAndView:请求的视图,可以对其增加属性或者跳转改变*/
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView)
			throws Exception {
		System.out.println("执行拦截器时的方法");
//		modelAndView.setViewName("/Success");	//可以更改跳转页面
//		modelAndView.addObject("MSG", "从拦截器获得数据");//当跳转为视图时,可以添加,但是响应为返回字符串时,不能使用
	}
	
	/**拦截器通过的方法
	 * 多用于资源的销毁*/
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception)
			throws Exception {
		System.out.println("执行拦截器后的方法");
	}
}
```

- 通过实现`WebRequestInterceptor`创建拦截器
- `preHandle`,`postHandle`,`afterCompletion`分别在请求的前中后期执行.
- 区别去前者的实现方式,后者不能终止用户请求.

``` java
public class OtherInterceptor implements WebRequestInterceptor {

	/**拦截器执行前,不能终止请求*/
	@Override
	public void preHandle(WebRequest request) throws Exception {

		System.out.println("其他拦截器执行前");
	}

	/**拦截器执行时*/
	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception {

		System.out.println("其他拦截器执行时");
	}

	/**拦截器执行后*/
	@Override
	public void afterCompletion(WebRequest request, Exception ex) throws Exception {

		System.out.println("其他拦截器执行后");
	}
}
```

**登录拦截器创建**
通过实现`HandlerInterceptor`的方法,从Session中判断用户信息,进而确定是否登录

``` java
public class LoginInterceptor implements HandlerInterceptor {

	/**拦截器执行前方法
	 * true:继续执行
	 * false:停止执行
	 * Object:表示被拦截的对象*/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		System.out.println("登录拦截执行前方法");
		//登录验证
		if(request.getSession().getAttribute("username") == null){
			//没有登录,返回到登录页面
			request.getRequestDispatcher("/login/loginPage").forward(request, response);
			return false;
		}
		return true;
	}

	/**拦截器执行方法
	 * ModelAndView:请求的视图,可以对其增加属性或者跳转改变*/
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView)
			throws Exception {
		modelAndView.setViewName("/Success");	//可以更改跳转页面
		modelAndView.addObject("MSG", "从拦截器获得数据");
		System.out.println("登录拦截器执行时");
	}
	
	/**拦截器通过的方法
	 * 多用于资源的销毁*/
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception)
			throws Exception {
		System.out.println("登录拦截器执行后");
	}
}
```

**登录前端控制器**

``` java
@Controller
@RequestMapping("/login")
public class LoginController {

	/**	登录页面的展示
	 * 	URL:http://localhost:8080/SpringMVC/login/loginPage*/
	@RequestMapping(value="loginPage",method=RequestMethod.GET)
	public String LoginShow() {
		
		return "Login";
	}
	
	/**登录页面*/
	@RequestMapping(value="login")
	public String Login(@ModelAttribute User user)  {
		
		System.out.println("获取用户信息:"+user.toString());
		return "Success";
	}
}
```

**注册拦截器**

在前端控制器类中,使用`<mvc:interceptors>`对拦截器进行注册

- 不指定拦截范围的`<bean>`配置的拦截器默认在所有请求中过滤
- 可以通过在`<mvc:interceptor>`标签中配置各种拦截方式
- `<mvc:interceptor>`可以有多个,构成拦截器链,其执行顺序与注册顺序一致
``` xml
<beans>
	<!-- 注册拦截器 -->
	<mvc:interceptors>
		<!-- 全部网页拦截器的Bean -->
 		<bean class="mvcdemo.Interceptor.AllInterceptor"/>
		<!-- 其他的拦截器实现方法,无返回值 -->
		<bean class="mvcdemo.Interceptor.OtherInterceptor"/>
		<!-- 登录验证拦截器 -->	
		<mvc:interceptor>
			<mvc:mapping path="/login/login"/>
			<bean class="mvcdemo.Interceptor.LoginInterceptor"/>
		</mvc:interceptor>
	</mvc:interceptors>
</beans>	
```

### 类型转换
**浮点型数字与货币单位转换**

``` java
public class FormatTest {

	@Test
	public void testOne() throws ParseException {
		CurrencyFormatter currencyFormatter = new CurrencyFormatter();
		currencyFormatter.setFractionDigits(2); 		//小数点后保留2位
		currencyFormatter.setRoundingMode(RoundingMode.CEILING);	//四舍五入的模式.ceiling:四舍五入
		
		//断言测试,将货币字符串转为对象为大的数字类型,并进行四舍五入 
		Assert.assertEquals(new BigDecimal("123.13"), currencyFormatter.parse("$123.123" , Locale.US));
	}
	
	@Test
	public void testTwo() {
		CurrencyFormatter currencyFormatter = new CurrencyFormatter();
		currencyFormatter.setFractionDigits(2); 		//小数点后保留2位
		currencyFormatter.setRoundingMode(RoundingMode.CEILING);	//四舍五入的模式.ceiling:四舍五入
		
		//断言测试,将大的数字类型转为带美元符号的字符串
		Assert.assertEquals("$123.00", currencyFormatter.print(new BigDecimal("123"), Locale.US));
	}
}
```

**自定义转换规则,将字符串转为对象**
- 继承`PropertyEditorSupport`类,实现抽象方法
``` java
public class PropertyEditorTest extends PropertyEditorSupport{

	/*自定义的文字传输顺序,并将其转换为对象属性,最终返回一个对象*/
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
	
		User user = new User();
		String[] textArray = text.split(",");	//将传入的字符串依据逗号分隔
		user.setUsername(textArray[0]);
		user.setPassword(textArray[1]);			//属性绑定
		this.setValue(user);					//将值压入,用来返回调用
	}

	//测试
	@Test
	public void test() {
		PropertyEditorTest editor = new PropertyEditorTest();
		editor.setAsText("张三,123456");		//传入字符串,注意顺序
		User user = (User) editor.getValue();	//获得对象
		System.out.println("将字符串转为对象:"+user.toString());	//验证
	}
}
```

### 参数格式转化
**日期转换类**

- 实现`Formatter<Date>`接口,对其中的`print`方法和`parse`方法进行重写,表示对日期的返回格式解析格式进行指定.

``` java
public class DateFormatter implements Formatter<Date>{

	/**打印日期*/
	@Override
	public String print(Date date, Locale locale) {
		
		return null;
	}

	/**解析日期*/
	@Override
	public Date parse(String text, Locale locale) throws ParseException {
		return new SimpleDateFormat("YYYY-MM-DD").parse(text);
	}
}
```

- 继承`Converter`指定泛型,表示前后的格式变化

``` java
public  class DateConverter implements Converter<String, Date>{

	@Override
	public Date convert(String source) {
		try {
			return new SimpleDateFormat("YYYY-MM-DD").parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
```

**注册转换类**

- Spring自带的字符串转为布尔值
- 自定义的日期转换格式,方式一
- 自定义的日期转换格式,方式二


``` xml

 	
	<!-- 日期转换类,首先是类工厂 ,在mvc:annotation-driven conversion-service="dateFormatter" 中添加-->
	<bean id="dateFormatter" class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
		<!-- 自定义的日期转换格式 -->
		<property name="formatters">
			<set>
				<bean class="mvcdemo.common.DateFormatter"/>
			</set>
		</property>
	</bean>
	
	<!-- 日期转换类,首先是类工厂  ,在mvc:annotation-driven conversion-service="dateFormatter" 中添加-->
	<bean id="dateConverter" class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
		<!-- 自定义的日期转换格式 -->
		<property name="converters">
			<set>
				<bean class="mvcdemo.common.DateConverter"/>
			</set>
		</property>
	</bean>
```


### Spring数据绑定
`@ResponseBody`标注的类,会将字符串返回结果输出为前端显示.

`http://localhost:8080/SpringMVC/data/showView`访问
``` java
@Controller
@RequestMapping("/data")
public class DataBing {
	@RequestMapping("/showView")
	public String  showView() {
		return "Data";
	}
}
```

**基本数据类型绑定**

`@RequestParam`注解将要接收到的参数,可以不使用,但是如果标注,前台必须传入该参数
`http://localhost:8080/SpringMVC/data/baseTypeInt?param=20`访问
``` java
@Controller
@RequestMapping("/data")
public class DataBing {
	/**基本数据类型的绑定*/
	@RequestMapping("/baseTypeInt")
	@ResponseBody	//将返回字符串作为结果		@RequestParam:指定前台参数名称,必须传入
	public String baseTypeInt(@RequestParam(value="param",defaultValue="10") int param) {
		return "收到参数"+param;
	}
}
```

**包装类数据类型的绑定**
`http://localhost:8080/SpringMVC/data/packTypeInt`访问
``` java
@Controller
@RequestMapping("/data")
public class DataBing {
	/**包装类数据类型的绑定*/
	@RequestMapping("/packTypeInt")
	@ResponseBody	//将返回字符串作为结果		
	public String packTypeInt( Integer param) {
		return "收到参数"+param;	//包装类可以前台不同传递
	}
}
```

**数组类型的绑定**
`http://localhost:8080/SpringMVC/data/arrType?name=张三&name=李四&name=wangwu`访问
``` java
@Controller
@RequestMapping("/data")
public class DataBing {
	/**数组类型的绑定*/
	@RequestMapping("/arrType")
	@ResponseBody	//将返回字符串作为结果		@RequestParam:指定前台参数名称,必须传入
	public String arrType(@RequestParam(value="name") String[] arrs) {
		return Arrays.toString(arrs);
	}
}
```

**对象类型的绑定**
`@InitBinder`注解标注在方法中,表示对前缀为指定值的参数解析为对象属性
`http://localhost:8080/SpringMVC/data/objType?teacher.username=老师&user.username=张三&user.password=123456`访问
``` java
@Controller
@RequestMapping("/data")
public class DataBing {
	/**对象类型的绑定*/
	@RequestMapping("/objType")
	@ResponseBody	//将返回字符串作为结果
	public String objType(Teacher teacher , User user) {
		return teacher.toString() + user.toString();
	}
	/*针对不同对象的相同属性进行赋值*/
	@InitBinder("user")
	public void initUser(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("user.");
	}
	@InitBinder("teacher")
	public void initTeacher(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("teacher.");
	}
}
```

**List容器绑定**
- 创建容器对象,对实体类进行包装

``` java
public class UserListForm {

	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "UserListForm [users=" + users + "]";
	}
}
```

- 前端控制器参数类型指定为自定义的List容器对象
`http://localhost:8080/SpringMVC/data/listType?users[0].username=张三&users[0].password=123456&users[1].username=lisi&users[1].password=456789`访问

``` java
@Controller
@RequestMapping("/data")
public class DataBing {
	/**List容器绑定,绑定的对象必须为包装对象后*/
	@RequestMapping("/listType")
	@ResponseBody	//将返回字符串作为结果		
	public String listType(UserListForm userListForm) {
		return userListForm.toString();
	}
}
```

**Set容器绑定**
- 创建容器对象,对实体类进行包装

``` java
public class UserSetForm {

	private Set<User> users;

	private UserSetForm(){
		users = new LinkedHashSet<>();
		users.add(new User());			//初始化set
		users.add(new User());
	}
	
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "UserSetForm [users=" + users + "]";
	}
}
```

- 前端控制器参数类型指定为自定义的Set容器对象
`http://localhost:8080/SpringMVC/data/setType?users[0].username=张三&users[0].password=123456`访问

``` java
@Controller
@RequestMapping("/data")
public class DataBing {
	/**Set容器绑定,必须使用初始化后才可以使用,当重写过判断方法后,只能封装指定数量的不同对象*/
	@RequestMapping("/setType")
	@ResponseBody	//将返回字符串作为结果		
	public String setType(UserSetForm userSetForm) {
		return userSetForm.toString();
	}
}
```

**Map容器绑定**
- 创建容器对象,对实体类进行包装

``` java
public class UserMapForm {

	private Map<String, User> users;

	public Map<String, User> getUsers() {
		return users;
	}

	public void setUsers(Map<String, User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "UserMapForm [users=" + users + "]";
	}
}
```

- 前端控制器参数类型指定为自定义的Map容器对象
`http://localhost:8080/SpringMVC/data/mapType?users['user1'].username=张三&users['user1'].password=123456&users['user2'].username=lisi&users['user2'].password=456789`访问

``` java
@Controller
@RequestMapping("/data")
public class DataBing {
	/**Map容器绑定,绑定的对象必须为包装对象后,传入前必须指定Key*/
	@RequestMapping("/mapType")
	@ResponseBody	//将返回字符串作为结果		
	public String mapType(UserMapForm userMapForm) {
		return userMapForm.toString();
	}
}
```

**JSON对象绑定**
`@RequestBody`标注的对象参数,表示将接受一个JSON字符串格式的传入参数

``` java
@Controller
@RequestMapping("/data")
public class DataBing {
	/**JSON对象绑定*/
	@RequestMapping("/jsonType")
	@ResponseBody	//将返回字符串作为结果		@RequestBody:读取字符串对象,完成解析		
	public String jsonType(@RequestBody User user) {
		return user.toString();
	}
}
```

**字符串转为布尔值**

- 在`mvc-dispatcher-servlet.xml`中注册

``` xml
<beans>
	<!-- 将传入的字符串转为布尔值 -->
	<bean id="stringToBooleanConverter" class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
		<property name="converters">
			<set>
				<bean class="org.springframework.core.convert.support.StringToBooleanConverter"/>
			</set>
		</property>
	</bean>
</beans>	
```

- 前端控制器中配置
`http://localhost:8080/SpringMVC/data/booleanType?bool=true`访问

``` java
@Controller
@RequestMapping("/data")
public class DataBing {
	/**字符串转为布尔值.在Spring中配置转化器,可以将true,1,yes,on转为true*/
	@RequestMapping("/booleanType")
	@ResponseBody	//将返回字符串作为结果		@RequestBody:读取字符串对象,完成解析		
	public String booleanType(Boolean bool) {
		return bool.toString();
	}
}
```

**字符串绑定为日期**

`http://localhost:8080/SpringMVC/data/dateOneType?dateOne=2017-5-25`访问
``` java
@Controller
@RequestMapping("/data")
public class DataBing {
	/**字符串绑定为日期*/
	@RequestMapping("/dateOneType")
	@ResponseBody
	public String dateOneType(Date dateOne) {
		return dateOne.toString();
	}
	@InitBinder("dateOne")	//绑定日期格式
	public void initDateOne(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-mm-dd"), true));
	}
}
```




**资源获取形式**

``` java
@Controller
@RequestMapping("/data")
public class DataBing {
	/**资源获取形式,通过在请求头中修改Content-Type完成资源的表现形式*/
	@RequestMapping(value="/bookType",method=RequestMethod.GET)
	@ResponseBody
	public String bookType(HttpServletRequest request) {
		String contentType = request.getContentType();
		if(contentType == null){
			return "书的默认展现形式";
		}else if(contentType.equals("txt")){
			return "书的txt展现形式";
		}else if(contentType.equals("html")){
			return "书的html展现形式";
		}else{
			return "书的默认展现形式";
		}
	}
}
```

**RESTful形式URL**

`http://localhost:8080/SpringMVC/data/30/RESTfulType`访问
``` java
@Controller
@RequestMapping("/data")
public class DataBing {
	@RequestMapping("/{id}/RESTfulType")
	@ResponseBody
	public String RESTfulType(@PathVariable("id") String id) {
		return "收到参数:"+id;
	}
}	
```


  [1]: https://www.github.com/jionjion/Picture_Space/raw/master/WorkSpace/Java/SpringMVC/SpringMVC-01.jpg "SpringMVC-01"
  [2]: http://mvnrepository.com/
  [3]: https://www.github.com/jionjion/Picture_Space/raw/master/WorkSpace/Java/SpringMVC/SpringMVC-02.jpg "SpringMVC-02"