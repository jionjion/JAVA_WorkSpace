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
			- `Logs.java`     实现序列化接口的日志记录Bean
			- `User.java`    实现序列化接口的用户Bean
		- `collector`
			- `HelloFreeMarker.java`    FreeMarker的前端控制器
			- `UserController.java`        用户模块的前端控制器
		- `dao`
			- `LogsRepository.java`      JPA规范,日志记录的映射数据库
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
		- `HelloController.java`             HelloWord示例
		- `MicroServicesApplication.java`    SpringBoot的入口类,开启Web容器
		- `ServletInitializer.java`
	- `resources`
		- `config`
			- `application.properties`  SpringBoot的配置文件,默认位置如此,包含SpringBoot框架支持的所有配置信息
		- `module`
			- `user`
				- `hello.html`              FreeMaker的显示模板
				- `user.html`              FreeMaker的数据结构
		- `static`
			- `css`
			- `fonts`
			- `img`
			- `js`
		- `templates`                   FreeMaker的模板片段
		- `applicationContext.xml`    整体的配置文件
- `test`
	- `java\MicroServices`
		- `collector`                 前端控制器测试类
		- `service`                   服务层测试类
		- `MicroServicesApplicationTests.java` 空运行测试类

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


| 请求类型 | 路径                            | 功能                      |
| -------- | ------------------------------- | ------------------------- |
| get      | /user/users                     | 查询全部                  |
| post     | /user/user                      | 更新一个                  |
| get      | user/users/{id}                 | 查询一个                  |
| put      | /user/users/{id}                | 更新一个                  |
| delete   | /user/users/{id}                | 删除一个                  |
| get      | /user/users/username/{username} | 条件查询                  |
| get      | /user/error/{code}              | 进行异常抛出,统一异常捕获 |

`@RestController`标注该类为一个前端控制器类
`@RequestMapping`标注在类上或者方法,共同构成请求的URL路径
`@Transactional`标注在类或者方法上,表示这是一个事务类或者方法
`@Autowired`标注在私有属性中,在类初始化时完成依赖注入
`@PostMapping`标注在方法中,表示该请求为Post请求
`@GetMapping`标注在方法中,表示该请求为Get请求
`@PutMapping`标注在方法中,表示该请求为Put请求
`@DeleteMapping`标注在方法中,表示该请求为Delete请求
`@PathVariable`标注在参数中,表示从URL占位符中获得参数
`@RequestParam`标注在参数中,表示从请求中获得参数
``` java
@RestController
@RequestMapping("/user")
@Transactional			//事务接口,表示该类同时为事务
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	
	/**	查询全部
	 * 	URL: http://localhost:8080/MicroServices/user/users
	 * */
	@GetMapping(value="/users")			//get方式,获得全部信息
	public List<User> userList() {
	
		return userRepository.findAll();
	}
	
	/**	新增一个
	 * 	URL: http://localhost:8080/MicroServices/user/users
	 */
	@PostMapping(value="/users")		//post方式,添加信息
	@Transactional						//事务接口,表示该方法为事务
	public ResultMessage<User>	userSave(@Valid User user ,BindingResult result) {	//@Valid表示进行对象参数验证	BindingResult:为参数的验证结果
		ResultMessage<User> resultMessage = new ResultMessage<User>();//封装的结果对象
		if (result.hasErrors()) {		//如果有错误,则返回bean中的定义
			String message = result.getFieldError().getDefaultMessage();
			return ResultUtil.error(400, message);
		}
		user.setUsername(user.getUsername());	//传入User对象,前台只需要传递对象的属性名即可
		user.setPassword(user.getPassword());
		return ResultUtil.success(userRepository.save(user));
	}
	
	/**	查询一个
	 * 	URL: http://localhost:8080/MicroServices/user/users/1  
	 * */
	@GetMapping(value="/users/{id}")			//get方式,获得全部信息
	public User userGet(@PathVariable("id") Integer id) {
		
		return userRepository.findOne(id);
	}
	
	/**	更新一个
	 * 	URL: http://localhost:8080/MicroServices/user/users/3 
	 */
	@PutMapping(value="/users/{id}")			//get方式,获得全部信息
	public User userUpdate(	@PathVariable("id") Integer id,
							@RequestParam("username") String username,
							@RequestParam("password") String password) {
		
		User user = new User();
		user.setId(id); 						//保存主键
		user.setUsername(username);
		user.setPassword(password);
		return userRepository.save(user);		//当保存主键一致时,为更新操作
	}
	
	/**	删除一个
	 * 	URL: http://localhost:8080/MicroServices/user/users/3  
	 * */
	@DeleteMapping(value="/users/{id}")			//get方式,获得全部信息
	public void userDelete(@PathVariable("id") Integer id) {
		userRepository.delete(id);
	}
	
	
	/**	自定义查询,通过姓名查询用户
	 * 	URL: http://localhost:8080/MicroServices/user/users/username/Jion  
	 * */
	@GetMapping(value="/users/username/{username}")			//get方式,获得全部信息,注意路径不要冲突
	public List<User> userGetByUsername(@PathVariable("username") String username) {
		
		return  userRepository.findByUsername(username);
	}
	
	/**
	 * 	统一异常捕获.
	 * 	将service层抛出的异常,dao层抛出的异常进行统一向外抛出,交由ExceptionHandle类进行包装向前台传递
	 * 	这里模拟包含的类进行的异常抛出
	 * 	URL:http://localhost:8080/MicroServices/user/error/2			调用第二种异常,并返回
	 * */
	@GetMapping(value="/error/{code}")			//get方式,获得全部信息,注意路径不要冲突
	public void errorCodeHandler(@PathVariable("code") String code) throws Exception{
		if ("1".equals(code)) {
			throw new UserException(ResultEnum.ONE_ERROR);				//抛出自定义的异常.完成统一异常处理
		}
		if ("2".equals(code)) {
			throw new UserException(ResultEnum.TWO_ERROR);
		}
			throw new UserException(ResultEnum.OTHER_ERROR);
	}
}
```


## ASPECT切面编程

创建一个切面编程类,实现对请求中用户地址信息的获取.

`@Aspect`注解在类上,表示该类为一个切面类
`@Component`注解在类上,将类托管到Spring容器中.
`@Before`前置通知,可以传入方法或者切面表达式,表示在该方法执行前进行通知.
`@After`后置通知,可以传入方法或者切面表达式,表示在该方法执行后进行通知.
`@Pointcut`定义切点表达式,在表达式匹配的方法中进行执行.
`@AfterReturning`在方法执行完后进行通知

``` java
@Aspect								//作为AOP
@Component							//引入到Spring容器
public class HttpAspect {

	@Autowired
	private LogsRepository logsRepository;
	
	/*
	 * 	一般的切面编程
	 * */
	/**在访问前统一AOP处理*/
//	@Before("execution(public * MicroServices.collector.UserController.userList(..))")						//拦截UserController类下的userList方法
	@Before("execution(public * MicroServices.collector.UserController.*(..)))")						//拦截UserController类下的所有方法
	public void logBefor() {
		System.out.println("------------开始访问--------");
	}
	
	/**在访问后统一AOP处理*/
	@After("execution(public * MicroServices.collector.UserController.*(..)))")
	public void logAfter() {
		
		System.out.println("------------访问结束-----------");
	}
	
	
	/*	
	 * 	使用切点结合切面方法,完成相同方法的不同调用,实现代码重用
	 * 	*/
	 
	private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);
	 
	/**定义切点*/
	@Pointcut("execution(public * MicroServices.collector.UserController.*(..)))")
	public void log() {}
	
	/**日志记录对象*/
	private Logs logs = new Logs();
	
	/**定义切面,完成切面编程*/
	@Before("log()")
	public void classBefore(JoinPoint joinpoint) {
		
		logger.info("-----各种类进行调用----");

		//获取request对象
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
	
		//获取ip
		String ip = request.getRemoteAddr();
		logger.info("IP>>>{}", ip);
		logs.setIp(ip);
		
		//获取url
		String url = request.getRequestURL().toString();
		logger.info("URL>>>{}", url);
		logs.setUrl(url);
		
		//获取mothod
		String method = request.getMethod();
		logger.info("Method>>>{}",method);
		logs.setMethod(method);
		
		//获取类名
		String className = joinpoint.getSignature().getDeclaringTypeName();
		logs.setClassName(className);
		logger.info("ClassName>>>{}", className);
		logs.setUrl(className);
				
		//获取方法名
		String methodName = joinpoint.getSignature().getName();
		logs.setMethodName(methodName);
		logger.info("MethodName>>>{}", methodName);
		logs.setUrl(methodName);
		
		//参数
		Object[] params =  joinpoint.getArgs();
		logs.setParams(params);
		logger.info("Params>>>{}",params);
		
	}
	
	@After("log()")
	public void classAfter() {
		logger.info("-----各种类结束调用----");
	}
	
	@AfterReturning(returning="object",pointcut="log()")
	public void classReturn(Object object){
		logger.info("-----各种类返回调用结束----");
		logger.info("Return>>>{}",object);
		logs.setRetruns(object.toString());
		logsRepository.save(logs);
		logs = null;
	}
}
```


## 日志记录

通过传入`JoinPoint`类,获得请求的中的各种参数,
`LoggerFactory.getLogger(Class<?> class)`创建日志实例
`logger.info("IP>>>{}", ip)`,进行日志信息级别的输出

``` java
public class HttpAspect {
	
	 
	private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);
	
	/**日志记录对象*/
	private Logs logs = new Logs();
	
	public void log(JoinPoint joinpoint) {
		
		logger.info("-----各种类进行调用----");

		//获取request对象
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
	
		//获取ip
		String ip = request.getRemoteAddr();
		logger.info("IP>>>{}", ip);
		logs.setIp(ip);
		
		//获取url
		String url = request.getRequestURL().toString();
		logger.info("URL>>>{}", url);
		logs.setUrl(url);
		
		//获取mothod
		String method = request.getMethod();
		logger.info("Method>>>{}",method);
		logs.setMethod(method);
		
		//获取类名
		String className = joinpoint.getSignature().getDeclaringTypeName();
		logs.setClassName(className);
		logger.info("ClassName>>>{}", className);
		logs.setUrl(className);
				
		//获取方法名
		String methodName = joinpoint.getSignature().getName();
		logs.setMethodName(methodName);
		logger.info("MethodName>>>{}", methodName);
		logs.setUrl(methodName);
		
		//参数
		Object[] params =  joinpoint.getArgs();
		logs.setParams(params);
		logger.info("Params>>>{}",params);
		
	}
}
```

## 配置文件
通过在`src/main/resources`目录下创建`applicationContext.xml`文件,对SpringBoot进行配置.
``` xml
<beans>

	<!-- Spring的容器配置文件 -->
	
	<!-- 开启注解 -->
	<context:annotation-config/>
	<!-- 引入注解驱动 -->
	<mvc:annotation-driven/>
	
	<!-- 通过注解,实现bean的自动注入 -->
	<context:component-scan base-package="collector.*"/>
	<context:component-scan base-package="service.*"/>
	<context:component-scan base-package="dao.*"/>
	<context:component-scan base-package="bean.*"/>
</beans>
```



## 测试类

### 空类测试
使用空类进行测试,完成请求的定义
``` java
@RunWith(SpringRunner.class)
@SpringBootTest
public class MicroServicesApplicationTests {

	@Test
	public void contextLoads() {
	}
}
```

### 前端控制器测试
通过对请求路径的访问,判断状态码是否正常进行测试

``` java
@RunWith(SpringRunner.class)
@SpringBootTest	
@AutoConfigureMockMvc	
public class UserControllerTest{

	@Autowired
	private MockMvc mvc;
	
	@Test
	public void testUserGet() {
		try {
			mvc.perform(MockMvcRequestBuilders.get("/user/users/1"))				//请求的url映射,为类验证+方法验证
							.andExpect(MockMvcResultMatchers.status().isOk());	//期望状态码为OK
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
```

### 服务层测试类
通过查询数据库中的某个记录,将其字段值与预期值相比较判断是否运行成功
``` java
/**测试Service层的方法*/
@RunWith(SpringRunner.class)
@SpringBootTest				//启动SpringBoot
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Test			
	public void testFindOne() {			//测试查找一个
		User user = userService.findOne(1);	//查找第一个
		Assert.assertEquals(new String("123456"), user.getPassword());	//断言数据库的密码和查询中的是否一致
	}
}
```



# Freemaker模板

## 简介
模板引擎技术的一种,是Spring官方推荐的一种热部署方式.

## 与SpringBoot结合

### 配置`application.properties`

常用到的Freemarker模板设置,其中最重要的是
`spring.freemarker.template-loader-path` 指定了模板存放的位置
`spring.freemarker.prefix` 模板的前缀
`spring.freemarker.suffix` 模板的后缀
`spring.freemarker.content-type` 模板的连接方式

``` 
## Freemarker的模板引擎配置
spring.freemarker.settings.tag_syntax=auto_detect
spring.freemarker.settings.template_update_delay=1
spring.freemarker.settings.defaultEncoding=UTF-8
spring.freemarker.settings.url_escaping_charset=UTF-8
spring.freemarker.settings.locale=zh_CN
spring.freemarker.settings.boolean_format=true,false
spring.freemarker.settings.datetime_format=yyyy-MM-dd HH:mm:ss 
spring.freemarker.settings.time_format=HH:mm:ss
spring.freemarker.settings.number_format=0.#####
spring.freemarker.settings.whitespace_stripping=true
#spring.freemarker.settings.auto_import=templates/spring.ftl
spring.freemarker.settings.classic_compatible=true
spring.freemarker.settings.template_exception_handler=ignore
spring.freemarker.order=1
## Freemarker的页面层展示
spring.freemarker.allow-request-override=false
spring.freemarker.check-template-location=true
spring.freemarker.expose-request-attributes=false
spring.freemarker.expose-session-attributes=false
spring.freemarker.expose-spring-macro-helpers=false

## 指定加载该class目录下,完成模板的扫描,指定前缀,后缀文件,连接格式,缓存以及字符编码
spring.freemarker.template-loader-path=classpath:/module/user/
spring.freemarker.prefix=
spring.freemarker.suffix=.html
spring.freemarker.content-type=text/html; charset=utf-8
spring.freemarker.cache=false
spring.freemarker.charset=UTF-8

```

### 配置前端控制器

通过返回`hello`结合配置文件中指定的前后缀,及文件位置,确定模板引擎需要渲染的文件.

``` java
@Controller
@RequestMapping("/freemarker")
public class HelloFreeMarker {

	/**	该模块的显示页面
	 * 	调用freemaker模板
	 *  URL: http://localhost:8080/MicroServices/freemarker/hello
	 * */
    @RequestMapping("hello")
    public String hello(Model model){
    	System.out.println("----------进入控制层-------------");
        model.addAttribute("message", "freemarker");
        return "hello";
    }
}
```

### 配置显示页面

```  html
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户展示页</title>
</head>
<body>
	<h1>你好,${message}</h1>
	<h1>用户模块下的..</h1>
</body>
</html>
```

## Freemaker常用的数据结构

### 不同数据结构的前端控制器

``` java
@Controller
@RequestMapping("/freemarker")
public class HelloFreeMarker {

	
    /**	用户展示页面
     * 	URL: http://localhost:8080/MicroServices/freemarker/userList  
     * */
    @GetMapping("/userList")
    public ModelAndView userList() {
		ModelAndView modelAndView = new ModelAndView("user");
		//基本数据类型
		int intType = 123;
		long longType = 12345678910l;
		double doubleType = 3.141592678d;		//精度由配置文件指定
		boolean booleanType = true;
		modelAndView.addObject("intType",intType);
		modelAndView.addObject("longType",longType);
		modelAndView.addObject("doubleType",doubleType);
		modelAndView.addObject("booleanType", booleanType);
		
		//日期类型
		Date dateType = new Date(new java.util.Date().getTime());	//对SQL的Date类型进行转换
		modelAndView.addObject("dateType", dateType);
		
		//引用类型
		User user = new User();
		user.setUsername("Jion");
		modelAndView.addObject("user",user);
		
		//字符串数据类型
		modelAndView.addObject("message","后台消息");
		modelAndView.addObject("briefType", "<em>富文本字符串</em>");
		
		//List集合数据类型
		List<String> listType = new ArrayList<String>();
		listType.add("元素一");
		listType.add("元素二");
		listType.add("元素三");
		modelAndView.addObject("listType", listType);
		
		//Map集合数据类型,每次获取的时候顺序不一致
		Map<String, String> mapType = new HashMap<String,String>();
		mapType.put("keyOne", "元素一");
		mapType.put("keyTwo", "元素二");
		mapType.put("keyThree", "元素三");
		modelAndView.addObject("mapType", mapType);
		
		//自定义函数,求和数列
		SumListMethod sumListMethod = new SumListMethod();
		modelAndView.addObject("sum_list",sumListMethod);
    	return modelAndView;
	}
}
```

### Freemaker显示不同数据结构

**基本数据类型获取**

``` xml
整型:${intType}
长整型:${longType}
浮点型:${doubleType}
布尔型:${booleanType}
```

**日期类型数据获取**

``` xml
日期型:${dateType}
时间戳:${dateType!?string('yyyy-MM-dd  hh:mm:ss')}
```

**非空判断**

``` xml
为空默认:${nothing!'默认值'}
判断集合是否为空:${listType??}
判断集合是否为空:${listType?exists}
```

**自定义变量**

``` xml
<#assign param="哔哩哔哩"/>
自定义变量:${param}
<#assign num=50/>
变量计算求和:${num+100}
```

**自定义引用类型**

``` xml
姓名:${user.username}			 获取对象属性
密码:${user.password!}		     获取不存在的属性,使用!
地址:${(address.position)!}		 使用!结合(),判断对象是否存在,如果存在则获取属性
```


**字符串类型**

``` xml
消息:${message}  
使用!避免空指针:${nothing!}
富文本:${briefType}
html转义文本:${briefType?html}
```


**List集合类型**

``` xml
<#list listType as item>
	<p>${item}</p>
</#list>
```

**Map集合类型**
将键作为List遍历,通过   名称[键]  的方式完成获取 
``` xml
<#list mapType?keys as key>					
	<p>键:${key}	值:${mapType[key]}</p>
</#list>
```

**逻辑运算符**

``` xml
<#if true && (false || !false)>
	<p>非!,与&&,或||</p>
</#if>
```


**逻辑if**

``` xml
if逻辑
<#if score==60>
	及格万岁
</#if>

if...else逻辑
<#if score&lt;90>							//小于90分
	渣渣...
<#else>
	流弊啊..
</#if>

if..elseif..else逻辑
<#if score&lt;60>							//小于60分
	不及格
<#elseif score&lt;90>				   //不小于60分,但小于90分
	只是及格了
<#else>										//其他情况
	还可以
</#if>
```

**String的内建函数**

``` xml
<#assign a='hello' b=' world'/>
字符串相加:${(a+b)}
字符串截取:${(a+b)?substring(6,8)}		//[6,8)的位置,从0开始
字符裁剪空串:${(a+b)?trim}
字符的长度:${(a+b)?length}
首字母大写:${(a+b)?cap_first}
字符转大写:${(a+b)?upper_case}
字符转小写:${(a+b)?lower_case}
前索引'o'位置:${(a+b)?index_of('o')}
后索引'o'位置:${(a+b)?last_index_of('o')}
替换'o'为'O':${(a+b)?replace('o','O')}
是否以'o'结尾:${(a+b)?ends_with('d')}
是否包含'o':${(a+b)?contains('o')}
```

**字符分隔**

``` xml
<#list "a|b|c|d"?split("|") as item>
	<strong>${item}</strong>
</#list>
```

**字符串转日期**

``` xml
<#assign date1 = "2017-7-29"?date("yyyy-MM-dd")>
${date1}

<#assign date2 = "12:00:00"?time("HH:mm:ss")>
${date2}

<#assign date3 = "2017-7-29 12:30 PM"?datetime("yyyy-MM-dd hh:mm")>
${date3}
```


**四舍五入**

``` xml
<#assign pi=3.141596278/>
小数点后两位:${pi?string('0.##')}
小数四舍五入:${pi?round}
小数舍一:${pi?floor}
小数进一:${pi?ceiling}
```


**自定义函数**

``` xml
自定义函数,数列求和
<#assign listNum = [1,2,3]/>
求和:${sum_list(listNum,'sum')}

<#function add_num param1 param2>
	<#return param1 + param2>
</#function>
调用
定义函数相加:1+2=${add_num(1,2)}
```


**List排序**

``` xml
<#assign listNum = [1,4,7,8,9,6,3,2,5]/>

<#list listNum?sort?reverse as item>		?sort调用排序方法,?reverse反转数列
	${item_index}:${item}			        _index表示元素的位置 -->
</#list>
list的长度:${listNum?size}				    lsit的长度 -->
获取第一个:${listNum[0]}					通过[]获取指定位置元素,不是排序后的第一个
```


**宏指令**

``` xml
<!-- 自定义无参的指令	  	no_param_macro:指令名称-->
<#macro no_param_macro>
	<p><em>调用时输出的html文本</em></p>
</#macro>
<!-- 调用自定义无参的指令 -->
<@no_param_macro/>

<!-- 自定义有参的指令	 	has_param_macro:指令名称	param1:参数	param2="默认参数.." 具有默认参数的,如果不传递,则使用默认参数-->
<#macro has_param_macro param1 param2="默认参数..">
	<p><strong>收到参数:<em>${param1}</em>,<em>${param2}</em></strong></p>
</#macro>
<!-- 调用自定义有参的指令 -->
 <@has_param_macro param1="参数一" param2="参数二" />


<!-- 自定义参数不固定的指令	  	no_param_macro:指令名称	params:表示其他参数	-->
<#macro many_param_macro params...>
	<p>	参数一:<strong>${params['one']}</strong>
		参数二:<strong>${params['two']}</strong>
		参数三:<strong>${params['three']}</strong></p>
</#macro>
<!-- 调用自定义无参的指令 -->
<@many_param_macro one="元素一" two="元素二" three="元素三"/>

```

  [1]: http://start.spring.io/