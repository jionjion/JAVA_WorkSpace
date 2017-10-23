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



## IOC控制反转与DI依赖注入
### IOC控制反转
控制权的转移,应用程序不负责依赖对象的创建和维护,而由外部容器负责创建和维护.

### DI依赖注入
是对控制反转的一种实现,负责创建对象并维护对象之间的关系.
