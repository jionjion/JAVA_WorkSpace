# Spring+Struts+ Hibernate的项目介绍

Tags : JDK8 Eclipse Tomcat7

---

[TOC]

---

## 简介
使用SpringMVC结合Hibernate,完成基本的框架的搭建,项目部署在Tomcat7中,编译使用JDK8.
演练模块以一个部门和员工的简单的增删改查作为实例.



## 框架的搭建
整体采用`web`动态项目,通过引入jar包和`web-xml`配置,实现`Struts2`的搭建.

## 包结构

* `action`                    控制层
* `bean`                      数据库映射层
* `dao`                        拦截器层
* `service`                   各种配置
* `applicationContext.xml`  Spring的配置文件
* `jdbc.properties`       数据库配置文件
* `log4j.properties`      日志配置文件
* `struts.xml`               Struts配置文件
* `WebContent\jsps`    JSP页面存放
* `WebContent\WEB-INF` 存放Jar包及`Web.xml`文件

## 框架搭建

### `web-xml`配置
- 配置Spring的监听器,设定路径参数,在Servlet容器加载时优先加载监听器
- 配置Struts2的过滤器,将所有URL请求进行过滤
- 配置Hibernate的延时过滤器,使Session在视图层开启

``` xml
  <!-- Spring的核心监听器,并指定contextConfigLocation常量所代表的配置文件的位置 -->
  <listener>
  	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>  
  <context-param>
  	<param-name>contextConfigLocation</param-name>
  	<param-value>classpath:applicationContext.xml</param-value>
  </context-param>
  
  
  <!-- Struts2的核心过滤器的配置 -->
  <filter>
  	<filter-name>Struts2</filter-name>
  	<filter-class>org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter</filter-class>
  </filter>
  <filter-mapping>
  	<filter-name>Struts2</filter-name>
  	<url-pattern>/*</url-pattern>
  </filter-mapping>
  
  <!-- Hibernate的延时加载的过滤器,Session在视图层开启 -->
  <filter>
  	<filter-name>OpenSessionInViewFilter</filter-name>
  	<filter-class>org.springframework.orm.hibernate3.support.OpenSessionInViewFilter</filter-class>
  </filter>
  <filter-mapping>
  	<filter-name>OpenSessionInViewFilter</filter-name>
  	<url-pattern>*.action</url-pattern>
  </filter-mapping>
```

### 创建Spring的`applicationContext.xml`配置文件
Spring默认在`src`目录下查找`applicationContext.xml`作为其默认的配置文件.
在配置文件中,我们可以
- 通过引入外部属性文件,创建数据库连接池
- 使用连接池作为数据源,初始化Hibernate,并配置其`*.hbm.xml`的映射路径
- 根据`dao`包,`service`包,`action`之间的依赖关系,创建对应的bean配置
- 开启事物管理器
- 开启注解事务

``` xml
<!-- 尝试单元测试,检查框是否完成 -->
<bean id="date" class="java.util.Date"/>

<!-- 引入外部属性文件 -->
<context:property-placeholder location="classpath:jdbc.properties"/>
<!-- 配置连接池 -->
<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
	<property name="driverClass" value="${jdbc.driverClass}"/>
	<property name="jdbcUrl" value="${jdbc.url}"/>
	<property name="user" value="${jdbc.username}"/>
	<property name="password" value="${jdbc.password}"/>
</bean>

<!-- 配置Hibernate的属性 -->
<bean id="sessionFactory" class="org.springframework.orm.hibernate3.LocalSessionFactoryBean">
	<!-- 连接池信息 -->
	<property name="dataSource" ref="dataSource"/>
	<!-- Hibernate的属性,注意前缀必须写全 -->
	<property name="hibernateProperties">
		<props>
			<prop key="hibernate.dialect">org.hibernate.dialect.MySQLDialect</prop>
			<prop key="hibernate.show_sql">true</prop>
			<prop key="hibernate.format_sql">true</prop>
			<prop key="hibernate.hbm2ddl.auto">update</prop>
		</props>
	</property>
	<!-- Hibernate的映射文件 -->
	<property name="mappingResources">
		<list>
			<value>bean/Product.hbm.xml</value>
			<value>bean/Employee.hbm.xml</value>
			<value>bean/Department.hbm.xml</value>
		</list>
	</property>
</bean>

<!-- 配置控制层,使用多例模式 -->
<bean id="productAction" class="action.ProductAction" scope="prototype">
	<property name="productService" ref="productService"/>
</bean>
<bean id="employeeAction" class="action.EmployeeAction" scope="prototype">
	<property name="employeeService" ref="employeeService"/>
	<property name="departmentService" ref="departmentService"/>
</bean>
<bean id="departmentAction" class="action.DepartmentAction" scope="prototype">
	<property name="departmentService" ref="departmentService"/>
</bean>


<!-- 配置业务的类 -->
<bean id="productService" class="service.ProductService">
	<property name="productDao" ref="productDao"/>
</bean>
<bean id="employeeService" class="service.imp.EmployeeServiceImp">
	<property name="employeeDao" ref="employeeDao"/>
</bean>
<bean id="departmentService" class="service.imp.DepartmentServiceImp">
	<property name="departmentDao" ref="departmentDao"></property>
</bean>

<!-- 配置数据持久化的类 -->
<bean id="productDao" class="dao.ProductDao">
	<property name="sessionFactory" ref="sessionFactory"></property>
</bean>
<bean id="employeeDao" class="dao.imp.EmployeeDaoImp">
	<property name="sessionFactory" ref="sessionFactory"/>
</bean>
<bean id="departmentDao" class="dao.imp.DepartmentDaoImp">
	<property name="sessionFactory" ref="sessionFactory"/>
</bean>

<!-- 事物管理器 -->
<bean id="transactionManager" class="org.springframework.orm.hibernate3.HibernateTransactionManager">
	<property name="sessionFactory" ref="sessionFactory"/>
</bean>
<!-- 开启注解事物 -->
<tx:annotation-driven transaction-manager="transactionManager"/>
```

### 创建Struts2的`struts.xml`配置文件

默认在`src`目录下创建`struts.xml`作为Struts2的配置文件
将Struts2托管有两种方式
- Struts2自身管理
	1. 在`struts.xml`中,配置`<action>`的`class`属性为类全路径,不需要在Spring的`applicationContext.xml`中配置action包的`<bean>`标签.

- 交由Spring管理
	1. 在Spring的`applicationContext.xml`中配置`<bean>`标签,将action包引入Spring容器中.
	2. 在`struts.xml`中创建`<action>`,其中的`class`类名与Spring的配置文件`applicationContext.xml`中`<bean>`的`id`属性一致.

这里采用交由Spring管理的方式.`<action>`中调转方式,使用变量代替,在URL中配置的命名和Action类中调用的方法名一致,完成后台方法的调用.


``` xml
<struts>
<!-- 交由Struts2自身管理
	<package name="ssh" extends="struts-default" namespace="/">
		<action name="product_*" class="action.ProductAction" method="{1}"></action>
	</package>
 -->
 
	<!-- 交由Spring进行管理 -->
 	<package name="ssh" extends="struts-default" namespace="/">
		<action name="product_*" class="productAction" method="{1}"></action>
		<action name="employee_*" class="employeeAction" method="{1}">
			<!-- 变量小写 -->
			<result name="input">/index.jsp</result>
			<result name="success" type="redirect">/jsps/main.jsp</result>
		</action>
		<action name="department_*" class="departmentAction" method="{1}">
			<result name="findAll">/jsps/departmentList.jsp</result>
			<result name="savePage">/jsps/departmentSave.jsp</result>
			<result name="edit">/jsps/departmentEdit.jsp</result>
			<result name="save" type="redirectAction">department_findAll.action</result>
			<result name="delete" type="redirectAction">department_findAll.action</result>
			<result name="update" type="redirectAction">department_findAll.action</result>
		</action>
		<action name="employee_*" class="employeeAction" method="{1}">
			<result name="success">/jsps/main.jsp</result>
			<result name="input">/index.jsp</result>
			<result name="findAll">/jsps/employeeList.jsp</result>
			<result name="savePage">/jsps/employeeSave.jsp</result>
			<result name="edit">/jsps/employeeEdit.jsp</result>
			<result name="save" type="redirectAction">employee_findAll.action</result>
			<result name="delete" type="redirectAction">employee_findAll.action</result>
			<result name="update" type="redirectAction">employee_findAll.action</result>
		</action>
	</package>
</struts>	
```
## 演示实例
这里以一个简单的保存商品的页面为例,进行从前台到后台最终保存进入数据库的过程.

### 创建JSP页面
在这里使用下划线的方式,通过`struts.xml`配置,完成映射.

``` xml
<h1>保存商品的页面</h1>
<form action="product_save" method="post">
	商品:<input type="text" name="pname"><br>
	价格:<input type="text" name="price"><br>
		<input type="submit" value="保存"><br>	 
</form> 
```

### 配置struts的映射
这里将URL中`product_save`进行解析,对应为Spring的`productAction`bean代表的类,并执行里面的`save()`方法
``` xml
<package name="ssh" extends="struts-default" namespace="/">
	<action name="product_*" class="productAction" method="{1}"></action>
</package>
```

### 创建Action类
这里通过继承`ActionSupport`类耦合Struts2,实现`ModelDriven<T>`,并指定泛型为`Product`类,实现将前台传入属性映射为对象属性,组装为数据库持久化对象.

``` java
public class ProductAction extends ActionSupport implements ModelDriven<Product>{

	private static final long serialVersionUID = 1L;
	
	/*使用模型驱动装配传入参数*/
	private Product product = new Product();
	@Override
	public Product getModel() {
		return product;
	}
	
	/*使用插件包,完成自动装配*/
	private ProductService productService;

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	/**保存商品的方法,注意方法名和Struts2的配置文件的一致*/
	public String save() {
		
		System.out.println("Action执行了:"+product.toString());
		productService.save(product);
		//无返回页面
		return this.NONE;
	}
	
}
```

### 创建Service类
通过私有Dao层的接口类,面向接口编程,提供`set()`方法并在`applicationContext.xml`配置文件中指明实现类,完成依赖注入

``` xml
<bean id="productService" class="service.ProductService">
	<property name="productDao" ref="productDao"/>
</bean>
```


通过在类方法中使用`@Transactional`注解,将其标注为一个事务类
``` java
@Transactional
public class ProductService {

	private ProductDao productDao;

	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}

	public void save(Product product) {
		productDao.save(product);
		System.out.println("Service的方法执行");
	}
	
}
```


### 创建Dao类
通过继承Spring的`HibernateDaoSupport`方法,使用Spring的数据库模板,调用模板方法,完成数据对象的持久化工作.

``` java
public class ProductDao extends HibernateDaoSupport {
	public void save(Product product) {
		this.getHibernateTemplate().save(product);
		System.out.println("Dao层中保存了对象");
	}
}
```

## 其他技术尝试
### 一对多双向关联映射
- 在一方通过`Set<T>`集合引用多方
- 在一方配置文件中使用`<set>`标签,并设置控制反转

``` xml
<set name="employees" table="EMPLOYEE" inverse="true" lazy="true" access="field" cascade="delete">
	<key>
		<column name="DID" />
	</key>
	<one-to-many class="bean.Employee" />
</set>
```

- 在多方通过私有多方的类,完成引用
- 在多方配置文件中使用`<many-to-one>`标签,指向多方的类

``` xml
<many-to-one name="department" class="bean.Department" cascade="none" fetch="join">
	<column name="DID" />
</many-to-one>
```



### 分页类实现
1. 创建分页类,并在`setCurrentPage()`方法中对当前页小于0和大于最大页的情况进行校准.

``` java
public class PageBean<T> {

	//当前页
	private int currentPage;
	//每页显示条数
	private int pageSize;
	//总条数
	private int totalCount;
	//总页数
	private int totalPage;
	//每页显示的
	private List<T> list;
	public int getCurrentPage() {
		return currentPage;
	}
	//分页,针对于当前页在另外的情况
	public void setCurrentPage(int currentPage) {
		if (currentPage < 1) {
			this.currentPage = 1;
		}else if (currentPage>totalPage){
			this.currentPage = totalCount;
		}else {
			this.currentPage = currentPage;
		}
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
}
```

2. 分页类的实现
Dao层通过调用Mybatis的分页方法,完成分页.

``` java
@Override
public List<Employee> findByPage(int begin, int pageSize) {
	DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class);
	//MySQL的分页查询语句
	List<Employee> list = this.getHibernateTemplate().findByCriteria(criteria, begin, pageSize);
	return list;
}
```

3. 分页查询的返回
Service层传入需求的页数,并设置分页类的初始条件,获得查询

``` java
@Override
public PageBean<Employee> findByPage(Integer currentPage) {

	PageBean<Employee> pageBean = new PageBean<Employee>();
	//封装每页显示记录数量
	int pageSize = 3;
	pageBean.setPageSize(pageSize);
	//封装总记录数
	int totalCount = employeeDao.findCount();
	pageBean.setTotalCount(totalCount);
	//封装总页数
	double tc = totalCount;
	Double num = Math.ceil(tc / pageSize);
	pageBean.setTotalPage(num.intValue());
	//封装当前页数
	pageBean.setCurrentPage(currentPage);
	//封装每页显示的数据
	int begin = (currentPage -1) * pageSize;	//MySQL开始查询的位置
	List<Employee> list = employeeDao.findByPage(begin,pageSize);
	pageBean.setList(list);

	return pageBean;
}
```


### Spring的Hibernate模板方法