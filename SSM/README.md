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
			- `Seckill.java`                                       秒杀商品活动类
			- `SuccessSeckill.java`                         秒杀商品成功类
		- `controller`
			- `SeckillController.java`                       秒杀模块前端控制器
		- `dao`
			- `cache` 
				- `RedisDao.java`                            使用Redis缓存查询
			- `SeckillDao.java`                               Seckill表秒杀活动
			- `SuccessSeckillDao.java`                 SuccessSeckill表秒杀成功
		- `dto`
			- `Exposer.java`                                  暴露接口的封装类
			- `SeckillExecution.java`                     秒杀执行的封装类
			- `SeckillResult.java`                          秒杀结果的封装类
		- `enums`
			- `SeckillStatEnum.java`                     商品秒杀状态枚举类
		- `exception`
			- `RepeatKillException.java`               重复秒杀异常
			- `SeckillCloseException.java`            秒杀关闭异常
			- `SeckillException.java`                     秒杀异常,父类
		- `service`
			- `imp`
				- `SeckillServiceImp.java`              秒杀服务实现类
			- `SeckillService.java`                        秒杀服务接口
	- `resources`
		- `mapper`
			- `SeckillDao.xml`                               Seckill表的SQL
			- `SuccessSeckillDao.xml`                 SuccessSeckill表的SQL
		- `spring`
			- `spring-controller.xml`                     Spring中Controller层的配置文件
			- `spring-dao.xml`                             Spring中Dao层的配置文件
			- `spring-service.xml`                        Spring中Service层的配置文件 
		- `jdbc.properties`                                 JDBC数据库连接信息配置文件
		- `logback.xml`                                      日志输出格式配置文件
		- `mybatis-config.xml`                           Mybatis整体配置文件
	- `webapp`
		- `lib`                                                     其他扩展jar包
		- `static`
			- `img`
			- `js`
				- `seckill.js`                                    秒杀详情页面JS
		- `WEB-INF`
			- `jsp`
				- `common` 
					- `constant.jsp`                         页面常量定义
					- `head.jsp`                               页面静态资源引用
					- `tag.jsp`                                  JSTL标签引用
				- `seckill`
					- `seckillDetail.jsp`                   秒杀详情页面
					- `seckillList.jsp`                       秒杀列表页面
				- `web.xml`                                   站点配置文件
		- `index.jsp`                                          主页链接
- `test`
	- `java`
		- `dao`
			- `cache`
				- `RedisDaoTest.java`                   Redis缓存测试
			- `SeckillDaoTest.java`                      Seckill表测试
			- `SuccessSeckillDaoTest.java`        SuccessSeckill表测试
		- `service`
			- `SeckillServiceTest.java`                秒杀服务测试
- `pom.xml`                                                   Maven引用信息
## 使用技术
- Maven包管理
- Spring整合Mybatis
- REST风格URL设计
- Spring声明式事务
- JSON数据格式返回
- Redis缓存使用
- 统一异常处理
- MySQL存储过程编写
- JSP页面分层管理
- JS对象层次编写
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
- 通过调用存过进行秒杀,使用`call`关键字调用,传入参数类型,出入类型,`statementType="CALLABLE"`表示该SQL语句不需要动态编译.
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

### 使用Redis缓存,进行查询并缓存结果
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

### 统一异常处理
通过在Spring运行期间抛出运行时异常并交由Spring容器捕获,从而实现事物的管理和消息代码的传递.

#### 基类异常
通过创建基类异常,便于统一捕获秒杀活动相关的错误.

``` java
public class SeckillException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public SeckillException() {
		super();
	}

	public SeckillException(String message, Throwable cause) {
		super(message, cause);
	}

	public SeckillException(String message) {
		super(message);
	}
}
```

#### 秒杀关闭异常

``` java
public class SeckillCloseException extends SeckillException{

	private static final long serialVersionUID = 1L;

	public SeckillCloseException() {
		super();
	}

	public SeckillCloseException(String message, Throwable cause) {
		super(message, cause);
	}

	public SeckillCloseException(String message) {
		super(message);
	}
}
```


#### 重复秒杀异常

``` java
public class RepeatKillException extends SeckillException{

	private static final long serialVersionUID = 1L;

	public RepeatKillException() {
		super();
	}

	public RepeatKillException(String message, Throwable cause) {
		super(message, cause);
	}

	public RepeatKillException(String message) {
		super(message);
	}
}
```

### 状态枚举类
通过枚举类的创建,便于统一维护系统消息代码.
1. 属性私有
2. 使用私有属性创建构造器
3. 提供`get()`方法
4. 构造不同的枚举变量,逗号分隔,分号结束,放在类首.
5. 创建根据`state`属性进行索引的方法.

``` java
public enum SeckillStatEnum {
	 SUCCESS(1,"秒杀成功"),
	 END_KILL(0,"秒杀结束"),
	 REPEAT_KILL(-1,"重复秒杀"),
	 INNER_ERROR(-2,"系统异常"),
	 DATA_REWRITE(-3,"数据篡改");
	
	private int state;
	
	private String stateInfo;
	
	private SeckillStatEnum(int state,String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}
	
	/**传入状态索引,迭代返回枚举,在存储过程的返回结果中进行调用*/
	public static SeckillStatEnum stateOf(int index) {
		for(SeckillStatEnum state: values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
```







  [1]: https://www.github.com/jionjion/Picture_Space/raw/master/WorkSpace/Java/SSM/1510389041080.jpg
  [2]: https://www.github.com/jionjion/Picture_Space/raw/master/WorkSpace/Java/SSM/SSM-02.png "SSM-02"


### 返回数据包装类

#### 暴露接口的包装类
通过不同的构造函数,封装不同的属性组合,便于向前台输出结果.
- 开启秒杀时需要的MD5校验和秒杀商品活动的ID
- 没有开启时返回活动开始结束时间和当前服务校验时间
- 通过秒杀ID判断是否可以进行秒杀活动
``` java
public class Exposer {

	private boolean exposed;		//是否开启活动
	
	private String md5;				//用户消息验证
	
	private long seckillId;			//秒杀活动ID
	
	private long now;				//当前服务器时间
	
	private long start;				//活动开始时间
	
	private long end;				//活动结束时间

	/**
	 * 开启秒杀
	 * @param exposed 是否开启秒杀
	 * @param md5  验证用户
	 * @param seckillId 秒杀物品ID
	 */
	public Exposer(boolean exposed, String md5, long seckillId) {
		super();
		this.exposed = exposed;
		this.md5 = md5;
		this.seckillId = seckillId;
	}
	
	/**
	 * 没有开始秒杀,返回系统时间
	 * @param exposed 是否开启秒杀
	 * @param now 现在时间
	 * @param start 开始时间
	 * @param end 结束时间
	 */
	public Exposer(boolean exposed,long seckillId, long now, long start, long end) {
		super();
		this.exposed = exposed;
		this.seckillId = seckillId;
		this.start = start;
		this.end = end;
	}

	/**
	 * 返回当前是否可以秒杀
	 * @param exposed 是否开启秒杀
	 * @param seckillId 秒杀物品ID
	 */
	public Exposer(boolean exposed, long seckillId) {
		super();
		this.exposed = exposed;
		this.seckillId = seckillId;
	}

	public boolean isExposed() {
		return exposed;
	}

	public void setExposed(boolean exposed) {
		this.exposed = exposed;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public long getNow() {
		return now;
	}

	public void setNow(long now) {
		this.now = now;
	}

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}
	
	@Override
	public String toString() {
		return "Exposer [exposed=" + exposed + ", md5=" + md5 + ", seckillId=" + seckillId + ", now=" + now + ", start="
				+ start + ", end=" + end + "]";
	}
}
```




#### 秒杀对象的包装类

通过传入泛型,代表数据库查询结果;布尔值代表执行结果,如果失败则返回失败信息.
- 查询成功则返回数据和布尔结果值
- 查询失败则返回错误信息和布尔值


``` java
public class SeckillResult<T> {

	private boolean success;
	
	private T data;
	
	private String error;

	/**成功*/
	public SeckillResult(boolean success, T data) {
		super();
		this.success = success;
		this.data = data;
	}

	/**失败*/
	public SeckillResult(boolean success, String error) {
		super();
		this.success = success;
		this.error = error;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "SeckillResult [success=" + success + ", data=" + data + ", error=" + error + "]";
	}
}
```


#### 执行结果的包装类
对秒杀执行结果进行包装
- 如果秒杀成功,则返回商品状态和秒杀成功的信息
- 如果秒杀失败,则返回商品状态秒杀活动ID

``` java
public class SeckillExecution {

	private long seckillId;  				//秒杀对象ID
	
	private int state;						//秒杀商品状态code
	
	private String stateInfo;				//秒杀商品状态信息
	
	private SuccessSeckill successSeckill;	//秒杀成功对象

	/**
	 * 秒杀成功的状态
	 * @param seckillId	秒杀对象
	 * @param SeckillStatEnum: 包含 state 秒杀结果状态   stateInfo 状态信息
	 * @param successSeckill 秒杀成功对象
	 */
	public SeckillExecution(long seckillId, SeckillStatEnum statEnum, SuccessSeckill successSeckill) {
		super();
		this.seckillId = seckillId;
		this.state = statEnum.getState();
		this.stateInfo = statEnum.getStateInfo();
		this.successSeckill = successSeckill;
	}

	/**
	 * 秒杀失败的状态
	 * @param seckillId	秒杀对象
	 * @param SeckillStatEnum: 包含 state 秒杀结果状态   stateInfo 状态信息
	 */
	public SeckillExecution(long seckillId, SeckillStatEnum statEnum) {
		super();
		this.seckillId = seckillId;
		this.state = statEnum.getState();
		this.stateInfo = statEnum.getStateInfo();
	}

	public long getSeckillId() {
		return seckillId;
	}

	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public SuccessSeckill getSuccessSeckill() {
		return successSeckill;
	}

	public void setSuccessSeckill(SuccessSeckill successSeckill) {
		this.successSeckill = successSeckill;
	}

	@Override
	public String toString() {
		return "SeckillExecution [seckillId=" + seckillId + ", state=" + state + ", stateInfo=" + stateInfo
				+ ", successSeckill=" + successSeckill + "]";
	}
}
```


### 服务层
#### 秒杀接口展示

- 获得秒杀活动的列表
- 获得秒杀活动的ID
- 执行秒杀工作
- 执行秒杀工作,通过存储过程


``` java
public interface SeckillService {

	
	/**
	 * 	获得秒杀的列表
	 * @return 秒杀物品列表
	 */
	List<Seckill> getSeckillList();
	
	/**
	 * 	获得秒杀
	 * @param seckillId 秒杀对象物品的ID
	 * @return 秒杀对象物品
	 */
	Seckill getById(long seckillId);
	
	/**
	 * 	是否开启秒杀
	 * @param serckillId 如果开启秒杀则暴露秒杀地址,否则返回系统时间
	 * @return 是否开启秒杀
	 */
	Exposer exportSeckillUrl(long seckillId);

	
	/**
	 * 执行秒杀工作
	 * @param seckillId 秒杀物品的ID
	 * @param userPhone 用户电话
	 * @param md5 验证URL
	 * @return 秒杀结果
	 * @throws RepeatKillException 重复秒杀异常
	 * @throws SeckillCloseException 秒杀关闭异常
	 * @throws SeckillException 通用异常
	 */
	SeckillExecution executeSeckill(long seckillId,long userPhone,String md5)
				throws RepeatKillException,SeckillCloseException,SeckillException;
	
	/**
	 * 执行秒杀工作,通过存储过程
	 * @param seckillId 秒杀物品的ID
	 * @param userPhone 用户电话
	 * @param md5 验证URL
	 * @return 秒杀结果
	 */
	SeckillExecution executeSeckillByProcedure(long seckillId,long userPhone,String md5);	
}
```

#### 秒杀接口实现

- `Exposer exportSeckillUrl(long seckillId)`通过传入秒杀活动ID获得当前秒杀物是否开启轰动,使用数据库和数据库缓存技术完成基础查询,如果没有开启,则返回包装活动开始时间,结束时间,当前服务器时间的包装类,如果活动开启,则返回MD5加密后的秒杀活动ID,交由客户端以便随后进行校验
- `SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)`这是一个事务方法,用于执行减库存和插入购买明细.如果秒杀活动结束或者重复秒杀,则抛出异常交由Spring容器统一捕获,顺利则返回包装有秒杀活动ID和秒杀成功详情页的消息.
- `String getMD5(long SeckillId)`使用盐结合秒杀活动ID进行MD5消息摘要.
- `SeckillExecution executeSeckillByProcedure(long seckillId, long userPhone, String md5)`使用MySQL的存储过程执行减库存和插入购买明细

``` java
@Service
public class SeckillServiceImp implements SeckillService {

	//日志对象,不需要使用Spring的IOC容器
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final String salt = "Jion is mine";		//MD5消息摘要加密的盐
	
	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private SuccessSeckillDao successSeckillDao;

	
	@Override
	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 10);
	}

	@Override
	public Seckill getById(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	@Override
	public Exposer exportSeckillUrl(long seckillId) {
		/*秒杀地址的暴露,MySQL版*/
//		Seckill seckill = seckillDao.queryById(seckillId);
		
		/*秒杀地址的暴露,Redis缓存版*/
		//1.尝试从缓存中获取
		Seckill seckill =  redisDao.getSeckill(seckillId);
		//2.获取失败,从数据库中获取
		if (seckill == null) {
			seckill = seckillDao.queryById(seckillId);
			//3.判断,如果还是获取不到,则传入有误
			if (seckill == null) {
				return new Exposer(false, seckillId);
			}else {
			//3.放入缓存中
				redisDao.setSeckill(seckill);
			}
				
		}			
		//获得秒杀物的时间信息,并比较
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date nowTime = new Date();
		if (nowTime.getTime()<startTime.getTime() || nowTime.getTime()>endTime.getTime()) {
			return new Exposer(false,seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		//返回md5的加密结果
		String md5 = getMD5(seckillId);
		
		//暴露秒杀,返回消息摘要
		return new Exposer(true, md5, seckillId);
	}
	
	/*	注解事务的优点
	 *	1.开发团队达成一致约定,明确标注事务方法的编程风格
	 *	2.保证事务方法的执行时间尽可能短,不要穿插其他网络操作,如缓存/请求.(可以将其剥离到事务方法外)
	 *	3.明确方法的事务控制,只有在需要的时候声明其为事务方法
	 */
	@Override
	@Transactional
	public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
			throws RepeatKillException, SeckillCloseException, SeckillException {
		try {
			//将用户携带的md5和服务器端的进行比较
			if(md5 == null || !md5.equals(getMD5(seckillId))) {
				throw new SeckillException("秒杀地址异常,请从新进入提交");
			}
			/*执行秒杀事务过程*/	
			
			//记录结果 [优化:首先出入购买明细]
			int insertResult = successSeckillDao.insertSuccessKill(seckillId, userPhone);
			if (insertResult <= 0) {
				//插入秒杀成功记录,秒杀失败
				throw new RepeatKillException("抱歉,你已经秒杀成功,本次秒杀无效");
			}
			//进行减库存 [优化:减少持有行级所更新数据的时间]
			int updateResult = seckillDao.reduceNumber(seckillId, new Date());
			if (updateResult <= 0) {
				//没有库存减少,秒杀失败
				throw new SeckillCloseException("很抱歉,秒杀活动已经结束");
			}
			SuccessSeckill successSeckill = successSeckillDao.queryByIdIdWithSeckill(seckillId, userPhone);
			return new SeckillExecution(seckillId,SeckillStatEnum.SUCCESS,successSeckill);
			
		} catch (SeckillCloseException e) {
			throw e;
		} catch (RepeatKillException e) {
			throw e;
		} catch (Exception e) {
			logger.error("秒杀事务出现异常:"+e.getMessage());
			throw new SeckillException("很抱歉,秒杀过程中,服务器出现问题");
		}
	}
	
	/**
	 * 秒杀物品结合盐进行加密算法
	 * 根于的秒杀物生成消息摘要作为服务器一端的验证,结合用户携带的进行验证
	 * @param SeckillId	获得所需要的
	 * @return 
	 */
	private String getMD5(long SeckillId) {
		String base = SeckillId + '/' + salt;
		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
		return md5;
	}

	
	/*
	 *	调用存储过程完成事物逻辑的判断 
	 */
	@Override
	public SeckillExecution executeSeckillByProcedure(long seckillId, long userPhone, String md5){
		//将用户携带的md5和服务器端的进行比较
		if(md5 == null || !md5.equals(getMD5(seckillId))) {
			return new SeckillExecution(seckillId, SeckillStatEnum.DATA_REWRITE);//数据被修改
		}
		Date killTime = new Date();
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("seckillId", 1000);				//传入参数
		map.put("phone", userPhone);
		map.put("killTime", killTime);
		map.put("result", null);				//输出参数
		try {
			seckillDao.executeSeckillByProcedure(map);
			//获取map中的执行结果.使用common的工具包
			int result = MapUtils.getInteger(map, "result",-2);		//获得map中的值,如果为空,则返回-2
			if (result == 1) {
				//正常执行,返回执行的结果.
				SuccessSeckill successSeckill = successSeckillDao.queryByIdIdWithSeckill(seckillId, userPhone);
				return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successSeckill);
			}else{
				return new SeckillExecution(seckillId, SeckillStatEnum.stateOf(result));
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
		}
	}
}
```

### 控制层

- `String getSeckillList(Model model)`进入商品的返回列表
- `String seckillDetail(@PathVariable("seckillId") Long seckillId,Model model)`使用REST风格的URL传递参数,进入商品的详情页
- `SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long serckillId)`通过传入秒杀活动ID,查询当前是否开启秒杀活动,如果抛出异常则秒杀活动结束
- `SeckillResult<SeckillExecution> execute(	@PathVariable("seckillId") Long seckillId,@CookieValue(value = "userPhone", required = false) Long userPhone,@PathVariable("md5") String md5)`执行秒杀结果
- `SeckillResult<Long> getNowTime()`获得当前服务器时间,校准客户端倒计时

``` java
@Controller
@RequestMapping("/seckill")	//URL:/模块/资源/{id}/细分
public class SeckillController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SeckillService seckillService;
	
	
	/**
	 * 秒杀的商品列表页面
	 * @param model数据模型
	 * @return 跳转页面
	 * URL: 
	 */
	@RequestMapping(value="/seckillList",method=RequestMethod.GET)
	public String getSeckillList(Model model) {
		List<Seckill> seckillList = seckillService.getSeckillList();
		model.addAttribute("seckillList",seckillList);
		//seckillList.jsp + model = ModelAndView
		return "seckill/seckillList";	// 对应文件/WEB-INF/jsp/seckill/seckillList.jsp	
	}
	
	
	/**
	 * 商品的详情页面
	 * @param seckillId 使用
	 * @param model数据模型
	 * @return 跳转页面
	 */
	@RequestMapping(value="/{seckillId}/detail",method=RequestMethod.GET)
	public String seckillDetail(@PathVariable("seckillId") Long seckillId,Model model) {
		if (seckillId == null) {
			//URL中不存在参数,则重定向
			return "redirect:/seckill/seckillList";
		}
		Seckill seckill = seckillService.getById(seckillId);
		if (seckill == null) {
			//未获取秒杀商品,转发
			return "forward:/seckill/seckillList";
		}
		model.addAttribute("seckill",seckill);
		return "seckill/seckillDetail";
	}

	
	/**
	 * 将秒杀暴露接口,通过JSON数据传出
	 * @return 秒杀信息接口对象
	 */
	@RequestMapping(value="/{seckillId}/exposer",method=RequestMethod.GET,produces={"application/json; charset=UTF-8"})
	@ResponseBody	//返回为JSON格式
	public SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long serckillId) {
		SeckillResult<Exposer> seckillResult = null;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(serckillId);
			seckillResult = new SeckillResult<Exposer>(true, exposer);
		} catch (Exception e) {
			logger.error(e.getMessage());
			seckillResult = new SeckillResult<Exposer>(false, e.getMessage());
		}
		return seckillResult; 
	}
	
	
	/**
	 * 执行秒杀过程
	 * @param seckillId 秒杀物品的ID
	 * @param userPhone 用户的电话
	 * @param md5 :携带的消息摘要
	 * @return :秒杀结果,并封装为JSON格式
	 */
	@RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8" })
	@ResponseBody	//返回为JSON格式
	public SeckillResult<SeckillExecution> execute(	@PathVariable("seckillId") Long seckillId,
													@CookieValue(value = "userPhone", required = false) Long userPhone,
													@PathVariable("md5") String md5) {
		if (userPhone == null) {
			//电话为空,未注册
			return new SeckillResult<SeckillExecution>(false, "当前未注册");
		}
		
		try {
			//使用Mybatis调用JDBC获得秒杀执行结果
//			SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, userPhone, md5);
			//使用Mybatis调用存储过程获得执行结果
			SeckillExecution seckillExecution = seckillService.executeSeckillByProcedure(seckillId, userPhone, md5);
			
			return new SeckillResult<SeckillExecution>(true, seckillExecution);
		} catch (RepeatKillException e) {
			//重复秒杀,逻辑异常,不需要日志记录
			SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.REPEAT_KILL);
			return new SeckillResult<SeckillExecution>(true, seckillExecution);
		} catch (SeckillCloseException e) {
			//秒杀关闭,逻辑异常,不需要日志记录
			SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.END_KILL);
			return new SeckillResult<SeckillExecution>(true, seckillExecution);
		} catch (SeckillException e) {
			//其他异常,需要日志
			logger.error(e.getMessage());
			SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
			return new SeckillResult<SeckillExecution>(true, seckillExecution);
		}
	}
	
	
	/**
	 * 返回服务器的系统时间
	 * @return 服务器的系统时间
	 */
	@RequestMapping(value="/time/now",method=RequestMethod.GET,produces={"application/json; charset=UTF-8"})
	@ResponseBody	//返回为JSON格式
	public SeckillResult<Long> getNowTime(){
		Date now = new Date();
		return new SeckillResult<Long>(true, now.getTime());
	} 
}
```

### 前端页面显示

#### 页面常量
在这里定义前端常用到的常量信息,便于使用`<%@ include file=''...'' %>`标签引用

``` html
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
```

#### JSTL标签
在这里定义前端常用到的标签,便于使用`<%@ include file=''...'' %>`标签引用

``` html
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
```

#### 静态文件引用
这里定义页面常用到的JS和CSS样式,便于使用`<%@ include file="../common/head.jsp"%>`引用.
``` html
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script src="https://cdn.bootcss.com/jquery/2.2.1/jquery.js"></script>
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
<script src="https://cdn.bootcss.com/jquery.countdown/2.2.0/jquery.countdown.js"></script>

<link rel="stylesheet"	href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script	src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
```


#### 列表展示页面
显示当前待秒杀的商品活动信息
``` html
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/tag.jsp"%>
<%@ include file="../common/constant.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>秒杀商品列表</title>
<%@include file="../common/head.jsp"%>
</head>
<body>

	<!-- 列表部分 -->
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading text-center">
				<h2>秒杀商品列表</h2>
			</div>
			<div class="panel-body">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>名称</th>
							<th>库存</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>创建时间</th>
							<th>详情点击</th>
						</tr>
						<c:forEach var="item" items="${seckillList}">
							<tr>
								<td>${item.name}</td>
								<td>${item.number}</td>
								<td><fmt:formatDate value="${item.startTime}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><fmt:formatDate value="${item.endTime}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><fmt:formatDate value="${item.createTime}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><a class="btn btn-info"
									href="<%=basePath %>/seckill/${item.seckillId}/detail"
									target="_blank">详情</a></td>
							</tr>
						</c:forEach>
					</thead>
				</table>
			</div>
		</div>
	</div>
</body>
</html>
```


#### 商品秒杀详情页面
使用Bootstrap进行简单的页面绘制.
``` html
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/tag.jsp"%>
<%@ include file="../common/constant.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>秒杀商品详情</title>
<%@ include file="../common/head.jsp"%>
</head>
	<script type="text/javascript" src="<%=basePath %>/static/js/seckill.js"></script>
	<script type="text/javascript">
		$(function(){
			//使用EL表达式传入参数
			seckill.detail.init({
				seckillId : ${seckill.seckillId},
				startTime : ${seckill.startTime.time},
				endTime   :	${seckill.endTime.time}
			});
		});
	</script>
<body>
	<!-- 面板部分 -->
	<div class="container">
		<div class="panel panel-default text-center">
			<div class="panel-heading"><h3>${seckill.name}</h3></div>
			<div>
				<div class="panel-body">
					<h2 class="text-danger">
						<!-- 图标 -->
						<span class="glyphicon glyphicon-time"></span>
						<!-- 计时 -->
						<span class="glyphicon" id="seckill-box"></span>
					</h2>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 模态弹窗 -->
	<div id="userPhoneModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title text-center">
						<span class="glyphicon glyphicon-phone">用户电话:</span>
					</h3>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-xs-8 col-xs-offset-2">
							<input type="text" name="userPhone" id="userPhoneKey" placeholder="请填写手机号" class="form-control">
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<!-- 验证 -->
					<span id="userPhoneMessage" class="glyphicon"></span>
					<button type="button" id="userPhoneBtn" class="btn btn-success">
						<span class="glyphicon glyphicon-phone">提交</span>
					</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
```

#### 秒杀JS代码
这里使用创建对象的方式,将整个秒杀逻辑封装为一个对象,通过调用对象的方法进行层次化的执行

``` javascript
var seckill = {
		//项目根路径
		//js获取项目根路径，如： http://localhost:8080/ssm
		basePath : function (){
		    //获取当前网址，如： http://localhost:8080/ssm/share/meun.jsp
		    var curWwwPath=window.document.location.href;
		    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
		    var pathName=window.document.location.pathname;
		    var pos=curWwwPath.indexOf(pathName);
		    //获取主机地址，如： http://localhost:8083
		    var localhostPaht=curWwwPath.substring(0,pos);
		    //获取带"/"的项目名，如：/uimcardprj
		    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
		    return(localhostPaht+projectName);
		},
		
		//封装URL
		URL : {
			//请求当前服务器时钟
			now : function(){
				var basePath = seckill.basePath();
				return  basePath + '/seckill/time/now';	
			},
			//请求接口暴露参数
			exposer : function(seckillId){
				var basePath = seckill.basePath();
				return basePath + '/seckill/' + seckillId + '/exposer'; 
			},
			//秒杀接口
			execution : function(seckillId,md5){
				var basePath = seckill.basePath();
				return basePath + '/seckill/' + seckillId + '/' + md5 + '/execution';
			}
		},
		//执行秒杀逻辑,传入秒杀物品的ID和当前显示的DOM元素
		executeSeckill : function(seckillId,node){
			//首先在操作前隐藏
			node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>')
			//时间结束后,执行秒杀逻辑
			$.get(seckill.URL.exposer(seckillId),function(result){
				//判断,获取秒杀暴露对象
				if(result && result['success']){
					//获取秒杀暴露对象
					var exposer = result['data'];
					if(exposer['exposed']){
						//开启秒杀
						var md5 = exposer['md5'];
						var killUrl = seckill.URL.execution(seckillId, md5);
						//点击按钮,执行秒杀
						$('#killBtn').one('click',function(){
							//1.首先禁止按钮
							$(this).addClass('disabled');
							//2.发送秒杀的请求
							$.post(killUrl,function(result){
								//请求成功
								if (result && result['success']) {
									var killResult = result['data'];
									var state = killResult['state'];
									var stateInfo = killResult['stateInfo'];
									//3.显示秒杀结果
									node.html('<span class="label label-success">' + stateInfo + '</span>');
								}
							});
						});
						node.show();
					}else{
						//未开启,可能用户系统时间不一致,返回系统时间进行校准
						var nowTime = exposer['now'];
						var startTime = exposer['start'];
						var endTime = exposer['end'];
						//修正计时
						seckill.countTime(seckillId,nowTime,startTime,endTime);
					}
				}else{
					//失败打印日志
					console.log(result);
				}
			})
		},
		//验证手机号
		validatePhone : function(phone){
			if(phone && phone.length===11 && !isNaN(phone)){
				return true;
			}else{
				return false;
			}
		},
		//计时交互
		countTime : function(seckillId,nowTime,startTime,endTime){
			var seckillBox = $('#seckill-box')
			//时间判断
			if(nowTime > endTime){
				seckillBox.html('秒杀结束!');
			}else if(nowTime < startTime){
				//秒杀未开始,开始计时,将计时组件初始化
				var killTime = new Date(startTime);	//创建日期对象
				//将span元素,绑定countdown插件
				seckillBox.countdown(killTime,function(event){	
					//日期格式化
					var format = event.strftime('秒杀时间: %D天 %H时 %M分 %S秒');
					seckillBox.html(format);
				//组件绑定事件,时间结束
				}).on('finish.countdown',function(){
					seckill.executeSeckill(seckillId,seckillBox);
				});
				
			}else{
				//执行秒杀逻辑
				seckill.executeSeckill(seckillId,seckillBox);
			}
		},
		
		//详情页秒杀逻辑
		detail : {
			//初始化
			init : function(params){
				//手机验证和登录,计时
				//cookie中的手机号
				var userPhone = $.cookie('userPhone');
				//验证手机号,没有绑定
				if(!seckill.validatePhone(userPhone)){
					//绑定手机号
					var userPhoneModal = $('#userPhoneModal');
					userPhoneModal.modal({
						show : true,
						backdrop : false,
						keyboard : false
					});
					$('#userPhoneBtn').click(function(){
						var inputPhone = $('#userPhoneKey').val();
						if(seckill.validatePhone(inputPhone)){
							//写入cookie,注意cookie的写法
							$.cookie('userPhone',inputPhone,{expires:1,path:"/SSM/seckill"});
							//验证通过,刷新页面
							window.location.reload();
						}else{
							$('#userPhoneMessage').hide().html('<label class="label label-danger">手机号码错误!</label>').show(300);
						}
					});
				}
				
				//已经登录
				var seckillId = params['seckillId'];
				var startTime = params['startTime'];
				var endTime = params['endTime'];
				$.get(seckill.URL.now(),function(result){
					if(result){
						//服务器时间
						var nowTime = result['data'];
						//计时交互
						seckill.countTime(seckillId,nowTime,startTime,endTime);
					}else{
						//失败打印日志
						console.log(result);
					}
				});
			}
		}
	}
```

## 测试类

### Redis缓存测试
通过引入dao层的Spring配置文件,实现依赖注入,完成查询测试

``` java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

	@Autowired
	private RedisDao redisDao;	//缓存操作
	
	@Autowired
	private SeckillDao seckillDao;	//数据库操作
	
	private long seckillId = 1000;	//商品的ID
	/**
	 * 	测试缓存的get/set方法.
	 */
	@Test
	public void testSetAndGetSeckill() {
		//首先试图通过缓存获得秒杀商品的信息
		Seckill seckill = redisDao.getSeckill(seckillId);
		//如果获取失败,从数据库中获取
		if (seckill == null) {
			seckill = seckillDao.queryById(seckillId);
			//将获取的对象存入缓存中
			if (seckill != null) {
				String result = redisDao.setSeckill(seckill);
				System.out.println("缓存存入对象结果:"+result);
			}
			//重试将对象从缓存中读取
			seckill = redisDao.getSeckill(seckillId);
			System.out.println("从缓存中读取对象:"+seckill);
		}
	}
}
```

### 数据库查询测试

`Seckill`类的测试
``` java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

	@Resource
	private SeckillDao seckillDao;
	
	@Test
	public void testReduceNumber() {
		Date date = new Date();
		int count = seckillDao.reduceNumber(1000, date);
		System.out.println("减少:"+count);
	}

	@Test
	public void testQueryById() {
		Seckill seckill = seckillDao.queryById(1000);
		System.out.println(seckill);
	}

	@Test
	public void testQueryAll() {
		List<Seckill> list = seckillDao.queryAll(0, 10);
		System.out.println(list.toString());
	}
}
```

`SuccessSeckill`类的测试

``` java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessSeckillDaoTest {

	@Resource
	private SuccessSeckillDao successSeckillDao;
	
	@Test
	public void testInsertSuccessKill() {
		int result = successSeckillDao.insertSuccessKill(1000, 15516559772L);
		System.out.println("插入的主键为:"+result);
	}

	@Test
	public void testQueryByIdIdWithSeckill() {
		SuccessSeckill  successSeckill = successSeckillDao.queryByIdIdWithSeckill(1000L,15516559772L);
		System.out.println(successSeckill.toString());
	}
}
```

### 服务层测试

``` java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

	//日志
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired	//@Resource也可以实现自动注入
	private SeckillService seckillService;
	
	@Test
	public void testGetSeckillList() {
		List<Seckill> list = seckillService.getSeckillList();
		logger.info("获得秒杀列表:{}",list);
	}

	@Test
	public void testGetById() {
		long id = 1000l;
		Seckill seckill = seckillService.getById(id);
		logger.info("获得秒杀对象:{}",seckill.toString());
	}

	@Test
	public void testExportSeckillUrl() {
		Exposer exposer = seckillService.exportSeckillUrl(1000L);
		logger.info("是否可以执行秒杀请求信息:{}",exposer.toString());
	}

	@Test
	public void testExecuteSeckill() {
		try {
			SeckillExecution seckillExecution = seckillService.executeSeckill(1000L, 15516559773L, "7b5faf7d665a351143ba6a0601cb25a7");
			logger.info("执行秒杀:{}",seckillExecution.toString());
		} catch (RepeatKillException e) {
			logger.error(e.getMessage());
		} catch (SeckillCloseException e) {
			logger.error(e.getMessage());
		} catch (SeckillException e) {
			logger.error(e.getMessage());
		}
	}

	
	@Test
	public void testSeckillLogic() {
		//查询是否开启秒杀
		Exposer exposer = seckillService.exportSeckillUrl(1000L);
		logger.info("是否可以执行秒杀请求信息:{}",exposer.toString());
		//执行秒杀
		try {
			SeckillExecution seckillExecution = seckillService.executeSeckill(1000L, 15516559774L, exposer.getMd5());
			logger.info("执行秒杀:{}",seckillExecution.toString());
		} catch (RepeatKillException e) {
			logger.error(e.getMessage());
		} catch (SeckillCloseException e) {
			logger.error(e.getMessage());
		} catch (SeckillException e) {
			logger.error(e.getMessage());
		}
	}
	
	/*通过存储过程进行秒杀逻辑*/
	@Test
	public void testExecuteSeckillByProcedure() {
		long seckillId = 1000L;
		long userPhone = 15516559778L;
		Exposer exposer = seckillService.exportSeckillUrl(seckillId);	//暴露接口,完成调用
		if (exposer.isExposed()) {
			//如果暴露
			String md5 = exposer.getMd5();
			SeckillExecution seckillExecution = seckillService.executeSeckillByProcedure(seckillId, userPhone, md5);
			logger.info(seckillExecution.getStateInfo());
		}
	}
}```
