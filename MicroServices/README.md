---
title: SpringBoot构建微服务
tags: JDK8,Eclipse,Tomcat8
---

# 简介
SpringBoot是Spring推出的一款快速搭建Web项目的框架,通过它可以很方便的搭建项目.该项目通过SpringBoot搭建框架,Maven作为Jar包控制,视图视图显示选用Freemaker作为模板,引入bootstrap作为前端框架.

## 文件结构
- `main`
	- `java\MicroServices`
		- `aspect`
			- `HttpAspect.java`
		- `bean`
			- `Logs.java`    
			- `User.java`    实现序列化接口的Bean
		- `collector`
			- `HelloFreeMarker.java`
			- `UserController.java`
		- `dao`
			- `LogsRepository.java`  
			- `UserRepository.java`      JPA规范,查询数据库
		- `domain`
			- `ResultMessage.java`       使用泛型类包装数据库查询结果,返回给前台
		- `enums`
			- `ResultEnum.java`            返回数据信息状态的枚举类
		- `exception`
			- `UserException.java`          自定义运行期异常,便于事务的回滚
		- `handle`
			- `ExceptionHandle.java`        同意异常处理类
		- `service`
			- `serviceImp`
				- `UserServiceImp.java`      用户模块业务实现类
			- `UserService.java`                用户模块业务接口
		- `tool`
			- `ResultUtil.java`                REST风格的返回结果包装类,定义成功和失败的返回格式
			- `SumListMethod.java`
		- `HelloController.java`
		- `MicroServicesApplication.java`    SpringBoot的入口类,开启Web容器
		- `ServletInitializer.java`
	- `resources`
		- `config`
			- `application.properties`  SpringBoot的配置文件,默认位置如此,包含SpringBoot框架支持的所有配置信息
		- `module`
			- `user`
				- `hello.html`
				- `user.html`
		- `static`
			- `css`
			- `fonts`
			- `img`
			- `js`
		- `templates`
		- `applicationContext.xml`
- `test`
	- `java\MicroServices`
		- `collector`
		- `service`
		- `MicroServicesApplicationTests.java`

# 框架快速搭建
**使用SpringIO创建项目**
通过在[SpringIO官网][1]创建SpringBoot启动项目,完成项目依赖POM文件的搭建.


**pom.xml文件**

- 项目采用

``` xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<!-- 项目基本信息描述 -->
	<groupId>MicroServices</groupId>
	<artifactId>MicroServices</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>war</packaging>

	<!-- 项目名称是 -->
	<name>MicroServices</name>
	<description>Micro Services building with springboot and freemarker</description>

	<!-- 继承父类框架 -->
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>1.5.4.RELEASE</version>
		<relativePath/> 
	</parent>

	<!-- 定义字符编码及编译版本 -->
	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
	</properties>

	<!-- 各种依赖 -->
	<dependencies>
		<!-- web包 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		
		<!-- 引入freeMarker的依赖包. -->
	    <dependency>   
	        <groupId>org.springframework.boot</groupId>  
	        <artifactId>spring-boot-starter-freemarker</artifactId>
	    </dependency>
	    
		<!-- 热部署的包 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
		 	<optional>true</optional>
			<scope>runtime</scope>
		</dependency>
		
		<!-- MySQL数据库 -->
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>
		
		<!-- 数据库jpa规范 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
			<scope>runtime</scope>
		</dependency>
		
		<!-- AOP面向切面编程 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-aop</artifactId>
			<scope>runtime</scope>
		</dependency>
		
		<!-- tomcat容器 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-tomcat</artifactId>
			<scope>provided</scope>
		</dependency>
		
		<!-- 测试包 -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<!-- 插件包,支持maven插件 -->
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
</project>
```


**HelloWorld**
SpringBoot默认将`Application.java`同级的文件装载进入Spring容器中,因此我们在同级目录下创建`HelloController.java`文件,并使用注解将其注解为前端控制器类.
`@RestController`等于`@Controller[类注解]` + `@ResponseBody[方法注解]`标识该注解为前端控制器类,并支持将方法返回的字符串直接输入到前端页面.
`@Controller`注解类上,标识该类为一个前端控制器类
`@ResponseBody`注解在方法上,将方法的返回字符串直接输出到前台
`@RequestMapping`既可以注解在方法,也可以注解类上,作为进入方法的URL路径
`@Value`从配置文件中获得对应的属性值,配置文件的路径默认为`src\main\resources\`,Spring会将其目录及子目录下的文件注解进入引入容器中.
`@PathVariable`标识RESTful风格的URL,从URL占位符中获得对应的参数,要求参数类型与URL中的保持一致.
`@RequestParam`从前台传入参数中获得对应的值.
``` java
@RestController		//等于@Controller[类注解] + @ResponseBody[方法注解]
@RequestMapping("/demo")	//类注解,增加命名空间
public class HelloController {

	@Value("${author}")				//从配置文件中获取自定义的常量
	private String author;
	
	/**	http://localhost:8080/MicroServices/demo/helloWorld	*/
	@RequestMapping(value="/helloWorld")			//默认使用get和post.每一个URL必须唯一
	public String say() {
		
		return "<h4>this is micro services , building with springboot and freemarker！！！! </h4><br>"
				+ "	author :"+author;
	}
	
	/**	http://localhost:8080/MicroServices/demo/hello/param11	*/
	@RequestMapping(value="/hello/{param}")						//参数名称要求一致
	public String getPathVariable(@PathVariable("param") String param) {//参数如果是数字,使用包装类.汉字则需要转码
		
		return "获得参数:"+param;
	}
	
	/**	http://localhost:8080/MicroServices/demo/hello?param=汉字	*/
	@GetMapping(value="/hello")				//使用get方式的注解,参数如果是数字,使用包装类.汉字则需要转码
	public String getRequestParam(@RequestParam(value="param",required=false,defaultValue="默认参数",name="param") String param) {	//传参和形参名称不需要一致
		
		return "获得参数:"+param;
	}
}
```




**启动项目**
`@SpringBootApplication`注解标注的类,在SpringBoot项目中作为入口类,通过运行该类,启动SpringBoot内置的Tomcat容器,完成整体的项目启动.
`@SpringBootApplication`启动配置类,通过`exclude`表示排除某些类,避免在数据源未配置的情况下启动时抛出异常.

``` java
//@SpringBootApplication
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})	//无数据库时运行
public class MicroServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroServicesApplication.class, args);
	}
}
```

# SpringBoot
这里从后台到前台,实现一个简单的增删改查,包括JPA规范的使用,依赖注入的配置,REST风格的JSON数据交换及接口,统一异常处理,泛型及枚举类的创建.

## JPA规范
`JPA`定义了一系列对数据库的统一操作,通过它可以实现对不同数据间的统一操作.
传入Bean的类型和主键类型,构建JPA查询对象,对象必须实现`Serializable`接口,类名一般使用Repository结尾.
通过对命名规范的方式,完成对条件参数的数据查找,这里使用命名规范的`find`查找,表示根据属性中的`name`查找符合的List序列.

``` java
public interface UserRepository extends JpaRepository<User, Integer> {

	/**根据名字查询*/
	public List<User> findByUsername(String usename);		//注意命名规范
}
```

**Bean的构建**
Bean必须实现`Serializable`序列化接口
`@Entity`标注在类中,表示这是一个数据库映射类.
`@Id`表示该属性为主键属性,配合`@GeneratedValue`表示其自增属性.
`@Length`表示对其长度进行限制,当不满足时抛出异常和`message`中的信息.
``` java
@Entity
public class User implements Serializable{
	@Id
	@GeneratedValue
	private Integer id;
	
	private String username;
	
	@Length(min=6,message="密码最短为6位!!")
	private String password;
	
	private Date brithday;

	public User() {
		super();
	}
}	
```



## JPA服务层
`@Service`标注的类将会被Spring容器托管,标注该类是一个服务类,对外提供业务逻辑调用,调用时可以通过在接口上使用`@Autowired`自动注入实现类
`@Autowired`注解属性,表示该类交由Spring依赖注入,由容器进行创建.

``` java
@Service
public class UserServiceImp implements UserService{

	@Autowired
	private UserRepository userRepository;
 	
	@Override
	public User findOne(Integer id) {
		return userRepository.findOne(id);
	}
}
```

## REST风格数据交互
使用泛型`<T>`接收数据类型,对数据库中的查询结果进行包装,向前台传出JSON数据结构
私有属性`private T date`作为泛型参数
`private Integer code`和`private String message`分别表示返回的状态码和验证消息.
以上属性提供get/set方法.

``` java
public class ResultMessage<T> {
	//代表消息状态
	private Integer code;
	//详细内容
	private String message;
	//存放数据
	private T date;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getDate() {
		return date;
	}

	public void setDate(T date) {
		this.date = date;
	}
```


工具类方法,对数据的请求结果进行包装返回

``` java
public class ResultUtil {

	/**成功返回结果*/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ResultMessage success(Object object) {
		ResultMessage resultMessage = new ResultMessage();
		resultMessage.setCode(200);
		resultMessage.setMessage("成功");
		resultMessage.setDate(object);
		return resultMessage;
	}
	
	/**成功且不返回结果*/
	@SuppressWarnings("rawtypes")
	public static ResultMessage success() {
		return success(null);
	}
	
	/**失败返回失败结果*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ResultMessage error(Integer code,String message) {
		ResultMessage resultMessage = new ResultMessage();
		resultMessage.setCode(code);
		resultMessage.setMessage(message);
		resultMessage.setDate(null);
		return resultMessage;
	}
}
```


## 返回信息枚举类
使用枚举类,对后台返回的数据进行包装.

**枚举类创建方式**
1. 创建枚举类的私有变量
2. 根据私有变量创建构造方法
3. 由于枚举类是常量,因此只需要提供`get`方法即可
4. 根据构造器,创建预先生成好的枚举,并放在类开始处,逗号分隔,分号结尾

``` java
public enum ResultEnum {
	ERROR_UNKNOWN(-1,"未知的错误"),
	ONE_ERROR(1,"第一种异常"),
	TWO_ERROR(2,"第二种异常"),
	OTHER_ERROR(0,"其他异常");									//第四步:根据构造器,预先创建各种生成好的枚举对象					
	private Integer code;									//第一步:枚举的成员
	
	private String message;
	
	private ResultEnum(Integer code, String message) {		//第二步:定义一个使用全部成员变量的构造方法
		this.code = code;
		this.message = message;
	}
	
	public Integer getCode() {								//第三步:定义get方法.
		return code;
	}

	public String getMessage() {
		return message;
	}
}
```

## 自定义异常
Spring框架通过`@Transactional`注解可以标识一个类为实物类,在该类中所有的方法为事务方法,可以通过在事务方法中抛出自定义异常,借由框架完成事物的回滚.这里,自定义异常必须继承`RuntimeException`类,只有运行期异常才可以被框架捕获并处罚事务回滚.

在该自定义异常中,使用父类的构造方法,将枚举类中的错误信息放入错误堆栈中,使用子类的构造方法,传入错误枚举类,为子类的私有属性赋值.综上,可以通过传入一个信息类的枚举,构建一个自定义异常.
``` java
public class UserException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**自定义异常代码*/
	private Integer code;
	
	public UserException(ResultEnum resultEnum) {				//将枚举类传入,作为构造方法的参数
		super(resultEnum.getMessage());			//调用父类的构造方法
		this.code = resultEnum.getCode();		//追加两个参数的构造方法
	}

	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
}
```


## 统一异常处理

`@ControllerAdvice`表示为控制层的通知类,在该类中完成对异常类的捕获处理
`@ExceptionHandler`标注在方法中,指定该方法捕获的运行期异常种类,实现对该类型异常的指定捕获.
`@ResponseBody`标注在方法中,将方法的返回值,以JSON形式传到前台.

这里,通过对所有运行期异常进行捕获并做区分,凡是自定义的`UserException`类异常则进行强制类型转换,将错误信息中的code和message传入到返回结果的包装类中,返回前台;如果是其他未知的异常,则统一返回枚举中定义的其他异常.
``` java
@ControllerAdvice 		//控制层的通知
public class ExceptionHandle {

	@ExceptionHandler(value=Exception.class)			//处理异常的类,这里将异常统一捕获,完成分类处理
	@ResponseBody
	public ResultMessage<?> handle(Exception exception) {
		if (exception instanceof UserException ){				//如果属于自定义的异常
			UserException e = (UserException) exception;		//强制类型转换
			return ResultUtil.error(e.getCode(), e.getMessage());	//将抛出的异常捕获后包装
		}
		
		return ResultUtil.error(ResultEnum.ERROR_UNKNOWN.getCode(),ResultEnum.ERROR_UNKNOWN.getMessage());				//如果不是自动返回系统的异常
	}
}
```


## REST风格接口

## ASPECT切面编程

## 日志记录

## 配置文件


# Freemaker模板

  [1]: http://start.spring.io/