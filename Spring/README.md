--- 
title: Spring框架的介绍
tags: JDK8,Eclipse
---

[TOC]

---

# 简介
Spring是一个轻量级的控制反转(IOC)与依赖注入( DI)和面向切面(AOP)的容器框架
本项目介绍了Spring框架中常用的技术,例如控制反转和切面编程,基于XML和注解的配置方式,已经SpringData等扩展模块的介绍.

# 包结构
- `main` 编写的类,实现各个功能
	- `java` 各种功能实现类
		- `aop`
			- `api` 使用接口的方式配置通知
			- `aspectj` 注解配置通知
			- `schema` XML配置通知
		- `autowiring`  依赖注入的实现方式
		- `aware` 对应资源获取
		- `bean`  Bean作用范围
		- `beanannotation`
			- `injection` Bean注解实现依赖注入
			- `javabased` Spring容器注解
			- `jsr` Bean的其他注解
			- `multibean` Bean泛型和多态实例的自动注入
			- `BeanAnnotation`  Bean的注解配置
		- `ioc`
			- `injection` Web中依赖注入
			- `interfaces` 接口中依赖注入
		- `lifecycle` Bean生命周期
		- `resource` 获得资源文件
	- `resource`各种功能对应的配置文件
		- `config.properties`数据库配置文件
		- `config.txt` 
		-  `config.xml` 资源文件的获取
		-  `jdbc.properties` 资源文件的获取
		-  `spring-aop-api.xml`  使用接口的方式配置通知的配置文件
		-  `spring-aop-aspectj.xml` 注解配置通知的配置文件
		-  `spring-aop-schema-advice.xml` XML配置通知的配置文件
		-  `spring-aop-schema-advisors.xml`  XML配置通知的配置文件
		-  `spring-autowiring.xml`  依赖注入的配置文件
		-  `spring-aware.xml` 对应资源获取的配置文件
		-  `spring-beanannotation.xml` Bean扫描的配置文件
		-  `spring-beanscope.xml` Bean作用范围的配置文件
		-  `spring-injection.xml` Web中依赖注入的配置文件
		-  `spring-ioc.xml`  接口中依赖注入
		-  `spring-lifecycle.xml`  Bean生命周期的配置文件
		-  `spring-resource.xml` 获得资源文件的配置文件
- `test` 对应的测试类
	- `java` 各种功能测试类
		- `aop` 
			- `aspectj`  注解配置通知的测试
			- `TestAOPAPI`  使用接口的方式配置通知的测试
			- `TestAOPSchemaAdvice`  XML配置通知的测试
			- `TestAOPSchemaAdvisors`  XML配置通知的测试
		- `autowiring` 依赖注入的测试
		- `aware` 对应资源获取的测试
		- `base` 编写基础的测试类,便于其他测试类继承使用
		- `bean` Bean作用范围的测试
		- `beanannotation`  
			- `TestBeanAnnotation`  Bean的注解配置的测试
			- `TestInjection`Bean注解实现依赖注入的测试
			- `TestJavabased`  Spring容器注解的测试
			- `TestJsr` Bean的其他注解的测试
			- `TestMulti`Bean泛型和多态实例的自动注入的测试
		- `ioc`
			- `TestInjection` Web中依赖注入的测试
			- `TestOneInterface`接口中依赖注入的测试
		- `lifecycle`  Bean生命周期的测试
		- `resource`  获得资源文件的测试
	- `resource`各种功能对应的配置文件的测试类


# Spring核心

## 基础测试类的搭建
1. 引入`junit-*.jar`导入工程
2. 创建`UnitTestBase`类,完成对Spring配置文件的加载,销毁,并作为父类让所有单元测试类子类继承,并提供`getBean()`方法,获得配置文件中定义的容器中应有的对象.
3. 子类在类方法中使用注解`@RunWith(BlockJUnit4ClassRunner.class)`

**基本测试类的编写**
`@Before`注解的方法在类加载时进行执行,这里我们使用`ClassPathXmlApplicationContext`类构建Spring容器,该类通过传入一个类路径下的所有XML文件,获取容器所需要创建的类,并加载.这里的类路径包含`resource`目录下的文件,调用`context.start()`进行容器启动.

`@After`注解的方法在类销毁时执行,这里调用`context.destroy()`进行容器的销毁过程.

`getBean()`方法有两个重载方法,分别传入Bean的ID和Bean的类类型,通过调用容器的`context.getBean()`方法,实现通过Bean的ID或类类型获得对象实例,以便子类测试使用.

``` java
public class UnitTestBase {
	
	private ClassPathXmlApplicationContext context;
	
	private String springXmlpath;										//xml文件的位置
	
	public UnitTestBase() {}
	
	public UnitTestBase(String springXmlpath) {
		this.springXmlpath = springXmlpath;								//构造器传入xml文件的位置
	}
	
	@Before
	public void before() {
		if (StringUtils.isEmpty(springXmlpath)) {						//如果变量对象为空,加载全部配置文件
			springXmlpath = "classpath*:spring-*.xml";
		}
		try {															//如果不为空则字符串分隔后进行加载
			context = new ClassPathXmlApplicationContext(springXmlpath.split("[,\\s]+"));
			context.start();											//容器启动
		} catch (BeansException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void after() {
		context.destroy();												//执行后销毁
	}
	
	/**
	 * @param beanId bean在配置时的名字
	 * @return 返回对象
	 */
	@SuppressWarnings("unchecked")
	protected <T extends Object> T getBean(String beanId) {
		try {
			return (T)context.getBean(beanId);
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param clazz 传入类的类类型
	 * @return	返回对象结果
	 */
	protected <T extends Object> T getBean(Class<T> clazz) {
		try {
			return context.getBean(clazz);
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	}
}
```

**Spring容器的加载方式**
1. 使用文件路径加载

``` java
FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("F:/workspace/appcontext.xml")
```


2. 使用类路径加载

``` java
ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml")
```

3. 使用Web.xml加载
加载Web前端控制器
``` xml
<servlet>
	<servlet-name>springDispatcherServlet</servlet-name>
	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	<init-param>
		<!-- 将Spring的所有XML路径写下 -->
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:spring/spring-*.xml</param-value>
	</init-param>
	<load-on-startup>1</load-on-startup>
</servlet>
```
加载类容器
``` xml
<listener>
  	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
  <servlet>
  	<servlet-name>context</servlet-name>
  	<servlet-class>org.springframework.web.context.ContextLoaderListener</servlet-class>
  	<load-on-startup>1</load-on-startup>
</servlet>
```

## IOC控制反转与DI依赖注入
### IOC控制反转
控制权的转移,应用程序不负责依赖对象的创建和维护,而由外部容器负责创建和维护.

- 定义接口

``` java
public interface OneInterface {
	public void say(String arg);
}
```

- 定义实现类

``` java
public class OneInterfaceImpl implements OneInterface {
	public void say(String arg) {
		System.out.println("实现类: " + arg);
	}
}
```

- 托管Spring容器

``` xml
<beans>
    <!-- 对Spring的Bean的配置,托管后交由 Spring管理-->    
	<bean id="oneInterface" class="ioc.interfaces.OneInterfaceImpl"></bean>
 </beans>
```

- 测试获取

``` java
@RunWith(BlockJUnit4ClassRunner.class)
//继承自基础类,传入配置文件的路径,Spring容器加载配置文件,并返回对应的bean对象
public class TestOneInterface extends UnitTestBase {

	public TestOneInterface() {
		super("classpath*:spring-ioc.xml");
	}
	
	/**通过传入配置文件中bean的名字完成IOC控制反转*/
	@Test
	public void testSay() {
		OneInterface oneInterface = super.getBean("oneInterface");
		oneInterface.say("测试类...");
	}

	/**通过传入类型的类类型,完成IOC控制反转*/
	@Test
	public void testSay2() {
		
		OneInterface oneInterface = super.getBean(OneInterface.class);
		oneInterface.say("测试类...");
	}
}
```


### DI依赖注入
是对控制反转的一种实现,负责创建对象并维护对象之间的关系.
在启动Spring容器加载Bean配置的时候,完成的对变量赋值的行为

- XML配置
首先在XML的根节点`<beans>`开启`default-autowire`属性,选择对应的自动装配型`constructor(构造器);byName(名字);byType(类型);"default"(默认);no(禁止)`

| 方式        | 说明                                                                                               |
| ----------- | -------------------------------------------------------------------------------------------------- |
| No          | 不做任何操作                                                                                       |
| byName      | 根据属性名自动装配                                                                                 |
| byType      | 如果容器中存在与指定属性相同的Bean则装配;如果存在多个,则抛出异常;如果不存在则隐式装配失败,不做异常 |
| Constructor | 使用构造参数装配                                                                                   |
如果使用依据名称或者类型自动装配,则不能再声明构造器方式,否则会抛出异常.
如果使用构造器方式自动装配,则使用私有属性后set/get方法并不会改变注入方式


``` xml
<beans  default-autowire="constructor">
	<!-- 自动装配的类型:constructor(构造器);byName(名字);byType(类型);"default"(默认);no(禁止) -->
	<bean id="autoWiringService" class="autowiring.AutoWiringService" ></bean>
	<bean id="autoWiringDAO" class="autowiring.AutoWiringDAO" ></bean>
</beans>
```

- 构造注入
1. 私有注入对象的属性
2. 在构造器中传入对象属性对象

``` java
public class AutoWiringService {
	private AutoWiringDAO autoWiringDAO;
	public AutoWiringService(AutoWiringDAO autoWiringDAO) {
		System.out.println("通过构造器,自动注入Dao层");
		this.autoWiringDAO = autoWiringDAO;
	}
}	
```

- 设值注入
1. 私有注入对象的属性
2. 提供对象的set方法,并在其中添加

``` java
public class AutoWiringService {
	
	private AutoWiringDAO autoWiringDAO;
	
	public void setAutoWiringDAO(AutoWiringDAO autoWiringDAO) {
		System.out.println("通过set方法,自动注入Dao层");
		this.autoWiringDAO = autoWiringDAO;
	}
}
```

- 测试方法
1.通过继承父类,调用父类的方法,传入配置文件的路径
2.使用父类的`getBean()`方法,获得容器中依赖注入的方法

``` java
@RunWith(BlockJUnit4ClassRunner.class)
public class TestAutoWiring extends UnitTestBase {
	
	public TestAutoWiring() {
		super("classpath:spring-autowiring.xml");
	}
	
	@Test
	public void testSay() {
		AutoWiringService service = super.getBean("autoWiringService");
	}
}
```

### Web中常用的依赖注入方式
- 创建Dao层接口

``` java
public interface InjectionDAO {
	public void save(String arg);
}
```

- 创建Dao层接口实现类

``` java
public class InjectionDAOImpl implements InjectionDAO {
	public void save(String arg) {
		//模拟数据库保存操作
		System.out.println("保存数据：" + arg);
	}
}
```

- 创建Service层接口

``` java
public interface InjectionService {
	public void save(String arg);
}
```

- 创建Service层接口实现类并通过构造器注入依赖

``` java
public class InjectionServiceImpl implements InjectionService {
	
	//必须,首先私有Dao层
	private InjectionDAO injectionDAO;
	
	/**构造器注入,如果用设置注入,则不用构造器,注意构造器内的传入参数的名字需要和xml中的bean的Id一致*/
	public InjectionServiceImpl(InjectionDAO injectionDAO) {
		this.injectionDAO = injectionDAO;
	}
	
	public void save(String arg) {
		//模拟业务操作
		System.out.println("Service接收参数：" + arg);
		arg = arg + ":" + this.hashCode();
		injectionDAO.save(arg);
	}
}
```

XML中配置

``` xml
<?xml version="1.0" encoding="UTF-8"?>
<beans >
    <!-- bean的名字为接口,指向实现类 -->
    <bean id="injectionDAO" class="ioc.injection.dao.InjectionDAOImpl"></bean>
	<!-- 通过构造注入,在Service层使用Dao层,注意构造器中传入参数的名称和bena的id一致 -->
		<!-- bean的名字为接口,指向为实现类 -->
 		<bean id="injectionService" class="ioc.injection.service.InjectionServiceImpl">
        	<constructor-arg name="injectionDAO" ref="injectionDAO"></constructor-arg>
        </bean>
 </beans>
```

- 创建Service层接口实现类并通过设值注入依赖

``` java
public class InjectionServiceImpl implements InjectionService {
	
	//必须,首先私有Dao层
	private InjectionDAO injectionDAO;
	
	/**设值注入,只需要get()方法即可,如果用构造器,则不需要set()*/
	public void setInjectionDAO(InjectionDAO injectionDAO) {
		this.injectionDAO = injectionDAO;
	}

	public void save(String arg) {
		//模拟业务操作
		System.out.println("Service接收参数：" + arg);
		arg = arg + ":" + this.hashCode();
		injectionDAO.save(arg);
	}
}
```
XML中配置

``` xml
<beans>
    <!-- bean的名字为接口,指向实现类 -->
    <bean id="injectionDAO" class="ioc.injection.dao.InjectionDAOImpl"></bean>
	
	<!-- 通过设置注入,在Service层使用Dao层 -->     
	 <bean id="injectionService" class="ioc.injection.service.InjectionServiceImpl">
		<property name="injectionDAO" ref="injectionDAO"></property>
	</bean> 
</beans>
```



## Bean配置
## xml中`<bean>`标签
### 标签的常用配置

| 标签                | 属性           | 说明                              |
| ------------------- | -------------- | --------------------------------- |
| `<bean>`            |                | Spring容器托管的类实例            |
|                     | id             | 类在容器中的唯一ID                |
|                     | class          | 类的全路径                        |
|                     | scope          | 类的作用范围                      |
|                     | autowire       | 启用构造注入的方式                |
|                     | lazy-init      | 懒加载的方式                      |
|                     | init-method    | 类初始化时,执行的方法             |
|                     | destroy-method | 类销毁时,执行的方法               |
| `<constructor-arg>` | `<bean>`子标签 | 使用构造器注入时,传入构造器的参数 |
| `<property>`        | `<bean>`子标签 | 使用属性值注入时,set私有属性的类  |
|                     | name           | 指向私有属性的Bean实例的id        |
|`<qualifier>` | `<bean>`子标签| 缩小自动装配时的查找范围|
|`<context:property-placeholder>` |   | 属性文件的加载|
|						|	location  | 属性文件的路径|


### Bean的作用范围
通过在Bean的配置标签中指定,其作用范围有以下:

| 参数           | 范围                                                    |
| -------------- | ------------------------------------------------------- |
| singleton      | 单例,每个容器只存在一份                                 |
| prototype      | 每次请求时创建新的,不执行其destroy方法                  |
| request        | 每次http请求时创建,只存在于当前http请求范围内           |
| session        | 每次session会话请求时创建,只存在于当前session会话范围内 |

- XML配置
通过设置`scope`属性为不同的值,进行范围测试
``` xml
<bean id="beanScope" class="bean.BeanScope" scope="prototype"/>
```

- 作用范围
用过在方法中,输出哈希值,进而判断是否为同一个Bean

``` java
public class BeanScope {
	public void say() {
		System.out.println("这个Bean的哈希值 : " + this.hashCode());
	}
}
```


- 测试作用范围
这里由于调用过两次`getBean()`方法,因此容器启动销毁两次,所取到的Bean不为同一个

``` java
@RunWith(BlockJUnit4ClassRunner.class)
public class TestBeanScope extends UnitTestBase {
	public TestBeanScope() {
		super("classpath:spring-beanscope.xml");
	}
	
	@Test
	public void testSay() {
		//在方法中请求IOC获得两次对象,对象的哈希码验证
		BeanScope beanScope = super.getBean("beanScope");
		beanScope.say();
		BeanScope beanScope2 = super.getBean("beanScope");
		beanScope2.say();
	}
}
```



## Bean的生命周期
Bean的生命周期可以分为初始化,使用,销毁三个阶段.在初始化和销毁中可以配置执行自定义的方法.

### 初始化方法
- 实现`InitializingBean`接口
通过直接实现接口中的抽象方法,实现在该类实例化时执行初始化方法
``` java
public class ExampleInitializingBean implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		//do something
		System.out.println("通过接口实现初始化方法");
	}
}
```

- 配置`<bean>`标签下的`init-method`属性,指向本类中的方法

xml中配置初始化方法
``` xml
<bean id="beanLifeCycle" class="lifecycle.BeanLifeCycle"  init-method="start"/>
```

在实现类中定义具体的初始化方法
``` java
public class BeanLifeCycle{
	public void start() {
		System.out.println("自定义Bean开始方法.");
	}
}
```

- 定义默认的全局初始化方法

在`<beans>`标签下声明`default-init-method`属性,指向本类簇的方法,表示在加载任意Bean时,执行初始化方法
``` xml
<beans  default-init-method="defautInit">
        <bean id="beanLifeCycle" class="lifecycle.BeanLifeCycle" />
 </beans>
```

``` java
public class BeanLifeCycle{
	public void defautInit() {
		System.out.println("自定义Bean的全局初始化方法.");
	}
}	
```

### 销毁方法
- 实现`DisposableBean`接口

实现接口中的抽象方法,在类销毁时执行该方法
``` java
public class ExampleDisposableBean implements DisposableBean {
	@Override
	public void destroy() throws Exception {
		//do something
		System.out.println("通过接口实现销毁方法");
	}
}
```

- 配置`<bean>`标签下的`init-method`属性,指向本类中的方法

xml中配置销毁方法
``` xml
<bean id="beanLifeCycle" class="lifecycle.BeanLifeCycle"   destroy-method="stop"/>
```

在实现类中定义具体的销毁方法
``` java
enter code here
```

- 定义默认的全局初始化方法

在`<beans>`标签下声明`default-destroy-method`属性,指向本类簇的方法,表示在加载任意Bean时,执行销毁方法
``` xml
<beans  default-destroy-method="defaultDestroy">
        <bean id="beanLifeCycle" class="lifecycle.BeanLifeCycle" />
 </beans>
```

``` java
public class BeanLifeCycle{
	public void defaultDestroy() {
		System.out.println("自定义Bean的全局销毁方法.");
	}
}	
```
**测试Bean生命周期**
通过测试可以知道,通过接口实现的初始化/销毁方法最先执行;在`<Bean>`中配置的其次执行.
通过`<Beans>`配置的默认初始化/销毁方法在以上两者实现后,默认不执行,其执行优先级最低.

``` java
@RunWith(BlockJUnit4ClassRunner.class)
public class TestBeanLifecycle extends UnitTestBase {
	
	public TestBeanLifecycle() {
		super("classpath:spring-lifecycle.xml");
	}
	
	@Test
	public void test1() {
		super.getBean("beanLifeCycle");
	}
}
```


## `Aware`接口
Spring中对以Aware结尾的接口,实现后可以获得对应的资源,进而进行一些操作.

### 获得上下文对象
`ApplicationContextAware`接口可以获得Spring容器对象
``` java
public class MoocApplicationContext implements ApplicationContextAware  {
	/**接口唯一的方法,获取上下文对象后获得Bean对象**/
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		System.out.println("获取上下文对象,MoocApplicationContext : " + applicationContext.getBean("moocApplicationContext").hashCode());
	}
}
```
### 获得Bean的名字
`BeanNameAware`接口可以获得Spring容器中,当前类的Bean的名称

``` java
public class MoocBeanName implements BeanNameAware, ApplicationContextAware {

	private String beanName;	//需要私有名称
	
	@Override	//根据使用接口的Bean,获取Bean的名字
	public void setBeanName(String name) {
		this.beanName = name;
		System.out.println("获取Bean的名字 : " + name);
	}

	@Override	//根据设置的Bean的名字,有上下文对象获取Bean
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		System.out.println("获取Bean的上下文对象 : " + applicationContext.getBean(this.beanName).hashCode());
	}
}

```

**测试aware接口**

``` java
@RunWith(BlockJUnit4ClassRunner.class)
public class TestAware extends UnitTestBase {
	
	public TestAware() {
		super("classpath:spring-aware.xml");
	}
	
	@Test
	public void testMoocApplicationContext() {
		System.out.println("测试获取Bean的上下文对象 : " + super.getBean("moocApplicationContext").hashCode());
	}
	
	@Test
	public void textMoocBeanName() {
		System.out.println("测试获取Bean的名字 : " + super.getBean("moocBeanName").hashCode());
	}
}
```

## 资源文件接口
以`Resource`结尾的接口是对资源文件的统一定位接口.
### 常用的资源文件接口

| 接口                   | 说明                      |
| ---------------------- | ------------------------- |
| UrlResource            | URL对应的资源,根据URL构建 |
| ClassPathResource      | 获得类路径下的资源        |
| FileSystemResource     | 文件系统下的资源          |
| ServletContextResource | ServletContext封装的资源  |
| InputStreamResource    | 输入流中的资源            |
| ByteArrayResource      | 针对字节数组封装的资源    |

### 资源加载器
通过传入资源文件的路径,进而调取.默认所有的 application容器都实现了资源文件接口


### Spring容器加载资源
这里我们通过实现`ApplicationContextAware`接口进而获得Spring容器,加载相对应的资源文件.

| 方法     | 路径                                                              | 说明                                 |
| -------- | ----------------------------------------------------------------- | ------------------------------------ |
| 类路径   | `classpath:config.txt`                                              | 通过类路径,加载对应文件              |
| 文件路径 | `file:F:\\JAVA_WorkSpace\\Spring\\src\\main\\resources\\config.txt` | 通过文件路径,获得对应文件            |
| 空缺     | `config.txt`                                                        | 默认使用ApplicationContext的构造方式 |
| URL路径  | `url:https://baidu.com/`                                            | 获得URL下的文件资源                  |

``` java
public class MoocResource implements ApplicationContextAware  {
	//1.私有属性
	private ApplicationContext applicationContext;
	//2.通过实现接口获得上下文容器
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	/**通过上下文对象获得资源文件对象,它是接口,实现类有很多,方法也很多*/
	public void resource() throws IOException {
		//1.通过classpath:config.txt方式获取
		Resource resource = applicationContext.getResource("classpath:config.txt");
		//2.通过file:F:\\JAVA_WorkSpace\\Spring\\src\\main\\resources\\config.txt方式获取
		Resource resource = applicationContext.getResource("file:F:\\JAVA_WorkSpace\\Spring\\src\\main\\resources\\config.txt");
		//3.空缺,默认使用ApplicationContext的构造方式
		Resource resource = applicationContext.getResource("config.txt");
		//4.通过url访问
		Resource resource = applicationContext.getResource("url:https://baidu.com/");
		
		System.out.println(resource.getFilename());			//文件名
		System.out.println(resource.contentLength());		//文件长度
	}
```

**测试资源文件**

``` java
@RunWith(BlockJUnit4ClassRunner.class)
public class TestResource extends UnitTestBase {
	
	public TestResource() {
		super("classpath:spring-resource.xml");
	}
	
	@Test
	public void testResource() {
		MoocResource resource = super.getBean("moocResource");
		try {
			resource.resource();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
```

## 注解使用

### 注解的开启与包扫描
通过在配置文件中使用`<context:annotation-config/>`或者`<context:component-scan base-package="com.imooc.beanannotation"/>`开启注解配置,并可以配置`scoped- proxy`代理方式.
前者默认开启Spring下的所有类的注解扫描.
后者指定包路径,开启指定类下的注解扫描.,推荐使用后者

**自定义扫描路径**
``` xml
<!-- 自定义扫描 -->
<context:component-scan base-package="beanannotation">
	<!-- 排除的 -->
	<context:exclude-filter type="annotation" expression="XXXX"/>
	<!-- 包括的 -->
	<context:include-filter type="annotation" expression="XXXX"/>
</context:component-scan>
```

### 常用注解

| 注解                     | 属性     | 说明                                           |
| ------------------------ | -------- | ---------------------------------------------- |
| <code>@Scope</code>      |          | 自定义作用域                                   |
| <code>@ Required</code>  |          | 注解后该类在装配时必须被装配                   |
| <code>@ Autowired</code> |          | 注解后该类被自动注入                           |
|                          | required | true/false  是否必须注入,常设置为false         |
| <code>@Order</code>      |          | 对于多实现子类在注入时进行优先级排定           |
| <code>@Qualifier</code>  |          | 缩小自动注入时的查找范围                       |
| <code>@Bean</code>       |          | 表示一个类为Spring托管对象                     |
| <code>@Resource</code>   |          | 该类作为资源类                                 |
| `@PostConstruct`         |          | 初始化方法                                     |
| `@PreDestroy`            |          | 销毁方法                                       |
| `@Inject`                |          | 等效于`@Autowired`,可以用于类,属性,方法,构造器 |
| `@Name`                  |          | 指定Bean的名称                                 |



### `@Component`和`@Scope`注解
`@Component`是通用注解,可以注解任意类,可以在`value`属性中指定Bean的名字,如果不指定则默认为类名首字母小写为Bean的名字
`@Scope`指定Bean的作用域,与XML中范围一致.

``` java
@Component("bean")	//通用注解,并指定注bean的名字
@Scope("prototype")					//作用范围,默认单例
public class BeanAnnotation {
	
	public void say(String arg) {
		System.out.println("使用注解 : " + arg);
	}
	
	/**通过注解,实现作用范围*/
	public void myHashCode() {
		System.out.println("使用注解 : " + this.hashCode());
	}
	
}
```
**测试类**
通过构造器,传入XML配置的注解扫描,扫描包下的所有类.
用过自定义的Bean名称获得实例,如果不指定,则为Bean类名的首字母小写
用过`@Scope`注解可以配置Bean实例的作用范围.

``` java
@RunWith(BlockJUnit4ClassRunner.class)
public class TestBeanAnnotation extends UnitTestBase {
	
	public TestBeanAnnotation() {
		super("classpath*:spring-beanannotation.xml");
	}
	
	/**通过在注解创建bean时指定名称*/
	@Test
	public void testSay() {
		//在创建bean的时候,在注解中指定bean的名字
		BeanAnnotation bean = super.getBean("bean");
		bean.say("这是测试.");
	}
	
	/**测试bean的作用范围*/
	@Test
	public void testScpoe() {			//bean的默认名称为首字母小写的类名
		BeanAnnotation bean = super.getBean("beanAnnotation");
		bean.myHashCode();
		
		bean = super.getBean("beanAnnotation");
		bean.myHashCode();
	}
	
}
```


### `@Required`和`@Service`和`@Autowired`注解
`@Required`注解适用于bean属性的setter方法
在这个注解仅仅表示,受影响的bean属性必须在配置时被填充,通过在bean定义或通过自动装配一个明确的属性值.
`@Autowired`可以放在`set()`方法,构造器或者成员变量之上,实现自动装配该对象
可以通过设置`required`属性为`false`避免
`@Repository`,注解在Dao层的实现类中,表示这是一个数据库映射对象
`@Service`,注解在Service层的实现类中,表示这是一个服务层对象

**模拟依赖注入关系**
- 模拟Dao层的接口

``` java
public interface InjectionDAO {
	
	public void save(String arg);
	
}
```

- 模拟Dao层的实现类
在实现类中配置注解`@Repository	`
``` java
@Repository		//Dao层的专用注解
public class InjectionDAOImpl implements InjectionDAO {
	
	public void save(String arg) {
		//模拟数据库保存操作
		System.out.println("保存数据：" + arg);
	}
}
```

- 模拟Service层的接口

``` java
public interface InjectionService {
	
	public void save(String arg);
	
}
```

- 模拟Service层的实现类
在实现类中配置注解`@Service`
set方法,私有属性,构造器中均可使用注解`@Autowired`
``` java
@Service	//Service层的专用注解
public class InjectionServiceImpl implements InjectionService {
	
	@Autowired	//可以注解在私有属性上
	private InjectionDAO injectionDAO;
	
//	@Autowired	//可以注解在构造器上
//	public InjectionServiceImpl(InjectionDAO injectionDAO) {
//		this.injectionDAO = injectionDAO;
//	}
	
//	@Autowired	//可以注解在set方法中
	public void setInjectionDAO(InjectionDAO injectionDAO) {
		this.injectionDAO = injectionDAO;
	}

	public void save(String arg) {
		//模拟业务操作
		System.out.println("Service接收参数：" + arg);
		arg = arg + ":" + this.hashCode();
		injectionDAO.save(arg);
	}
}
```

**测试依赖关系**

``` java
public class TestInjection extends UnitTestBase {
	
	public TestInjection() {
		super("classpath:spring-beanannotation.xml");
	}
	
	/**对自动注入进行验证*/
	@Test
	public void testAutowired() {
		InjectionService service = super.getBean("injectionServiceImpl");
		service.save("使用注解自动注入");
	}
}	
```

### `@Component`和`@Qualifier`注解
`@Component`注解适用于任何类,表示将该类托管交由Spring容器管理
`@Qualifier`注解,缩小自动装配时类的查找范围,可以传入类的名字或者匹配关键字查找,类似于`<Bean>`下的`<qualifier>`标签避免一个父类多个子类情况时由于装配失败而抛出异常.


**多态中,依赖注入的原则**
- 定义父接口
这里定义一个空类,作为父类,交由子类实现
``` java
public interface BeanInterface {

}
```

- 定义子类实现
使用`@Component`注解,将类托管给Spring容器

``` java
@Component		//万能的注解
public class BeanImplTwo implements BeanInterface {   }

@Component		//万能的注解
public class BeanImplOne implements BeanInterface {   }
```

- 使用父接口,指明具体子类泛型
引入父接口,由于`@Qualifier`注解,则使用指定的实现类.
``` java
@Component
public class BeanInvoker {
	@Autowired
	@Qualifier("beanImplTwo")	//通过@Qualifier注解,指定接口的实现类
	private BeanInterface beanInterface;
	
	public void say() {
		if (null != beanInterface) {
			System.out.println("通过@Qualifier注解,指定接口的实现类为:"+beanInterface.getClass().getName());
		} else {
			System.out.println("beanInterface 为空...");
		}	
	}
}
```

**测试多态中依赖注入**

``` java
@RunWith(BlockJUnit4ClassRunner.class)
public class TestMulti extends UnitTestBase {
	
	public TestInjection() {
		super("classpath:spring-beanannotation.xml");
	}
		
	/**对带有泛型的类实现对泛型的自动注入*/
	@Test
	public void testMultiBean() {
		BeanInvoker invoker = super.getBean("beanInvoker");
		invoker.say();
	}
	
}
```

### `@Order`注解
使用`@Order`注解可以在集合框架中实现排序,排序的优先级与注解值相关,数值越小,优先级越大

**集合框架中泛型的使用及其具体实现类**

- 定义父接口
这里定义一个空类,作为父类,交由子类实现
``` java
public interface BeanInterface {

}
```

- 定义子类实现
使用`@Component`注解,将类托管给Spring容器

``` java
@Component		//万能的注解
public class BeanImplTwo implements BeanInterface {   }

@Component		//万能的注解
public class BeanImplOne implements BeanInterface {   }
```

- 使用集合框架,泛型指定为父接口
Spring会自动识别接口的所有实现类,并按照`@Order`中的优先级进行排序.

``` java
@Component
public class BeanInvoker {
	
	@Autowired	//自动注解泛型中BeanInterface的类
	private List<BeanInterface> list;
	
	@Autowired
	private Map<String, BeanInterface> map;
	
	/**对自动注入的类进行遍历,由于默认为单例模式,所以只是注册了一个类类型*/
	public void say() {
		if (null != list && 0 != list.size()) {
			System.out.println("读取list中...");
			for (BeanInterface bean : list) {
				System.out.println(bean.getClass().getName());
			}
		} else {
			System.out.println("List<BeanInterface> list 为空 !!!!!!!!!!");
		}
		
		System.out.println("-------");
		
		if (null != map && 0 != map.size()) {
			System.out.println("读取map中...");
			for (Map.Entry<String, BeanInterface> entry : map.entrySet()) {
				System.out.println(entry.getKey() + "      " + entry.getValue().getClass().getName());
			}
		} else {
			System.out.println("Map<String, BeanInterface> map 为空 !!!!!!!!!!");
		}
	}
}
```

**测试集合框架中泛型的注入**
由于实现了`@Order`的顺序,会在容器中按照顺序进行排序

``` java
@RunWith(BlockJUnit4ClassRunner.class)
public class TestMulti extends UnitTestBase {

	public TestMulti() {
		super("classpath:spring-beanannotation.xml");
	}
	
	/**对带有泛型的类实现对泛型的自动注入*/
	@Test
	public void testMultiBean() {
		BeanInvoker invoker = super.getBean("beanInvoker");
		invoker.say();
	}
}
```


### 容器注解`@Configuration`,`@Bean`,`@ImportResource`,`@Value`

`@Configuration`将该类注解为容器类,可以在该类中配置Spring容器.
`@Bean`标注在方法上,表示该类的返回对象作为Spring容器的Bean,进行托管,可以在`name`属性中指定Bean的名字,默认为方法名作为Bean的名字,`initMethod`和`destroyMethod`分别指定Bean的初始化和销毁方法.
`@ImportResource`表示对XML资源文件的读取,通过`<context:property-placeholder>`标签指定属性文件的位置.
`@Value`获取配置文件中的属性值.
`@Scope`表示该类的作用范围,`proxyMode`属性可以配置类的代理方式

**类文件继承说明**


| 名称            | 类型       | 说明                                              |
| --------------- | ---------- | ------------------------------------------------- |
| `Store<T>`        | 接口       | 作为父接口                                        |
| `StringStore`     | 接口实现类 | 实现接口,并创建两个方法,配置为初始化,销毁方法     |
| `IntegerStore`    | 接口实现类 | 实现接口                                          |
| `StoreConfig`     | 容器类     | 被`@Configuration`注解配置,作为Spring容器         |
| `MyDriverManager` | 类         | 使用`@ImportResource`在容器类中获得配置文件的信息 |
| `TestJavabased`   | 测试类     | 对以上类的方法进行测试                            |


**Spring容器中Bean的托管**

- 创建Spring容器类,并提供返回Bean的方法

``` java
@Configuration	//在类上注解,表示将其配置为Spring容器进行配置,包括对Bean的生成和属性文件的读取
public class StoreConfig {
	
	/**使用@Bean,直接将方法返回的bean对象返回,完成IOC对象的获取.
	 * bean的名字为方法的名字**/
	@Bean
	public StringStore stringStore() {
		return new StringStore();
	}

}
```

- 测试获取`@Bean`注解中配置的Bean对象

``` java
@RunWith(BlockJUnit4ClassRunner.class)
public class TestJavabased extends UnitTestBase {
	
	public TestJavabased() {
		super("classpath:spring-beanannotation.xml");
	}
	
	/**测试,获取子类的名称接口Bean的实现,注意bean的名称的定义*/
	@Test
	public void test() {
		Store store = super.getBean("stringStore");
		System.out.println(store.getClass().getName());
	}
}
```

**Bean的作用范围及代理模式**

- `@Scope`注解中,通过`value`属性可以指定Bean的作用范围;通过`proxyMode`属性可以指定代理方式

``` java
@Configuration	//在类上注解,表示将其配置为Spring容器进行配置,包括对Bean的生成和属性文件的读取
public class StoreConfig {
	/**创建Bean并指定名字,
	 * 指定范围为请求IOC时创建新的对象,代理方式为类代理*/
	@Bean(name = "stringStore")
	@Scope(value="prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
	public Store stringStore() {
		return new StringStore();
	}
}
```

- 测试Bean的作用范围
通过对Bean的两次获取并打印对象的哈希,进而判断其是否为同一个对象.

``` java
@RunWith(BlockJUnit4ClassRunner.class)
public class TestJavabased extends UnitTestBase {
	
	public TestJavabased() {
		super("classpath:spring-beanannotation.xml");
	}
	
	/**测试,对Bean的范围和代理方式的测试**/
	@Test
	public void testScope() {
		Store store = super.getBean("stringStore");
		System.out.println(store.hashCode());
		
		store = super.getBean("stringStore");
		System.out.println(store.hashCode());
	}
}
```




**指定Bean的名字,并设定初始化,销毁方法**

- 在Bean中两个方法
``` java
public class StringStore implements Store<String> {
	
	public void init() {
		System.out.println("自定义初始化方法.");
	}
	
	public void destroy() {
		System.out.println("自定义销毁方法.");
	}
	
}
```

- 在`@Bean`注解中`name`属性指定Bean的名字,`initMethod`和`destroyMethod`分别指定初始化方法和销毁方法

``` java
@Configuration	//在类上注解,表示将其配置为Spring容器进行配置,包括对Bean的生成和属性文件的读取
public class StoreConfig {
	/**创建bean并指定名字,此时bean名称不与返回类一致
	 * 指定初始化方法和销毁方法均指定*/
	@Bean(name = "stringStore", initMethod="init", destroyMethod="destroy")
	public Store stringStore() {
		return new StringStore();
	}
}	
```


- 测试类进行测试

``` java
@RunWith(BlockJUnit4ClassRunner.class)
public class TestJavabased extends UnitTestBase {
	
	public TestJavabased() {
		super("classpath:spring-beanannotation.xml");
	}
	
	/**测试,获取子类的名称接口Bean的实现,注意bean的名称的定义*/
	@Test
	public void test() {
		Store store = super.getBean("stringStore");
		System.out.println(store.getClass().getName());
	}
}
```

**资源文件的获取**

- 创建需要资源文件中信息的对象

``` java
public class MyDriverManager {
	public MyDriverManager(String url, String userName, String password) {
		//验证方法
		System.out.println("读取配置文件中url : " + url);
		System.out.println("读取配置文件中userName: " + userName);
		System.out.println("读取配置文件中password: " + password);
	}
}
```

- `@ImportResource`注解引入资源文件
使用`@ImportResource`引入配置文件的位置,交由Spring容器进行加载
使用`@Value`注解对属性文件中的键进行值的匹配,通过私有属性成员,绑定配置文件中属性值

``` java
@Configuration	//在类上注解,表示将其配置为Spring容器进行配置,包括对Bean的生成和属性文件的读取
@ImportResource("classpath:config.xml")	//在配置类中,引入对xml文件的读取,进而获取xml的引用的属性文件获取
public class StoreConfig {
	
	
	/**获取配置文件的值,使用${key}来获取文件中的值**/
	@Value("${url}")
	private String url;
	
	@Value("${jdbc.username}")	//注意名字为.风格,确保key的唯一性,而不会获取到系统的预设变量
	private String username;
	
	@Value("${password}")
	private String password;
	
	/**自定义的类,用来使用配置文件中的值,在构造器中有打印验证*/
	@Bean
	public MyDriverManager myDriverManager() {
		return new MyDriverManager(url, username, password);
	}
}
```

- XML中的配置及指向的属性文件

XML文件中引入配置文件
``` xml
<beans>
    <!-- 作为xml,被Bean通过注解注入.xml中配置引用属性文件的地址 -->    
    <context:property-placeholder location="classpath:/config.properties"/>
</beans>
```

配置文件的内容
```
jdbc.username=root
password=root
url=127.0.0.1
```

- 测试对资源文件的读取

``` java
@RunWith(BlockJUnit4ClassRunner.class)
public class TestJavabased extends UnitTestBase {
	
	public TestJavabased() {
		super("classpath:spring-beanannotation.xml");
	}
	
	/**测试,对配置文件中的属性值的获取**/
	@Test
	public void testMyDriverManager() {
		MyDriverManager manager = super.getBean("myDriverManager");
		System.out.println("读取配置文件成功,创建自定义JDBC连接:"+manager.getClass().getName());
	}
}

```

**基于泛型的自动装配**
通过使用`@Bean`注解,为容器中注入Bean实例
使用`@Autowire`注解泛型进行装配
对测试Bean进行注入,返回测试.

``` java
@Configuration	//在类上注解,表示将其配置为Spring容器进行配置,包括对Bean的生成和属性文件的读取
public class StoreConfig {
	/**对String类型实现自动装配**/
	@Bean
	public StringStore stringStore() {
		return new StringStore();
	}
	
	/**对Integer类型实现自动装配**/
	@Bean
	public IntegerStore integerStore() {
		return new IntegerStore();
	}
	
	/**容器,存入String类型**/
	@Autowired
	private Store<String> s1;
	
	/**容器,存入Integer类型**/
	@Autowired
	private Store<Integer> s2;
	
	/**对同一个接口的不同实现类的进行测试**/
	@Bean(name = "stringStoreTest")
	public Store stringStoreTest() {
		System.out.println("s1 的类名: " + s1.getClass().getName());
		System.out.println("s2 的类名: " + s2.getClass().getName());
		return new StringStore();
	}
}
```

- 测试类,对泛型装配进行测试

``` java
@RunWith(BlockJUnit4ClassRunner.class)
public class TestJavabased extends UnitTestBase {
	public TestJavabased() {
		super("classpath:spring-beanannotation.xml");
	}
	
	/**测试,对store接口容器的不同实现类进行验证**/
	@Test
	public void testG() {
		StringStore store = super.getBean("stringStoreTest");
	}
}
```

### 其他注解`@Resource`,`@Inject`,`@Named`,`@PostConstruct`,`@PreDestroy`

`@Repository`注解,专门用于注解Dao层
`@Service`注解,专门用户注解Service层
`@Name`注解,显式给类提供Bean名,可以注解在类名上,成员方法上,方法参数中,默认值或缺省值为类名首字符小写
`@Resource`将依赖属性注入,在Set方法上注解,提供name值作为Bean的名称
`@Inject`将依赖属性注入,与`@autoWire`效果相同.
`@PostConstruct`本类中的初始化方法
`@PreDestroy`本类中的销毁方法


- 模拟Dao层

``` java
@Repository		//Dao层的接口
public class JsrDAO {
	public void save() {
		System.out.println("Dao层保存数据.");
	}
}
```

- 默认Service层

``` java
@Service
//@Named
public class JsrServie {
	
//	@Inject		//autoWire相等
	@Resource	//私有属性注入实例,指定其生命周期,也可以使用autoWire
	private JsrDAO jsrDAO;
	
//	@Inject		//autoWire相等
//	@Resource	//set方法注入实例,指定其生命周期,也可以使用autoWire
	//@name注解指定类名
	public void setJsrDAO(@Named("jsrDAO") JsrDAO jsrDAO) {
		this.jsrDAO = jsrDAO;	
	}
	
	@PostConstruct	
	public void init() {
		System.out.println("初始化注解.");
	}
	
	@PreDestroy
	public void destroy() {
		System.out.println("销毁注解");
	}

	/**调用保存的方法**/
	public void save() {
		jsrDAO.save();
	}
	
}
```

- 测试

``` java
@RunWith(BlockJUnit4ClassRunner.class)
public class TestJsr extends UnitTestBase {
	public TestJsr() {
		super("classpath:spring-beanannotation.xml");
	}

	@Test
	public void testSave() {
		JsrServie service = getBean("jsrServie");
		service.save();
	}
}
```

## AOP面向切面编程

面向切面编程是指通过预编译的方式结合运行期代理实现程序统一维护的技术.


| 名称                    | 说明                                                                                                  | 举例                                        |
| ----------------------- | ----------------------------------------------------------------------------------------------------- | ------------------------------------------- |
| 切面(Aspect)            | 一个关注点的模块化,这个关注点会横切多个对象                                                           | 多个模块间通用的服务                        |
| 连接点(Joinpoint)       | 程序执行过程的某个特定的点                                                                            | 具体的方法                                  |
| 通知(Advice)            | 在切面的某个特定连接点上执行的动作                                                                    | 在具体方法执行时,指定的额外功能             |
| 切入点(Pointcut)        | 匹配连接点的断言,在AOP中通知和一个切入点表达式关联                                                    | 匹配链接点的表达式                          |
| 引入(Introduction)      | 在不修改类代码的前提下,为类添加新的方法和属性                                                         | 编译期的动态增加新功能                      |
| 目标对象(Target Object) | 被一个或者多个切面所通知的对象                                                                        | 被关注的模块功能                            |
| AOP代理(AOP Proxy)      | AOP框架创建的对象,用来实现切面契约( aspect  contract)                                                 | 通知方法执行的对象,由框架代理               |
| 织入(Weaving)           | 把切面连接到其他应用程序类型或者对象上,并创建一个被通知的对象.分为:编译时织入,类加载时织入,执行时织入 | 将切面和对象关联起来,在时间点上进行通知操作 |

通知类型

| 名称                                  | 说明                                                                             |
| ------------------------------------- | -------------------------------------------------------------------------------- |
| 前置通知(Before advice)               | 在某个连接点(Join point)之前执行的通知,但是它不能阻止连接点前的执行,除非抛出异常 |
| 返回后通知(After returning advice)    | 在某个连接点(Join point)正常执行完成后的通知                                     |
| 抛出异常后通知(After throwing advice) | 在方法抛出异常退出时执行的通知                                                   |
| 后通知(After advice)                  | 当某个连接点退出的时候执行的通知(无论正常返回还是异常退出)                       |
| 环绕通知(Around Advice)               | 包围一个连接点(Join point)的通知                                                 |

### 传统方式实现切面编程
通过实现各种接口,完成对切面编程的实现.

**定义要横切的方法**

- 创建接口,定义方法

``` java
public interface BizLogic {
	
	String save();

}
```

- 创建接口实现类

``` java
public class BizLogicImpl implements BizLogic {
	public String save() {
		System.out.println("正在进行保存.....");
		return "返回处理结果为.....";
	}
}
```

**定义各种通知**

- 前置通知

``` java
public class MoocBeforeAdvice implements MethodBeforeAdvice {
	@Override
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		System.out.println("前置通知使用 : " + method.getName() + "     " + 
				 target.getClass().getName());
	}
}
```

- 后置通知

``` java
public class MoocAfterReturningAdvice implements AfterReturningAdvice {
	@Override
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		System.out.println("后置通知....." + method.getName() + "     " + 
			target.getClass().getName() + "       " + returnValue);
	}
}
```

- 环绕通知

``` java
public class MoocMethodInterceptor implements MethodInterceptor {
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("方法执行前进行环绕通知..........: " + invocation.getMethod().getName() + "     " + 
				invocation.getStaticPart().getClass().getName());
		 Object obj = invocation.proceed();
		 System.out.println("方法执行后进行环绕通知..........: " + obj);
		 return obj;
	}

}
```

- 异常通知

``` java
public class MoocThrowsAdvice implements ThrowsAdvice {
	public void afterThrowing(Exception ex) throws Throwable {
		System.out.println("异常处理通知......");
	}
	public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable {
		System.out.println("异常处理中.....参数为:" + method.getName() + "       " + 
				target.getClass().getName());
	}
}
```

**XML中配置通知**
1. 配置各通知的Bean
2. 创建目标类,指向接口的实现类
3. 创建切点类,指定横切的方法
4. 将切点和某个通知进行织入,够成横切面
5. 也可以通过代理的方式,为多个通知构成在同一个切点中多次使用
``` xml
<beans>
        
     <!-- 创建实现接口的通知类 -->   
     <bean id="moocBeforeAdvice" class="aop.api.MoocBeforeAdvice"></bean>
     
     <bean id="moocAfterReturningAdvice" class="aop.api.MoocAfterReturningAdvice"></bean>
     
     <bean id="moocMethodInterceptor" class="aop.api.MoocMethodInterceptor"></bean>
     
     <bean id="moocThrowsAdvice" class="aop.api.MoocThrowsAdvice"></bean>
     
     
    
    <!-- 处理方式 --> 
    <bean id="bizLogicImplTarget" class="aop.api.BizLogicImpl"></bean>

	<bean id="pointcutBean" class="org.springframework.aop.support.NameMatchMethodPointcut">
		<property name="mappedNames">
			<list>
				<value>sa*</value>
			</list>
		</property>
	</bean>
	
 	<bean id="defaultAdvisor" class="org.springframework.aop.support.DefaultPointcutAdvisor">
		<property name="advice" ref="moocBeforeAdvice" />
		<property name="pointcut" ref="pointcutBean" />
	</bean>
	
	<bean id="bizLogicImpl" class="org.springframework.aop.framework.ProxyFactoryBean">
		<property name="target">
			<ref bean="bizLogicImplTarget"/>
		</property>
		<property name="interceptorNames">
			<list>
				<value>defaultAdvisor</value>
				<value>moocAfterReturningAdvice</value>
				<value>moocMethodInterceptor</value>
				<value>moocThrowsAdvice</value>
			</list>
		</property>
	</bean>
 </beans>
```

**使用代理类配置通知**

``` xml
<beans>
        
     <!-- 创建实现接口的通知类 -->   
     <bean id="moocBeforeAdvice" class="aop.api.MoocBeforeAdvice"></bean>
     
     <bean id="moocAfterReturningAdvice" class="aop.api.MoocAfterReturningAdvice"></bean>
     
     <bean id="moocMethodInterceptor" class="aop.api.MoocMethodInterceptor"></bean>
     
     <bean id="moocThrowsAdvice" class="aop.api.MoocThrowsAdvice"></bean>

	<!-- 创建代理类 -->    
	<bean id="bizLogicImplTarget" class="aop.api.BizLogicImpl"></bean>

	<bean id="bizLogicImpl" class="org.springframework.aop.framework.ProxyFactoryBean">
		<property name="proxyInterfaces">
			<value>aop.api.BizLogic</value>
		</property>
		<property name="target">
<!-- 			<bean class="aop.api.BizLogicImpl"/> -->
			<ref bean="bizLogicImplTarget"/>
		</property>
		<property name="interceptorNames">
			<list>
				<value>moocBeforeAdvice</value>
				<value>moocAfterReturningAdvice</value>
				<value>moocMethodInterceptor</value>
				<value>moocThrowsAdvice</value>
				<value>mooc*</value>
			</list>
		</property>
	</bean>
 </beans>
```

**使用代理类配置通知**

``` xml
<beans>
     <!-- 创建实现接口的通知类 -->   
     <bean id="moocBeforeAdvice" class="aop.api.MoocBeforeAdvice"></bean>
     
     <bean id="moocAfterReturningAdvice" class="aop.api.MoocAfterReturningAdvice"></bean>
     
     <bean id="moocMethodInterceptor" class="aop.api.MoocMethodInterceptor"></bean>
     
     <bean id="moocThrowsAdvice" class="aop.api.MoocThrowsAdvice"></bean>
			lazy-init="true" abstract="true"></bean>
	
	<bean id="bizLogicImpl"  parent="baseProxyBean">
		<property name="target">
			<bean class="aop.api.BizLogicImpl"></bean>
		</property>
		<property name="proxyInterfaces">
			<value>aop.api.BizLogic</value>
		</property>
		<property name="interceptorNames">
			<list>
				<value>moocBeforeAdvice</value>
				<value>moocAfterReturningAdvice</value>
				<value>moocMethodInterceptor</value>
				<value>moocThrowsAdvice</value>
			</list>
		</property>
	</bean>
 </beans>
```


**测试通知**

``` java
@RunWith(BlockJUnit4ClassRunner.class)
public class TestAOPAPI extends UnitTestBase {
	public TestAOPAPI() {
		super("classpath:spring-aop-api.xml");
	}
	@Test
	public void testSave() {
		BizLogic logic = (BizLogic)super.getBean("bizLogicImpl");
		logic.save();
	}
}
```


### 使用XML配置切面编程

**常用的切点表达式**

| 表达式                                     | 解释                                |
| ------------------------------------------ | ----------------------------------- |
| `execution(public * *(..))`                  | 切点为执行所有的public方法时        |
| `execution(* set*(..))`                      | 执行所有set开始方法时               |
| `execution(* service.EmployeeService.*(..))` | 执行EmployeeService类下的所有方法时 |
| `execution(* service..(..))`                 | 执行service包下的所有类方法时       |
| `execution(* service...(..))`                | 执行service包及其子包下的所有方法   |


- 声明代理类,及其实现,作为类型转换的类

``` java
public interface Fit {
	void filter();
}

public class FitImpl implements Fit {
	@Override
	public void filter() {
		System.out.println("接口实现通知代理.");
	}
}
```

- 创建业务类,调用无参或者有参的方法,对其进行通知的触发

``` java
public class AspectBiz {
	public void biz() {
		System.out.println("业务正在进行.");
//		throw new RuntimeException();		//抛出异常,调用异常通知.而后置通知不再通知
	}
	public void init(String bizName, int times) {
		System.out.println("业务中传入参数: " + bizName + "   " + times);
	}
}
```

- 创建各种通知方法
其中传入参数对象`ProceedingJoinPoint`表示环绕通知的时候进行代理的对象.
``` java
public class MoocAspect {
	
	public void before() {
		System.out.println("在执行方法前,进行前置通知.");
	}
	
	public void afterReturning() {
		System.out.println("在执行前方法后,进行后置通知.");
	}
	
	public void afterThrowing() {
		System.out.println("抛出异常了,进行通知.");
	}
	
	public void after() {
		System.out.println("在最后一步前,进行通知");
	}
	
	/**环绕通知一般使用*/
	public Object around(ProceedingJoinPoint pjp) {
		Object obj = null;
		try {
			System.out.println("在类中所有方法执行前,进行环绕通知.");
			obj = pjp.proceed();
			System.out.println("在类中所有方法执行后,进行环绕通知.");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	/**匹配参数的环绕通知,针对特定参数列表进行匹配**/
	public Object aroundInit(ProceedingJoinPoint pjp, String bizName, int times) {
		System.out.println(bizName + "   " + times);
		Object obj = null;
		try {
			System.out.println("获得环绕通知传入参数.");
			obj = pjp.proceed();
			System.out.println("程序执行后,返回参数.");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return obj;
	}
}
```

- 配置XML

``` xml
<beans>

	<!-- 使用XML。对切面编程进行配置 -->
	<!-- 第一种方式,通过指定ID绑定基本类的AOP切面编程 -->
	<!-- id:唯一标示,标示切面方法	class:切面具方法 -->
	<bean id="moocAspect" class="aop.schema.advice.MoocAspect"></bean>
	
	<bean id="aspectBiz" class="aop.schema.advice.biz.AspectBiz"></bean>
	
	<!-- 可以有多个,表示一组aop配置 -->
	<aop:config>
		<!-- 准备进行的AOP配置.id:唯一名称 	ref:与切面方法的类一致 -->
		<aop:aspect id="moocAspectAOP" ref="moocAspect">
			<!-- 切点的配置,没有指定类型 	id:切入点的名字	expression:表达式,对这个包下的所有类进行	 -->	
			<aop:pointcut id="moocPiontcut" expression="execution(* aop.schema.advice.biz.*Biz.*(..))"/>
			<!-- 前置通知 	method:类型	pointcut-ref:切点的映射 -->
 			<aop:before method="before" pointcut-ref="moocPiontcut"/>
 			<!-- 后置通知 -->
 			<aop:after-returning method="afterReturning" pointcut-ref="moocPiontcut"/>
 			<!-- 抛出异常的方法 -->
 			<aop:after-throwing method="afterThrowing" pointcut-ref="moocPiontcut"/>
 			<!-- 最终通知 -->
 			<aop:after method="after" pointcut-ref="moocPiontcut"/>
 			<!-- 环绕通知 -->
 			<aop:around method="around" pointcut-ref="moocPiontcut"/>
			<!-- 环绕通知,使用参数 -->
			<aop:around method="aroundInit" pointcut="execution(* aop.schema.advice.biz.AspectBiz.init(String, int))
							and args(bizName, times)"/>
			<!-- 类型转化的通知,实现直接接口实现实现类,配置文件的通知只支持单例模式-->
			<aop:declare-parents types-matching="aop.schema.advice.biz.*(+)" 
					implement-interface="aop.schema.advice.Fit"
					default-impl="aop.schema.advice.FitImpl"/>
		</aop:aspect>
	</aop:config>
 </beans>
```

- 测试类

``` java
@RunWith(BlockJUnit4ClassRunner.class)
public class TestAOPSchemaAdvice extends UnitTestBase {
	
	public TestAOPSchemaAdvice() {
		super("classpath:spring-aop-schema-advice.xml");
	}
	
	/**一般的通知测试**/
	@Test
	public void testBiz() {
		AspectBiz biz = super.getBean("aspectBiz");
		biz.biz();	//模拟业务方法
	}
	
	/**带参数的环绕通知的测试**/
	@Test
	public void testInit() {
		AspectBiz biz = super.getBean("aspectBiz");
		biz.init("moocService", 3);
	}
	
	/**对其他类强制转换为一个不想关的类,并代表原来的对象**/
	@Test
	public void testFit() {
		Fit fit = (Fit)super.getBean("aspectBiz");
		fit.filter();
	}
}
```

### 统计错误执行的次数,并返回结束

- 正确/错误执行,被通知统计发生错误的次数,终止返回

``` java
@Service
public class InvokeService {
	
	public void invoke() {
		System.out.println("业务正在执行 ......");
	}
	
	public void invokeException() {
		throw new PessimisticLockingFailureException("主动抛出异常");
	}
}
```

- 声明接口类

``` java
@Retention(RetentionPolicy.RUNTIME)
public @interface Idempotent {
    // marker annotation
}
```

- 自定义异常,统计次数

``` java
public class ConcurrentOperationExecutor implements Ordered {

	private static final int DEFAULT_MAX_RETRIES = 2;

	private int maxRetries = DEFAULT_MAX_RETRIES;
	
	private int order = 1;	

	public void setMaxRetries(int maxRetries) {		//从XML文件中获得参数
		this.maxRetries = maxRetries;
	}

	public int getOrder() {
		return this.order;
	}

	public void setOrder(int order) {				//从配置文件中获得参数
		this.order = order;
	}

	/**自定义异常,统计重置次数*/
	public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable {
		int numAttempts = 0;		//记录次数
		PessimisticLockingFailureException lockFailureException;
		do {
			numAttempts++;
			System.out.println("重试次数 : " + numAttempts);
			try {
				return pjp.proceed();
			} catch (PessimisticLockingFailureException ex) {
				lockFailureException = ex;
			}
		} while (numAttempts <= this.maxRetries);
		System.out.println("发生错误 : " + numAttempts);
		throw lockFailureException;
	}
}
```

- XML配置文件

``` xml
<beans>
	<!-- 使用 -->
	<!-- 配置包扫描 -->
	<context:component-scan base-package="aop.schema"></context:component-scan>

	<!-- 配置AOP切面 -->
	<aop:config>
		<!-- 创建切点,指定切点处理类 -->
		<aop:aspect id="concurrentOperationRetry" ref="concurrentOperationExecutor">
			<!-- 织入,唯一ID后,使用匹配表达式 -->
			<aop:pointcut id="idempotentOperation"
				expression="execution(* aop.schema.advisors.service.*.*(..)) " />
<!--      			expression="execution(* aop.schema.service.*.*(..)) and -->
<!--         						@annotation(aop.schema.Idempotent)" /> -->
			<!-- 环绕通知 -->
			<aop:around pointcut-ref="idempotentOperation" method="doConcurrentOperation" />
		</aop:aspect>
	</aop:config>
	
	<!-- 切点处理类 -->
	<bean id="concurrentOperationExecutor" class="aop.schema.advisors.ConcurrentOperationExecutor">
		<!-- 最大重置顺序 -->
		<property name="maxRetries" value="3" />
		<!-- 排序 -->
		<property name="order" value="100" />
	</bean>
 </beans>
```

- 测试类

``` java
@RunWith(BlockJUnit4ClassRunner.class)
public class TestAOPSchemaAdvisors extends UnitTestBase {
	
	public TestAOPSchemaAdvisors() {
		super("classpath:spring-aop-schema-advisors.xml");
	}
	
	@Test
	public void testSave() {
		InvokeService service = super.getBean("invokeService");
		service.invoke();		//正确执行
		
		System.out.println();	//错误执行
		service.invokeException();
 	}

}
```



### 使用注解配置切面编程

切点表达式常用参数

| 参数        | 说明                                                   |
| ----------- | ------------------------------------------------------ |
| execution   | 匹配方法执行的连接点                                   |
| within      | 限定匹配特定类型的连接点                               |
| this        | 匹配特定连接点的Bean引用是指定类型的实例的限制         |
| target      | 限定匹配特定连接点的目标对象是指定类型的实例           |
| args        | 限定匹配特定连接点的参数是给定类型的实例               |
| @target     | 限定匹配特定连接点的类执行对象的具有给定类型的注解     |
| @args       | 限定匹配特定连接点实际传入参数的类型具有给定类型的注解 |
| @within     | 限定匹配到内具有给定的注释类型的连接点                 |
| @annotation | 限定匹配特定连接点的主体具有给定的注解                 |


- 开启包扫描

``` xml
<beans>
        <!-- 使用aspectj注解方式完成切面编程 -->
        <!-- 扫描包路径 -->
        <context:component-scan base-package="aop.aspectj"/>
     	<!-- 开启 -->
     	<aop:aspectj-autoproxy/>
 </beans>
```


- 创建切面类

``` java
@Service	//作为服务层,被切面横切
public class MoocBiz {
	
	@MoocMethod("传入参数....")	//指定传入参数的值
	public String save(String arg) {
		System.out.println("业务保存参数 : " + arg);
//		throw new RuntimeException("保存失败...");
		return " 保存成功!";
	}
}
```

- 创建接口类

``` java
@Retention(RetentionPolicy.RUNTIME)	//运行时注解
@Target(ElementType.METHOD)		
public @interface MoocMethod {
	
	String value();

}
```

- 创建通知

``` java
@Component	//通用bean的注入
@Aspect		//将其转为切面类,不进行代理
public class MoocAspect {
	
	//接入点的配置,指定其扫描包路径
	@Pointcut("execution(* aop.aspectj.biz.*Biz.*(..))")
	public void pointcut() {}
	
	//切入点的配置,确定连接的范围
	@Pointcut("within(aop.aspectj.biz.*)")
	public void bizPointcut() {}
	
	//前置切入点
	@Before("pointcut()")
	public void before() {
		System.out.println("前置通知开始....");
	}
	
	@Before("pointcut() && args(arg)")		//前置通知,并传入参数arg,参数名和方法中的一致
	public void beforeWithParam(String arg) {
		System.out.println("前置通知并传入参数:" + arg);
	}
	
	@Before("pointcut() && @annotation(moocMethod)")	//传入参数,参数的注解的值和方法中的对象名一致
	public void beforeWithAnnotaion(MoocMethod moocMethod) {
		System.out.println("前置通知,并用注解获取方式:" + moocMethod.value());
	}
	
	//后置通知
	@AfterReturning(pointcut="bizPointcut()", returning="returnValue")	//后置通知,并有返回值
	public void afterReturning(Object returnValue) {
		System.out.println("后置通知,收到参数 : " + returnValue);
	}
	
	//抛出异常后的通知
	@AfterThrowing(pointcut="pointcut()", throwing="e")	//抛出异常,并传递异常参数
	public void afterThrowing(RuntimeException e) {
		System.out.println("抛出异常通知 : " + e.getMessage());
	}
	
	//最终通知
	@After("pointcut()")
	public void after() {
		System.out.println("最终通知开始......");
	}

	//环绕通知
	@Around("pointcut()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("环绕通知在方法执行前.");
		Object obj = pjp.proceed();
		System.out.println("环绕通知在方法执行后.");
		System.out.println("环绕通知在方法执行是返回对象为: " + obj);
		return obj;
	}
}
```

- 测试类

``` java
@RunWith(BlockJUnit4ClassRunner.class)
public class TestAspectJ extends UnitTestBase {
	
	public TestAspectJ() {
		super("classpath:spring-aop-aspectj.xml");
	}
	
	@Test
	public void test() {
		MoocBiz biz = getBean("moocBiz");
		biz.save("通过注解保存这个参数.");
	}
}
```

# Spring Data框架
## 简介
Spring-Data是Spring对各种数据库的接口定义,简化了数据访问层的代码量.
- Spring Data JPA 对关系型数据库
- Spring Data Mongo DB  对非结构文档数据库
- Spring Data Redis  缓存数据库
- Spring Data Solr 文本搜索数据库


## 基础信息
### `jdbc.properties`属性文件

``` 
jdbc.username=root
jdbc.password=123456
jdbc.url=jdbc\:mysql\://127.0.0.1\:3306/spring
jdbc.driverClass=com.mysql.jdbc.Driver
```

### `Student`学生类创建

``` java
public class Student {

	private int id;
	
	private String name;
	
	private int age;
	
	private String address;
	
	private Date date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", age=" + age + ", address=" + address + ", date=" + date
				+ "]";
	}
}
```


### `spring-data`配置文件创建

## JDBCTemplate方式访问数据库
### 编码

### 测试

## Spring-Data-JPA方式访问数据库
