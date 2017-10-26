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
		- `autowiring`  依赖注入的实现方式
		- `aware` 对应资源获取
		- `bean`  Bean作用范围
		- `beanannotation`
			- `injection` Bean注解实现依赖注入
			- `javabased`
			- `jsr`
			- `multibean`
			- `BeanAnnotation`  Bean的注解配置
		- `ioc`
		- `lifecycle` Bean生命周期
		- `resource` 获得资源文件
	- `resource`各种功能对应的配置文件
		- `config.properties`数据库配置文件
		- `config.txt`
		-  `config.xml`
		-  `jdbc.properties`
		-  `spring-aop-api.xml`
		-  `spring-aop-aspectj.xml`
		-  `spring-aop-schema-advice.xml`
		-  `spring-aop-schema-advisors.xml`
		-  `spring-autowiring.xml`  依赖注入的配置文件
		-  `spring-aware.xml` 对应资源获取的配置文件
		-  `spring-beanannotation.xml`
		-  `spring-beanscope.xml` Bean作用范围的配置文件
		-  `spring-injection.xml`
		-  `spring-ioc.xml`
		-  `spring-lifecycle.xml`  Bean生命周期的配置文件
		-  `spring-resource.xml` 获得资源文件的配置文件
- `test` 对应的测试类
	- `java` 各种功能测试类
		- `aop` 
		- `autowiring` 依赖注入的测试
		- `aware` 对应资源获取的测试
		- `base` 编写基础的测试类,便于其他测试类继承使用
		- `bean` Bean作用范围的测试
		- `beanannotation`
			- `TestBeanAnnotation`  Bean的注解配置的测试
			- `TestInjection`Bean注解实现依赖注入的测试和泛型装配的测试
			- `TestJavabased`
			- `TestJsr`
		- `ioc`
		- `lifecycle`  Bean生命周期的测试
		- `resource`  获得资源文件的测试
	- `resource`各种功能对应的配置文件的测试类


# 各功能实现

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
|                          |          |                                                |
|                          |          |                                                |
|                          |          |                                                |
|                          |          |                                                |


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

## `@Component`和`@Order`注解
`@Component`注解适用于任何类,表示将该类托管交由Spring容器管理
`@Order`注解表示该类在依赖注入时的优先等级


**多态中,依赖注入的原则**
- 定义父接口
这里定义一个空类,作为父类,交由子类实现
``` java
public interface BeanInterface {

}
```

- 定义子类实现
使用`@Order(1)`注解,表示该类为第一优先子类实现,优先依赖注入
使用`@Component`注解,将类托管给Spring容器

``` java
@Order(1)		//排序为1,传入整形数字
@Component		//万能的注解
public class BeanImplTwo implements BeanInterface {   }

@Order(2)		//排序为1,传入整形数字
@Component		//万能的注解
public class BeanImplOne implements BeanInterface {   }
```
