---
title : Spring+SpringMVC+ Mybatis的构建商城秒杀项目
tags : JDK8 ,Eclipse ,Tomcat8
---

[TOC]

---

# 简介
使用SpringMVC结合Mybatis实现一个商城中秒杀活动的实现.

## 文件结构

- `main`
	- `java`
		- `bean`
			- `Seckill.java`
			- `SuccessSeckill.java`
		- `controller`
			- `SeckillController.java`
		- `dao`
			- `cache`
				- `RedisDao.java`
			- `SeckillDao.java`
			- `SuccessSeckillDao.java`
		- `dto`
			- `Exposer.java`
			- `SeckillExecution.java`
			- `SeckillResult.java`
		- `enums`
			- `SeckillStatEnum.java`
		- `exception`
			- `RepeatKillException.java`
			- `SeckillCloseException.java`
			- `SeckillException.java`
		- `service`
			- `imp`
				- `SeckillServiceImp.java`
			- `SeckillService.java`
	- `resources`
		- `mapper`
			- `SeckillDao.xml`
			- `SuccessSeckillDao.xml`
		- `spring`
			- `spring-controller.xml`
			- `spring-dao.xml`
			- `spring-service.xml`
		- `jdbc.properties`
		- `logback.xml`
		- `mybatis-config.xml`
	- `webapp`
		- `lib`
		- `static`
			- `img`
			- `js`
				- `seckill.js`
		- `WEB-INF`
			- `jsp`
				- `common`
					- `constant.jsp`
					- `head.jsp`
					- `tag.jsp`
				- `seckill`
					- `seckillDetail.jsp`
					- `seckillList.jsp`
				- `web.xml`
		- `index.jsp`
- `test`
	- `java`
		- `dao`
			- `cache`
				- `RedisDaoTest.java`
			- `SeckillDaoTest.java`
			- `SuccessSeckillDaoTest.java`
		- `service`
			- `SeckillServiceTest.java`
- `pom.xml`  Maven引用信息
## 使用技术
- Spring整合Mybatis
- REST风格URL设计
- Spring声明式事务
- JSON数据格式返回
- Redis缓存使用
- 统一异常处理
- MySQL存储过程编写

# 项目编码

## Pom文件结构

- `junit` 单元测试
- `commons-collections` 阿帕奇工具包
- `slf4j-api` 日志规范
- `logback-core`  日志接口
- `logback-classic` 日志实现
- `mysql-connector-java`  MySQL数据库驱动
- `c3p0`  MySQL数据库连接池
- `jedis`  Redis数据库驱动
- `protostuff-core`  二进制与对象模型转化工具
- `protostuff-runtime`  二进制与对象模型转化工具
- `mybatis`  Mybats核心包
- `mybatis-spring`  Mybatis提供的适配Spring包
- `javax.servlet-api`  Servlet规范
- `standard`  JSP标签
- `jstl`  EL表达式
- `jackson-core` json格式工具
- `jackson-databind`  json绑定SpringMVC
- `spring-core`  Spring核心包
- `spring-beans`  Spring的Bean管理包
- `spring-context` Spring的容器包
- `spring-jdbc`  Spring的JDBC连接包
- `spring-tx`  Spring的事务包
- `spring-aop`  Spring切面编程包
- `spring-web`  Spring整合Web包
- `spring-webmvc`  Spring的MVC模式实现包
- `spring-test`  Spring提供的测试包


``` xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
  
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.SSM</groupId>
  <artifactId>SSM</artifactId>
  <packaging>war</packaging>
  <version>0.0.1-SNAPSHOT</version>
  <name>SSM Maven Webapp</name>
  <url>http://maven.apache.org</url>
  
  <build>
    <finalName>SSM</finalName>
  </build>
  
  <dependencies>
  	<!-- junit测试 -->
	<!-- https://mvnrepository.com/artifact/junit/junit -->
	<dependency>
	    <groupId>junit</groupId>
	    <artifactId>junit</artifactId>
	    <version>4.12</version>
	    <scope>test</scope>
	</dependency>
	
	<!-- commons的工具包,集合框架 -->
	<!-- https://mvnrepository.com/artifact/commons-collections/commons-collections -->
	<dependency>
	    <groupId>commons-collections</groupId>
	    <artifactId>commons-collections</artifactId>
	    <version>3.2.2</version>
	</dependency>
	
	<!-- 日志,规范接口 -->
	<!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
	<dependency>
	    <groupId>org.slf4j</groupId>
	    <artifactId>slf4j-api</artifactId>
	    <version>1.7.25</version>
	</dependency>
	
	<!-- 规范日志接口 -->
	<!-- https://mvnrepository.com/artifact/ch.qos.logback/logback-core -->
	<dependency>
	    <groupId>ch.qos.logback</groupId>
	    <artifactId>logback-core</artifactId>
	    <version>1.1.7</version>
	</dependency>
	
	<!-- 接口实现 -->		
	<!-- https://mvnrepository.com/artifact/ch.qos.logback/logback-classic -->
	<dependency>
	    <groupId>ch.qos.logback</groupId>
	    <artifactId>logback-classic</artifactId>
	    <version>1.1.7</version>
	    <scope>test</scope>
	</dependency>
	
	<!-- 数据库驱动 -->
	<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
	<dependency>
	    <groupId>mysql</groupId>
	    <artifactId>mysql-connector-java</artifactId>
	    <version>5.1.41</version>
	</dependency>
		
	<!-- 数据库连接池 -->
	<!-- https://mvnrepository.com/artifact/com.mchange/c3p0 -->
	<dependency>
	    <groupId>com.mchange</groupId>
	    <artifactId>c3p0</artifactId>
	    <version>0.9.5.2</version>
	</dependency>
				
	<!-- Rides缓存 -->
	<!-- https://mvnrepository.com/artifact/redis.clients/jedis -->
	<dependency>
	    <groupId>redis.clients</groupId>
	    <artifactId>jedis</artifactId>
	    <version>2.9.0</version>
	</dependency>
	
	<!-- protostuff序列化,实现将对象转为二进制存入缓存 -->
	<!-- https://mvnrepository.com/artifact/com.dyuproject.protostuff/protostuff-core -->
	<dependency>
	    <groupId>com.dyuproject.protostuff</groupId>
	    <artifactId>protostuff-core</artifactId>
	    <version>1.1.1</version>
	</dependency>
	<!-- https://mvnrepository.com/artifact/com.dyuproject.protostuff/protostuff-runtime -->
	<dependency>
	    <groupId>com.dyuproject.protostuff</groupId>
	    <artifactId>protostuff-runtime</artifactId>
	    <version>1.1.1</version>
	</dependency>
	
					
	<!-- Mybatis核心jar包 -->
	<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
	<dependency>
	    <groupId>org.mybatis</groupId>
	    <artifactId>mybatis</artifactId>
	    <version>3.4.2</version>
	</dependency>

	<!-- Mybatis提供的整合Spring -->
	<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-spring -->
	<dependency>
	    <groupId>org.mybatis</groupId>
	    <artifactId>mybatis-spring</artifactId>
	    <version>1.3.1</version>
	</dependency>
		
	<!-- Servlet -->		
	<!-- https://mvnrepository.com/artifact/javax.servlet/javax.servlet-api -->
 	<dependency>
	    <groupId>javax.servlet</groupId>
	    <artifactId>javax.servlet-api</artifactId>
	    <version>3.1.0</version>
	    <scope>provided</scope>
	</dependency>
	
	<!-- jsp标签 -->
	<!-- https://mvnrepository.com/artifact/taglibs/standard -->
 	<dependency>
	    <groupId>taglibs</groupId>
	    <artifactId>standard</artifactId>
	    <version>1.1.2</version>
	</dependency>
		
	<!-- EL表达式 -->
 	<dependency>
	    <groupId>javax.servlet</groupId>
	    <artifactId>jstl</artifactId>
	    <version>1.2</version>
	</dependency>

	<!-- JSON数据 -->		
	<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->
	<dependency>
	    <groupId>com.fasterxml.jackson.core</groupId>
	    <artifactId>jackson-core</artifactId>
	    <version>2.8.8</version>
	</dependency>

	<!-- JSON数据MVC绑定 -->
	<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
	<dependency>
	    <groupId>com.fasterxml.jackson.core</groupId>
	    <artifactId>jackson-databind</artifactId>
	    <version>2.8.8</version>
	</dependency>
	
	<!-- Spring核心jar -->
	<!-- https://mvnrepository.com/artifact/org.springframework/spring-core -->
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-core</artifactId>
	    <version>4.3.7.RELEASE</version>
	</dependency>
	
	<!-- Spring的IOC -->
	<!-- https://mvnrepository.com/artifact/org.springframework/spring-beans -->
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-beans</artifactId>
	    <version>4.3.7.RELEASE</version>
	</dependency>

	<!-- Spring的容器 -->
	<!-- https://mvnrepository.com/artifact/org.springframework/spring-context -->
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-context</artifactId>
	    <version>4.3.7.RELEASE</version>
	</dependency>
	
	<!-- Spring的jdbc -->
	<!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-jdbc</artifactId>
	    <version>4.3.7.RELEASE</version>
	</dependency>
	
	<!-- Spring的事务 -->
	<!-- https://mvnrepository.com/artifact/org.springframework/spring-tx -->
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-tx</artifactId>
	    <version>4.3.7.RELEASE</version>
	</dependency>
	
	<!-- Spring的AOP -->
	<!-- https://mvnrepository.com/artifact/org.springframework/spring-aop -->
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-aop</artifactId>
	    <version>4.3.7.RELEASE</version>
	</dependency>
	
	<!-- Spring的WEB -->
	<!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-web</artifactId>
	    <version>4.3.7.RELEASE</version>
	</dependency>
	
	<!-- Spring的MVC -->
	<!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-webmvc</artifactId>
	    <version>4.3.7.RELEASE</version>
	</dependency>
	
	<!-- Spring的test -->
	<!-- https://mvnrepository.com/artifact/org.springframework/spring-test -->
	<dependency>
	    <groupId>org.springframework</groupId>
	    <artifactId>spring-test</artifactId>
	    <version>4.3.7.RELEASE</version>
	    <scope>test</scope>
	</dependency>
  </dependencies>
</project>
```


## 框架搭建
### `web.xml`指定
我们使用`web.xml`文件,配置Spring的前端控制器`org.springframework.web.servlet.DispatcherServlet`,设定启动优先级为1(数字越小优先级越高),匹配映射为全部映射路径.
使用`<init-param>`标签向Servlet向容器提供初始化参数`contextConfigLocation`,该参数为指向的文件为Spring的容器的配置文件,使用类路径`classpath:spring/spring-*.xml`表示,文件在`src\main\resources\spring`目录下.
``` xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns="http://xmlns.jcp.org/xml/ns/javaee"
	xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
	version="3.1">
	<display-name>SSM</display-name>
	<welcome-file-list>
		<welcome-file>index.jsp</welcome-file>
	</welcome-file-list>

	<!--SpringMVC的前端控制器,请求将由下面的配置xml进行处理 -->
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

	<!-- Spring的请求映射,指定映射规则,默认匹配所有的请求 -->
	<servlet-mapping>
		<servlet-name>springDispatcherServlet</servlet-name>
		<url-pattern>/</url-pattern>
	</servlet-mapping>
</web-app>
```


### `Spring`容器配置文件
本项目采用层次化的Spring配置文件,通过设定`<context:component-scan>`包扫描的`base-package`目录不同,从而实现分层次管理目录.


**`jdbc.properties`文件**
记载数据连接信息
``` 
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc\:mysql\://localhost\:3306/ssm?useUnicode=true&amp;characterEncoding=UTF-8
jdbc.username=root
jdbc.password=123456
```

**`spring-dao.xml`文件**

- 引用属性文件,并读取信息,从而创建C3P0数据库连接池.
- 初始化`SessionFactory`Bean,注入数据源;Mybatis全局属性的配置文件位置;mapper目录中的映射各个文件;并开启别名配置扫描包
- 基于接口的Mybatis映射,配置`org.mybatis.spring.mapper.MapperScannerConfigurer`映射类,传入`sqlSessionFactoryBeanName`指向`sqlSessionFactoryBeanName`和`basePackage`属性中需要扫描配置的包目录
- `redisDao`类,通过该类的依赖注入Redis的`JedisPool`连接池,创建缓存驱动.

``` xml
<beans>
	<!-- 数据库属性配置文件 -->
	<context:property-placeholder location="classpath:jdbc.properties" />

	<!-- 数据库连接池 -->
	<bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
		<property name="driverClass" value="${jdbc.driver}" />
		<property name="jdbcUrl" value="${jdbc.url}" />
		<property name="user" value="${jdbc.username}" />
		<property name="password" value="${jdbc.password}" />
		<property name="maxPoolSize" value="20" />
		<property name="minPoolSize" value="5" />
		<property name="autoCommitOnClose" value="false" />
		<property name="checkoutTimeout" value="2000" />		<!-- 连接超时时间 -->
		<property name="acquireRetryAttempts" value="2" />	<!-- 重试次数 -->
	</bean>

	<!-- SessionFactory对象 -->
	<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
		<!-- 注入数据库连接池 -->
		<property name="dataSource" ref="dataSource" />
		<!-- Mybatis全局属性 -->
		<property name="configLocation" value="classpath:mybatis-config.xml" />
		<!-- 扫描bean包,使用别名 -->
		<property name="typeAliasesPackage" value="bean" />
		<!-- 扫描mapper下的sql的xml文件 -->
		<property name="mapperLocations" value="classpath:mapper/*.xml" />
	</bean>

	<!-- 配置Dao接口的扫描包,动态实现 -->
	<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
		<!-- 注入SessionFactory -->
		<property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
		<!-- 给出扫描Dao的接口包 -->
		<property name="basePackage" value="dao" />
	</bean>
	
	<!-- RedisDao的注入,创建构造器设定参数-->
	<bean id="redisDao" class="dao.cache.RedisDao">
		<constructor-arg index="0" name="ip" value="localhost"/>
		<constructor-arg index="1" name="port" value="6379"/>
	</bean>
</beans>
```


**`spring-service.xml`文件**

- 启用动态扫描,将`service`包的类扫描进入Spring容器中.
- 注入数据库连接池,开启事务管理器,在这里使用JDBC的事务管理
- 开启基于注解的事务管理方式,模式采用代理模式

``` xml
<beans>

	<!-- 配置动态扫描,扫描Service包下的所有注解类型 -->
	<context:component-scan base-package="service" />

	<!-- 配置事务管理器,mybatis默认使用jdbc的事务 -->
	<bean id="transactionManager"
		class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
		<!-- 注入数据库连接池,Spring加载时默认会将所有的xml同步加载 -->
		<property name="dataSource" ref="dataSource" />
	</bean>

	<!-- 基于注解的声明式事务,也可以通过AOP切面编程实现 -->
	<tx:annotation-driven mode="proxy"
		transaction-manager="transactionManager" />
</beans>
```

**`spring-controller.xml`文件**

- 启动动态扫描,将`controller`包的类扫描进入Spring容器中.
- 启用注解配置,并配置注解驱动的简化信息
- 开启静态资源过滤,对静态资源不进行拦截
- 视图选择为JSP模板,并配置前缀和后缀.

``` xml
<beans>
	<!-- 配置SpringMVC -->

	<!-- 基于注解的controller配置,简化配置 (1)自动注册DefaultAnnotationHandlerMapping,AnnotationMethodHandlerAdapter 
		(2)提供数据绑定,日期,XML,数组格式化 @NumberFormat,@DataTimeFormat -->
	<mvc:annotation-driven>
	    <mvc:message-converters>
            <bean class="org.springframework.http.converter.StringHttpMessageConverter" />  
            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter" />  
        </mvc:message-converters>
	</mvc:annotation-driven>

	<!-- 静态资源的处理 -->
	<mvc:default-servlet-handler />

	<!-- 配置jsp模板引擎 -->
	<bean
		class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="viewClass"
			value="org.springframework.web.servlet.view.JstlView" />
		<property name="prefix" value="/WEB-INF/jsp/" />
		<property name="suffix" value=".jsp" />
	</bean>

	<!-- 包扫描 -->
	<context:component-scan base-package="controller"/>
</beans>
```

### `Mybatis`持久化框架配置
**`mybatis-config.xml`Mybatis整体配置文件**
通过在Spring的`org.mybatis.spring.SqlSessionFactoryBean`Bean的`configLocation`属性将该文件指定为Mybatis的整体配置文件

- 返回MySQL数据库插入数据时生成的自增主键
- 将查询结果集中的列别名作为`<resultMap>`中的属性,便于组装为Bean对象
- 开启驼峰命名法与数据库下划线命名之间的转换

``` xml
<configuration>
	<!-- 配置全局属性 -->
	<settings>
		<!-- 使用jdbc的getGeneratedKeys得到数据库的自增主键 -->
		<setting name="useGeneratedKeys" value="true" />

		<!-- 使用列别名替换 -->
		<setting name="useColumnLabel" value="true" />

		<!-- 开启驼峰命名的转换 user_name => userName -->
		<setting name="mapUnderscoreToCamelCase" value="true" />
	</settings>
</configuration>
```

**映射文件编写**
这里由于在设置中启用了简化版的配置文件,在保证查询结果集与Bean类属性一致的情况下,不需要额外配置`<resultMap>`属性,只需要编写SQL语句即可

####  `Seckill`秒杀动作的实现
- 减少数据库商品库存数量
- 查询商品全部信息
- 通过调用存过进行秒杀
``` xml
<mapper namespace="dao.SeckillDao">

	<!-- 减库存 -->
	<update id="reduceNumber">
		update seckill set number = number -1
		where seckill_id = #{seckillId}
		and start_time <![CDATA[<=]]>
		#{killTime}
		and end_time >= #{killTime}
		and number > 0;
	</update>

	<!-- 查询秒杀商品 -->
	<select id="queryById" resultType="Seckill" parameterType="long">
		select seckill_id , name , create_time , start_time , end_time from
		seckill
		where seckill_id = #{seckill_id};
	</select>

	<!-- 分页查询全部 -->
	<select id="queryAll" resultType="Seckill">
		select seckill_id , name ,number,
		start_time , end_time,create_time from seckill
		order by create_time desc
		limit
		#{offset}, #{limit};
	</select>
	
	<!-- 调用存储过程,完成秒杀逻辑 -->
	<select id="executeSeckillByProcedure" statementType="CALLABLE">
		call execute_seckill (
			#{seckillId , jdbcType = BIGINT , mode = IN},
			#{phone , jdbcType = BIGINT , mode = IN},
			#{killTime , jdbcType = TIMESTAMP , mode = IN},
			#{result , jdbcType = INTEGER , mode = OUT}
		);
	</select>
</mapper>    
```

#### `SuccessSeckill`秒杀成功动作的实现
- 插入购买明细
- 查询秒杀成功的信息,并携带商品信息

``` xml
<mapper namespace="dao.SuccessSeckillDao">
	<!-- 插入购买明细 -->
	<insert id="insertSuccessKill">
		<!-- 发生主键冲突时,不抛出异常,不执行插入.返回修改记录数为0 -->
		insert ignore into success_kill(seckill_id,user_phone)
		values
		(#{seckillId},#{userPhone});
	</insert>

	<!-- 根据秒杀ID查询SuccessKilled表,并返回携带秒杀对象实体 -->
	<select id="queryByIdIdWithSeckill" resultType="SuccessSeckill">
		<!-- 将查询结果映射到SuccessKilled同时映射到seckill属性.将别名忽略,自动实现驼峰命名转换,将其映射为对象属性 -->
		select
		sk.seckill_id,sk.user_phone,sk.create_time,sk.state,
		s.seckill_id as 'seckill.seckill_id',
		s.name as 'seckill.name',
		s.number as 'seckill.number',
		s.start_time as 'seckill.start_time',
		s.end_time as 'seckill.end_time',
		s.create_time as 'seckill.create_time'
		from success_kill sk
		inner join seckill s on
		sk.seckill_id = s.seckill_id
		where sk.seckill_id = #{seckillId}
		and
		sk.user_phone = #{userPhone};
	</select>
</mapper>  
```


### `logback.xml`日志格式
配置日志文件,以`logback.xml`文件命名,放在resource目录下,容器启动时自动加载.
- 设置日志格式
- 设置日志输出等级
``` xml
<configuration debug="true">
	<appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
		<encoder>
			<pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
		</encoder>
	</appender>
	<logger name="com.ibatis" level="debug" />
	<logger name="classpath:mapper" level="DEBUG" />

	<root level="debug">
		<appender-ref ref="STDOUT" />
	</root>
</configuration>
```


## 功能简介
用户可以看到商品的列表,可以通过时间看到当前是否开启活动;在用户点击进入详情页时,通过本地Cookie验证用户是否登录,进而进入详情页.
![展示实现][1]

在详情页可以执行具体的秒杀逻辑,当用户点击秒杀时,首先判断当前时间是否可以执行秒杀,否则给出秒杀结束或者倒计时显示;当可以秒杀时,我们暴露秒杀地址,用户携带MD加密后的字符与服务端进行校验,如果通过则进行秒杀成功,执行后续的减库存和记录秒杀详细,并返回结果.
![秒杀流程][2]
## 功能模块实现

### 秒杀和秒杀成功动作类Bean
这里需要将每个Bean的`toString()`方法重写,以便日志输出.
**秒杀表**
作为主表,记载了活动信息和当前库存

``` java
public class Seckill {

	private long seckillId; 	//秒杀ID
	
	private String name;		//秒杀活动
	
	private int number;			//库存数量
	
	private Date createTime;	//创建时间
	
	private Date startTime;		//开始时间
	
	private Date endTime;		//结束时间

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "Seckill [seckillId=" + seckillId + ", name=" + name + ", number=" + number + ", createTime="
				+ createTime + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}
}
```

**秒杀成功详细表**
作为从表,将每个活动对应的秒杀成功情况进行记录
记录秒杀ID和用户电话,作为联合主键,以及状态和创建时间
``` java
public class SuccessSeckill {

	private long seckillId;		//秒杀ID
	
	private long userPhone;		//用户电话
	
	/**	-1:无效	0:成功	1:已发货	2:已收货	*/
	private short state;		//商品状态
	
	private Date createTime;	//创建时间

	private Seckill seckill;	//多对一,方便存取
	
	public Seckill getSeckill() {
		return seckill;
	}

	public void setSeckill(Seckill seckill) {
		this.seckill = seckill;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(long userPhone) {
		this.userPhone = userPhone;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "SuccessSeckill [seckillId=" + seckillId + ", userPhone=" + userPhone + ", state=" + state
				+ ", createTime=" + createTime + ", seckill=" + seckill + "]";
	}
}
```

### `Seckill`表和`SuccessSeckill`表的操作

**秒杀表**
方法名与`mapper.xml`文件中标签的ID一致
使用`@Param`标识传入参数对应的`mapper.xml`中方法的参数一致.

- 减库存
- 查询秒杀商品
- 分页查询全部信息

``` java
public interface SeckillDao {

	/**
	 * 减库存
	 * @param seckillId 秒杀ID	
	 * @param KillTime	秒杀时间
	 * @return	秒杀记录ID
	 * 	注意:这里引用的param类型需要注意,引号内的参数名称,为mapper映射中传入的#{}的参数名
	 * 	参考资料=> http://blog.csdn.net/gao36951/article/details/44258217
	 */
	int reduceNumber(@Param("seckillId")long seckillId,@Param("killTime")Date KillTime);
	
	/**
	 * 查询秒杀商品
	 * @param seckillId	秒杀id
	 * @return	秒杀对象
	 */
	Seckill queryById(long seckillId);
	
	
	/**
	 * 分页查询全部
	 * @param offet	开始位置
	 * @param limit	偏移量
	 * @return	秒杀对象集合
	 * 	offset注解实现向SQL传入参数名称
	 */
	List<Seckill> queryAll(@Param("offset")int offset,@Param("limit")int limit);
}
```

**秒杀成功表**

- 插入秒杀成功的购买明细
- 根据秒杀ID和用户手机查询秒杀实现明细

``` java
public interface SuccessSeckillDao {

	/**
	 * 插入购买明细
	 * @param seckillId	秒杀物品ID
	 * @param userPhone	用户手机号
	 * @return	插入的记录ID
	 */
	int insertSuccessKill(@Param("seckillId") long seckillId ,@Param("userPhone") long userPhone);
	
	/**
	 * 根据秒杀ID和用户手机查询SuccessKilled表,并返回携带秒杀对象实体
	 * @param seckillId :秒杀成功表的ID
	 * @return	秒杀记录明细
	 */
	SuccessSeckill queryByIdIdWithSeckill(@Param("seckillId") long seckillId ,@Param("userPhone") long userPhone);
}
```

### 使用Redis缓存,进行插叙并缓存结果
这里使用Redis的缓存连接池创建连接
使用序列化操作,将对象转为二进制,进行对象的储存获取.

``` java
public class RedisDao {

	//日志
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//缓存数据库连接池
	private final JedisPool jedisPool;
	
	//序列化对象的约束
	private RuntimeSchema<Seckill> runtimeSchema = RuntimeSchema.createFrom(Seckill.class);
	
	/**
	 * @param ip 地址
	 * @param port 端口
	 * 自定义构造器,Spring容器可以通过构造方法的方式注入实例
	 */
	public RedisDao(String ip, int port) {
		jedisPool = new JedisPool(ip, port);
	}
	
	
	/**
	 * @param seckillId	商品的ID
	 * @return	秒杀商品的详情
	 */
	public Seckill getSeckill(long seckillId) {
		try {
			//获得数据库对象
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:"+seckillId;		//以key-value的形式,存储数据
				//get -> byte[] -> 反序列化 -> Object(Seckill)
				//使用自定义的序列化
				byte[] bytes = jedis.get(key.getBytes());
				//缓存中获取序列化,并转为对象
				if (bytes != null) {
					//创建空对象
					Seckill seckill = runtimeSchema.newMessage();
					//传入 取出的字节数组,空对象,对象约束->构建缓存对象
					ProtostuffIOUtil.mergeFrom(bytes, seckill, runtimeSchema);
					//返回 反序列化的结果
					return seckill;
				}
			} finally {
				//只是关闭,将异常上抛,捕获日志记录
				jedis.close();
			}
		} catch (Exception e) {
			//日志记录
			logger.error(e.getMessage(),e);
		} 
		
		return null;
	}
	
	/**
	 * @param seckill 秒杀商品
	 * @return 是否成功转化的为字节数组
	 */
	public String setSeckill(Seckill seckill) {
		// Object -> byte[] -> redis
		try {
			Jedis jedis = jedisPool.getResource();
			try {
				String key = "seckill:"+seckill.getSeckillId();	//序列化的键
				//将对象转为字节数组	传入,对象,对象序列化约束,缓存器的大小(这里使用缓存器的默认大小)
				byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, runtimeSchema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				//传入key,超时时间,序列化对象字节数组
				String result = jedis.setex(key.getBytes(), 60*60*3, bytes);
				return result;
				
			} finally {
				jedis.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}
```








  [1]: https://www.github.com/jionjion/Picture_Space/raw/master/WorkSpace/Java/SSM/1510389041080.jpg
  [2]: https://www.github.com/jionjion/Picture_Space/raw/master/WorkSpace/Java/SSM/SSM-02.png "SSM-02"
