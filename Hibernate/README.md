# Hibernate的项目介绍

Tags : JDK8 Eclipse Hibernate

---

[TOC]

---

## 简介

该项目是对Hibernate框架的练习,涉及到框架的映射文件配置,HQL语句查询,注解的使用,缓存的使用.
该项目采用的是`Hibernate4`和`MySQL`数据库,运行环境为`JDK8`.

## 包结构
- `src`源码目录,编写实现代码
	- `annotationBean`基于注解的数据对象模型
	- `bean` 基于配置文件的对象模型
	- `ehcache.xml` 二级缓存的配置文件
	- `hibernate.cfg.xml` Hibernate的全局配置文件
- `test`测试目录,对相应功能进行测试
	- `annotationBean`对注解配置的数据对象进行持久化测试
	- `bean` 对配置文件配置的数据对象进行持久化测试
	- `cache` 测试使用二级缓存
	- `hql` HQL查询语句
	- `session` 数据库持久化Session对象的使用

## 代码编写
**搭建Hibernate的一般步骤:**

 1. 创建配置文件
 2. 创建持久化类
 3. 创建对象关系映射
 4. 根据API编写访问
 5. 使用缓存

### 配置文件信息
在`src`目录下,创建配置文件`hibernate.cfg.xml`其中`<session-factory>`节点中
**常用到配置有:**

| 参数                                   | 推荐值                                           | 说明                          |
| -------------------------------------- | ------------------------------------------------ | ----------------------------- |
| connection.username                    |                                                  | 用户名                        |
| connection.password                    |                                                  | 密码                          |
| connection.driver_class                |                                                  | 数据库驱动                    |
| connection.url                         |                                                  | 数据库连接                    |
| dialect                                | org.hibernate.dialect.MySQLDialect               | 方言设定                      |
| show_sql                               | true                                             | 输出SQL到控制台               |
| format_sql                             | true                                             | 格式化SQL                     |
| hibernate.default_schema               |                                                  | 数据库前缀,区分不同数据库名称 |
| hbm2ddl.auto                           | create \ update \ create-drop \  validate        | 生成方式                      |
| current_session_context_class          | thread \ jta                                           | 事务种类, 本地事务 \ 全局事务        |
| hibernate.cache.use_second_level_cache | true \ false                                     | 是否开启二级缓存              |
| hibernate.cache.region.factory_class   | org.hibernate.cache.ehcache.EhCacheRegionFactory |    插件的实现类                        |

**关联配置对象**
`<mapping resource="XXXX.xml"/>`常用来关联以XML形式配置的实体类
`<mapping class="XXXX.xml"/>`常用来关联以注解形式配置的实体类


``` xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
		"-//Hibernate/Hibernate Configuration DTD 3.0//EN"
		"http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
    	<!-- 连接名称 -->
    	<property name="connection.username">root</property>
    	<!-- 密码 -->
    	<property name="connection.password">123456</property>
    	<!-- 驱动 -->
    	<property name="connection.driver_class">com.mysql.jdbc.Driver</property>
    	<!-- 连接url,指定数据库编码 -->
    	<property name="connection.url">jdbc:mysql:///hibernate?useUnicode=true&amp;characterEncoding=UTF-8</property>
    	<!-- 方言设定 -->
    	<property name="dialect">org.hibernate.dialect.MySQLDialect</property>
    	<!-- 输出SQL -->
    	<property name="show_sql">true</property>
    	<!-- 格式化SQL -->
    	<property name="format_sql">true</property>
    	<!-- 数据库前缀,区分不同数据库名称 -->
    	<property name="hibernate.default_schema">hibernate</property>
    	<!-- 生成方式 -->
    	<property name="hbm2ddl.auto">update</property>
    	<!-- 事务种类 线程事务GetCurrentSession使用 -->
    	<property name="current_session_context_class">thread</property>
    	
    	<!-- 使用二级缓存 -->
  	  	<property name="hibernate.cache.use_second_level_cache">true</property>
  	  	<!-- 二级缓存的工厂类 -->
		<property name="hibernate.cache.region.factory_class">org.hibernate.cache.ehcache.EhCacheRegionFactory</property>
	
	   	<!-- 配置连接的对象关系文件 -->
    	<mapping resource="bean/Student.hbm.xml"/>
    	<mapping resource="bean/Pupil.hbm.xml"/>
    	<mapping resource="bean/grade.hbm.xml"/>
  	    <mapping resource="bean/Employee.hbm.xml"/>
	    <mapping resource="bean/Project.hbm.xml"/>
	    
	    <!-- 以下使用注解完成解析,映射文件使用.分隔 -->
	    <mapping class="annotationBean.AStudent"/>
	    <mapping class="annotationBean.AStudentCard"/>
	    <mapping class="annotationBean.AGrade"/>
	    <mapping class="annotationBean.APupil"/>
	    <mapping class="annotationBean.ATeacher"/>
	    
    </session-factory>
</hibernate-configuration>

```
### Session对象和Transaction开启
`Session`是操作数据库的对象,每个`session`对应一个数据库 `connection`连接, 同一个`connection`在不同时刻可以被多个连接使用.
`Transaction`事务封装了Hibernate对数据库的操作,默认为非自动提交.在进行数据操作时,需要手动开启事物,并进行事物提交,除服修改`dowork()`方法,设置自动提交.

**如何获取`Session`**
- ` openSession()`
- `getCurrentSession()`

**区别:**

1. getCurrentSession需要在事务提交或者回滚后session自动关闭,而openSession需要手动关闭session.如果使用openSession而没有手动关闭session,多次之后会导致连接池溢出.
2. OpenSession每次创建新的session对象,getCurrentSession使用现有的session对象.

测试Session的获取

``` java
public class SessionTest {
	
	
	@Test
	public void testOpenSession(){
	
	//创建配置对象
	Configuration configuration = new Configuration().configure();
	//创建服务注册对象
	ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
	//创建会话工厂
	SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	//创建会话对象，第一种方式。
	Session session = sessionFactory.openSession();
	if(session != null){
		System.out.println("Session创建成功");
		Session session2 = sessionFactory.openSession();
		System.out.println("session是否相同"+(session==session2));
	}else{
		System.err.println("Session创建失败");
	}
	
	
	//开启事物
 	Transaction transaction = session.beginTransaction();
 		//这里写业务代码.....
 	
 	//提交事务
 	transaction.commit();
 	//回滚事务
 	transaction.rollback();
	}
	
	
	@Test
	public void testGetCurrentSession(){
		//创建配置对象
		Configuration configuration = new Configuration().configure();
		//创建服务注册对象
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		//创建会话工厂
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//创建会话对象，第二种方式。
		Session session = sessionFactory.getCurrentSession();
		if(session != null){
			System.out.println("Session创建成功");
			Session session2 = sessionFactory.getCurrentSession();
			System.out.println("session是否相同"+(session==session2));
		}else{
			System.err.println("Session创建失败");
		}
	}
	
	@Test
	public void testSaveStudentWithOpenSession(){
		//创建配置对象
		Configuration configuration = new Configuration().configure();
		//创建服务注册对象
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		//创建会话工厂
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//创建会话对象，第一种方式。
		Session session1 = sessionFactory.openSession();
		//开启事物
		Transaction transaction1 = session1.beginTransaction();
		//创建对象
		Student student = new Student(1, "张三", "女", new Date(), "上海");
		session1.doWork(new Work() {
			
			@Override
			public void execute(Connection connection) throws SQLException {
				System.out.println("数据库连接的Hash码:"+connection.hashCode());
			}
		});
		//保存,但是不关闭事务
		session1.save(student);
		//session1.close();
		//提交事务
		transaction1.commit();
		
		
		//创建会话对象，第一种方式。
		Session session2 = sessionFactory.openSession();
		//开启事物
		Transaction transaction2 = session2.beginTransaction();
		//创建对象
		Student student2 = new Student(2, "李四", "男", new Date(), "上海");
		session2.doWork(new Work() {
			
			@Override
			public void execute(Connection connection) throws SQLException {
				System.out.println("数据库连接的Hash码:"+connection.hashCode());
			}
		});
		//保存,但是不关闭事务
		session2.save(student2);
		//session2.close();
		//提交事务
		transaction2.commit();
		
	}
	
	
	@Test
	public void testSaveStudentWithGetCurrentSession(){
		//创建配置对象
		Configuration configuration = new Configuration().configure();
		//创建服务注册对象
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		//创建会话工厂
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//创建会话对象，第一种方式。
		Session session1 = sessionFactory.getCurrentSession();
		//开启事物
		Transaction transaction1 = session1.beginTransaction();
		//创建对象
		Student student = new Student(1, "张三", "女", new Date(), "上海");
		session1.doWork(new Work() {
			
			@Override
			public void execute(Connection connection) throws SQLException {
				System.out.println("数据库连接的Hash码:"+connection.hashCode());
			}
		});
		//保存,但是不关闭事务
		session1.save(student);
		//session1.close();
		//提交事务
		transaction1.commit();
		
		//创建会话对象，第一种方式。
		Session session2 = sessionFactory.getCurrentSession();
		//开启事物
		Transaction transaction2 = session2.beginTransaction();
		//创建对象
		Student student2 = new Student(2, "李四", "男", new Date(), "上海");
		session2.doWork(new Work() {
			
			@Override
			public void execute(Connection connection) throws SQLException {
				System.out.println("数据库连接的Hash码:"+connection.hashCode());
			}
		});
		//保存,但是不关闭事务
		session2.save(student2);
		//session2.close();
		//提交事务
		transaction2.commit();
	}
}
```

### 单表查询
封装了多种JavaBean,满足不同情况下对关联关系的要求.

**对象模型**
`Student`类和`Address`类分别为数据模型和其组件属性,映射为数据库中`student`表的字段.
其中`Student`类通过私有属性引用`Address`对象,实现将其作为组件,在配置文件`Student.hbm.xml`中,通过`<component>`标签将对象的属性映射为主表的字段.

**属性配置**
`Student`类对应的配置文件为`Student.hbm.xml`,配置文件声明了对象属性和数据库字段的关系.

| 标签                | 属性             | 说明           |
| ------------------- | ---------------- | -------------- |
| <hibernate-mapping> |                  | 根标签         |
|                     | schema           | 模式名         |
|                     | catalog          | 目录名称       |
|                     | default- cascade | 级联风格       |
|                     | default-access   | 访问策略       |
|                     | default-lazy     | 加载策略       |
|                     | package          | 包名           |
| <class>             |                  | 映射类标签     |
|                     | name             | 映射类         |
|                     | table            | 映射表         |
|                     | batch_size       | 抓取条数       |
|                     | where            | 抓取条件       |
|                     | entity-name      | 同一个类映射表 |
| <id>                |                  | 主键标签       |
|                     | name             | 属性名         |
|                     | type             | 属性类型       |
|                     | column           | 字段名         |
|                     | length           | 长度           |
|                     | generator        | 主键生成策略   |
| <component>         |                  | 组件标签       |
|                     | name             | 属性名         |
|                     | class            | 映射类         |
| <property>          |                  | 映射关系       |
|                     | name             | 属性名         |
|                     | column           | 数据库字段     |


常用的主键生成策略

| 生成策略    | 说明                                        |
| ----------- | ------------------------------------------- |
| increment   | 代理主键.Hibernate自动递增生成              |
| identity    | 代理主键.底层数据库生成                     |
| sequence    | 代理主键.Oracle数据库自增序列               |
| hilo        | 代理主键.high/low生成标示符                 |
| seqhilo     | 代理主键.high/low生成long,int,short类型主键 |
| native      | 代理主键.自动选择identity / sequence / hilo |
| uuid.hex    | 代理主键.使用128位的UUID                    |
| uuid.string | 代理主键.使用16字符长度的UUID               |
| assigned    | 自然主键,应用指定                           |
| foreign     | 代理主键.使用相关联的对象表示               |

映射类型

| Hibernate类型             | Java类型                                  | SQL类型   | 大小     |
| ------------------------- | ----------------------------------------- | --------- | -------- |
| integer/int               | java.lang.Integer/int                     | INTEGER   | 4字节    |
| long                      | java.lang.Long/long                       | BIGINT    | 8字节    |
| short                     | java.lang.Short/short                     | SMALLINT  | 2字节    |
| byte                      | java.lang.Byte/byte                       | TINYINT   | 1字节    |
| float                     | java.lang.Float/float                     | FLOAT     | 4字节    |
| double                    | Java.lang.Double/double                   | DOUBLE    | 8字节    |
| big_ decimal              | java.math.BigDecimat                      | NUMERIC   |          |
| character                 | java.lang.Character/java.lang.String/char | CHAR(1)   | 定长字节 |
| string                    | java.lang.String                          | VARCHAR   | 变长字节 |
| boolean/yes_no/true_false | java.lang.Boolean/Boolean                 | BIT       | 布尔类型 |
| date                      | java.until.Date/java.sql.Date             | DATE      | 日期 yyyy-MM-dd    |
| time						| java.until.Date/java.sql.Time				| TIME		|时间	 hh:mi:ss	|		
| timestamp                 | java.until.Date/java.until.Timestamp      | TIMESTAMP | 日期和时间   yyyyMMddhhminss |
| calendar                  | java.until.Calendar                       | TIMESTAMP | 日期和时间   yyyyMMddhhminss    |
| calendar_date             | java.until.Calendar                       | DATE      | 日期   yyyy-MM-dd  |

对象类型

| 映射类型 | java类型         | SQL类型         | MySQL类型 | Oracle类型 |
| -------- | ---------------- | --------------- | --------- | ---------- |
| binary   | byte[]           | VARCHAR /  BLOB | BLOB      | BLOB       |
| text     | java.lang.String | CLOB            | TEXT      | CLOB       |
| clob     | java.sql.CLOB    | CLOB            | TEXT      | CLOB       |
| blob     | java.sql.BLOB    | BLOB            | BLOB      | BLOB       |

``` xml
<hibernate-mapping>
    <class name="bean.Student" table="STUDENT">
       
        <id name="sid" type="int">
            <column name="SID" />
            <generator class="assigned" />
        </id>
        <property name="sname" type="java.lang.String">
            <column name="SNAME" />
        </property>
        <property name="gender" type="java.lang.String">
            <column name="GENDER" />
        </property>
        <property name="birthday" type="java.util.Date">
            <column name="BIRTHDAY" />
        </property>
        <property name="addree" type="java.lang.String">
            <column name="ADDREE" />
        </property>
        <property name="picture" type="java.sql.Blob">
            <column name="PICTURE" />
        </property>
		<component name="address" class="bean.Address">
			<property name="postCode" column="POSTCODE"/>
			<property name="phone" column="PHONE"/>
			<property name="address" column="ADDRESS"/>
		</component>
    </class>
</hibernate-mapping>
```


**查询方法**

- 涉及到`Student` 类和`Address`分别作为数据模型和属性对象.
- 在`Student`类中,通过引用私有`Address`类作为对象属性,实现组合属性,将属性对象类的属性映射为数据库中的字段.
- 在各个`@Test`方法之前,使用`@Before`的`init`和`@After`分别实现开启资源的加载和资源的清理.
- `get()`和`load()`的区别:
	- 在不考虑缓存下,`get()`在调用后会立刻向数据库发出SQL语句,返回持久化对象
	- `load()`方法返回一个代理对象,该代理对象只保存了实体对象的ID,直到使用对象非主键属性时才会发出SQL语句
		- 查询数据不存在时,`get()`返回`null`;`load()`抛出` org.hibernate.ObjectNot Found Exception`


| 方法                    | 介绍                      | 说明                                                                             |
| ----------------------- | ------------------------- | -------------------------------------------------------------------------------- |
| init()                  | 创建Session对象,开启事物  | `@ Before`注解标注                                                               |
| destory()               | 提交事务,关闭资源         | `@After`注解标注                                                                 |
| testStudent()           | 保存对象,改为自动提交事务 | 需要将`destory`方法中的`transaction.commit()`自动提交注释                        |
| testWriteBlob()         | 将二进制文件写入数据库    |                                                                                  |
| testReadBlod()          | 从数据库中读取二进制文件  |                                                                                  |
| testStudentAndAddress() | 使用组件属性              | `Student`类中私有`Address`类的对象作为其属性,并在配置映射中将`Address`的属性映射为数据库表字段 |
| testGetStudent()        | `get()`方法查询           |                                                                                  |
| testLoadStudent()       | `load`方法查询            |                                                                                  |
| testGetUpdateStudent()  | 更新对象                  |                                                                                  |
| testDeleteStudent()     |     删除对象     |                                                                                  |

- 
**注意:** 当改为自动提交时,需要在`@After`中将`transaction.commit()`注释

``` java
/**
 *	学生类的测试类
 */
public class StudentTest{
	
	//会话工厂
	private SessionFactory sessionFactory;
	//会话对象
	private Session session;
	//事物对象
	private Transaction transaction;
	
	/*
	 * 	测试方法前执行
	 */
	@Before
	public void init() {
		//创建配置对象
		Configuration configuration = new Configuration().configure();
		//创建服务注册对象
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		//创建会话工厂
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//创建会话对象
		session = sessionFactory.openSession();
		//开启事物
		transaction = session.beginTransaction();
	}
	
	/*
	 * 	测试方法后执行
	 */
	@After
	public void destory() {
		//事物提交
		transaction.commit();			//当改为自动提交时,需要将其注释掉
		//关闭会话 
		session.close();
		//关闭工厂
		sessionFactory.close();
	}
	
	/*
	 * 	测试保存学生
	 */
	@Test
	public void testStudent(){
		//创建学生对象
		Student student = new Student(1,"张三","男",new Date(),"河南");
		
		//改为自动提交事务
		session.doWork(new Work() {
			
			@Override
			public void execute(Connection connection) throws SQLException {
				connection.setAutoCommit(true);
			}
		});
		//保存对象
		session.save(student);
		//强制输出SQL语句
		session.flush();
		//注意:需要将transaction.commit()自动提交注释
	}
	
	/*
	 * 	测试保存对象类型数据
	 * 	向数据库存入的照片最大为1M
	 */
	@Test
	public void testWriteBlob() throws Exception{
		//创建学生对象
		Student student = new Student(1,"小简","女",new Date(),"上海");
		//获得文件
		File file = new File("F:"+File.separator+"JAVA_WorkSpace"+File.separator+"Hibernate"+File.separator+"picture"+File.separator+"jym.jpg");
		//获取输入流
		InputStream inputStream = new FileInputStream(file);
		//创建Blob对象,传入session创建对象,进而创建Blob对象
		Blob picture = Hibernate.getLobCreator(session).createBlob(inputStream, inputStream.available());
		student.setPicture(picture);
		//保存对象	
		session.save(student);
	}
	
	/**
	 * 	读取数据库中二进制文件
	 */
	@Test
	public void testReadBlod()throws Exception{
		Student student = (Student) session.get(Student.class, 1);
		//获取Blob对象
		Blob picture = student.getPicture();
		//获取输入流
		InputStream inputStream = picture.getBinaryStream();
		//创建输出文件,获得输出流
		File file = new File("F:"+File.separator+"JAVA_WorkSpace"+File.separator+"Hibernate"+File.separator+"picture"+File.separator+"jym2.jpg");
		OutputStream outputStream = new FileOutputStream(file);
		//读取,誊写
		byte[] buff = new byte[inputStream.available()];
		inputStream.read(buff);
		outputStream.write(buff);
		inputStream.close();
		outputStream.close();
	}
	
	/*
	 * 	测试保存学生及其组件属性
	 */
	@Test
	public void testStudentAndAddress(){
		//创建学生对象
		Student student = new Student(2,"张谦","男",new Date(),"河南");
		student.setAddress(new Address("451300", "15516559772", "河南科技学院"));
		//保存对象
		session.save(student);
	}
	
	/*
	 * 	GET查询方法
	 */
	@Test
	public void testGetStudent(){
		Student student = (Student)session.get(Student.class, 1);
		System.out.println("真实类名:"+student.getClass().getName());
		System.out.println("查询出:"+student.toString());
	}
	
	/*
	 * 	Load查询方法
	 */
	@Test
	public void testLoadStudent(){
		Student student = (Student)session.load(Student.class, 1);
		System.out.println("代理类名:"+student.getClass().getName());
		System.out.println("查询出:"+student.toString());
	}
	
	/*
	 * 	GET查询后更新
	 */
	@Test
	public void testGetUpdateStudent(){
		Student student = (Student)session.get(Student.class, 1);
		student.setAddree("浙江");
		System.out.println("更新:"+student.toString());
	}
	
	/*
	 * 	删除方法
	 */
	@Test
	public void testDeleteStudent(){
		Student student = (Student)session.get(Student.class, 1);
		session.delete(student);
		System.out.println("删除:"+student.toString());
	}
}
```

###  一对多双向关联查询
**对象模型**
这里使用`Grade`表示班级为一方,`Pupil`表示学生为多方.
在一方私有`set<Pupil>`集合对象(实例化),实现对多方的引用;
在多方私有一方对象(无需实例化),实现对一方的引用,


**属性配置**

| 标签                | 属性    | 说明                                        |
| ------------------- | ------- | ------------------------------------------- |
| &lt;set&gt;         |         | 在一方中使用,配置对多方的引用               |
|                     | name    | 映射多方的集合属性名                        |
|                     | table   | 多方的数据库表                              |
|                     | inverse | 控制反转,一般为true,交由多方维护            |
|                     | lazy    | 是否懒加载                                  |
|                     | cascade | 级联操作  all / save-update / delete / none |
| &lt;key&gt;         |         | 在一方`set`标签下指定关联的多方外键字段     |
|                     | column  | 多方的外键字段,将会追加到表字段中.          |
| &lt;one-to-many&gt; |         | 在一方`set`标签下设定一对多配置             |
|                     | class   | 多方的类                                    |
| &lt;many-to-one&gt; |         | 在多方使用,配置对一方的引用                 |
|                     | name    | 引用的属性                                  |
|                     | class   | 引用的类名                                  |
|                     | column  | 字段名                                      |
|                     | cascade | 级联操作                                    |


`Grade`班级一方配置

``` xml
<hibernate-mapping>
    <class name="bean.Grade" table="GRADE">
    	<!-- 主键为该表的唯一 -->
        <id name="gid" type="int">
            <column name="GID" />
            <generator class="assigned" />
        </id>
        <property name="gname" type="java.lang.String">
            <column name="GNAME" length="20" not-null="true" />
        </property>
        <property name="gdescribe" type="java.lang.String">
            <column name="GDESCRIBE" />
        </property>
        
        <!-- 配置单向的一对多关联关系 name:映射属性名称	table:多方的数据库表	inverse:控制反转,由多方维护可提高效率    lazy:是否懒加载   	cascade:级联操作-->
        <set name="pupils" table="PUPIL" inverse="true" lazy="true" cascade="save-update">
            <!-- 指定关联的外键列 -->
           	<!-- Pupil类的外键类,在这里对其赋值 -->
            <key column="GID"/>
            <!-- 对应多方的类 -->
            <one-to-many class="bean.Pupil" />
        </set>
    </class>
</hibernate-mapping>
```

`Pupil`学生多方配置

``` xml
	<!-- name:类名			table:表名 -->
    <class name="bean.Pupil" table="PUPIL">

        <!-- 开启二级缓存,usage:缓存策略	include:是否包含懒加载	region:为该缓存起别名,用于单独控制 -->
       	<cache usage="read-only" include="non-lazy" region="PUPIL"/>
    
    	<!-- 主键信息 -->
        <id name="pid" type="int">
            <column name="PID" length="20" not-null="true" />
            <generator class="assigned" />
        </id>
        <property name="pname" type="java.lang.String">
            <column name="PNAME" />
        </property>
        <property name="psex" type="java.lang.String">
            <column name="PSEX" />
        </property>
        
        <!-- 多对一的配置  name:引用的属性名	class:引用的类		column:引用的外键属性,外键不用单独在上面创建字段	cascade:级联操作-->
        <many-to-one name="grade" class="bean.Grade" column="gid" cascade="all"/>
    </class>
```
**查询**
`Grade`一方的测试

| 方法               | 介绍                                  | 说明 |
| ------------------ | ------------------------------------- | ---- |
| init()             | 创建Session对象,开启事物              |      |
| destory()          | 提交事务,关闭资源                   |      |
| addPupil()         | 通过级联保存                          |      |
| findPupilByGrade() | 通过在一方中调用对多方的引用,实现查询 |      |
| updatePupil()      | 级联更新                              |      |
| deletePupil()      |                                       |      |

``` java
public class GradeTest {

	private  SessionFactory sessionFactory;
	private  Session session;
	private  Transaction transaction;
	/*
	 * 	测试方法前
	 */
	@Before
	public void init(){
		//创建Configuration对象,读取Hibernate的哦配置文件
		Configuration configuration = new Configuration().configure();
		
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		//创建会话工厂
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//创建会话对象
		session = sessionFactory.openSession();
		//开启事物
		transaction = session.beginTransaction();
		
	}

	
	/*
	 * 	测试方法后执行
	 */
	@After
	public void destory() {
		//事物提交
		transaction.commit();
		//关闭会话 
		session.close();
		//关闭工厂
		sessionFactory.close();
	}
	
	/*增加学生*/
	@Test
	public void	 addPupil() {
		//创建班级
		Grade grade = new Grade();
		grade.setGid(1);
		grade.setGname("大一");
		grade.setGdescribe("新来的小鲜肉");
		
		//创建学生
		Pupil pupil1 = new Pupil(1,"张三","男",1);
		Pupil pupil2 = new Pupil(2,"李四","男",1);
		Set<Pupil> pupils = new HashSet<Pupil>();
		pupils.add(pupil1);
		pupils.add(pupil2);
		
		//添加学生们
		grade.setPupils(pupils);
		
		//保存
		session.save(grade);
		session.save(pupil1);
		session.save(pupil2);
	}
	
	
	/*查询学生的信息,通过多方查询单方的信息,进行导航查询*/
	@Test
	public void findPupilByGrade() {
		Grade grade = (Grade)session.get(Grade.class, 1);
		System.out.println("查询到班级"+grade.toString());
		Set<Pupil> pupils = grade.getPupils();
		for (Pupil pupil : pupils) {
			System.out.println("每个学生信息"+pupil.toString());
		}
	}
	
	/*修改学生表的一个人,变为其他年级*/
	@Test
	public void updatePupil(){
		//创建二年级
		Grade grade = new Grade();
		grade.setGid(2);
		grade.setGname("大二");
		grade.setGdescribe("变成老油条了");
		//获得学生
		Pupil pupil = (Pupil) session.get(Pupil.class, 1);
		//修改年级
		pupil.setGid(2);
		//增加学生到二年级
		grade.getPupils().add(pupil);
		//保存主表和从表
		session.save(grade);
		session.save(pupil);
	}
	
	/*删除学生表的信息,删除从表*/
	@Test
	public void deletePupil(){
		Pupil pupil = (Pupil) session.get(Pupil.class, 2);
		session.delete(pupil);
	}
}
```

`Pupil`多方的测试

| 方法                   | 介绍     | 说明                     |
| ---------------------- | -------- | ------------------------ |
| init()                 |    创建Session对象,开启事物        |                          |
| destory()              |     提交事务,关闭资源     |                          |
| addPupil()             | 新增     |                          |
| addPupilByReciprocal() | 级联新增 | 通过保存一方级联保存多方 |
| findGradeByPupil()     |          |                          |

``` javascript
public class PupilTest {

	private  SessionFactory sessionFactory;
	private  Session session;
	private  Transaction transaction;
	/*
	 * 	测试方法前
	 */
	@Before
	public void init(){
		//创建Configuration对象,读取Hibernate的哦配置文件
		Configuration configuration = new Configuration().configure();
		
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		//创建会话工厂
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//创建会话对象
		session = sessionFactory.openSession();
		//开启事物
		transaction = session.beginTransaction();
		
	}
	/*
	 * 	测试方法后执行
	 */
	@After
	public void destory() {
		//事物提交
		transaction.commit();
		//关闭会话 
		session.close();
		//关闭工厂
		sessionFactory.close();
	}
	
	/*测试多对一的配置*/
	@Test
	public void addPupil(){
		//创建班级
		Grade grade = new Grade();
		grade.setGid(3);
		grade.setGname("大三");
		grade.setGdescribe("老腊肉");
		//创建学生
		Pupil pupil1 = new Pupil(3, "张三", "男", 3);
		Pupil pupil2 = new Pupil(4, "李四", "男", 3);
		//设置关联关系
		pupil1.setGrade(grade);
		pupil2.setGrade(grade);
		//保存
		session.save(grade);
		session.save(pupil1);
		session.save(pupil2);
	}
	
	/*测试一/多相互关系.*/
	@Test
	public void addPupilByReciprocal(){
		//创建班级
		Grade grade = new Grade();
		grade.setGid(4);
		grade.setGname("大四");
		grade.setGdescribe("该滚蛋了");
		//创建学生
		Pupil pupil1 = new Pupil(5, "张三", "男", 3);
		Pupil pupil2 = new Pupil(6, "李四", "男", 3);
		//设置关联关系,相互关系
		grade.getPupils().add(pupil1);
		grade.getPupils().add(pupil2);
		pupil1.setGrade(grade);
		pupil2.setGrade(grade);
		//保存
		session.save(grade);
//		session.save(pupil1);
//		session.save(pupil2);
	}	
	
	/*查询学生所在班级的信息*/
	@Test
	public void findGradeByPupil(){
		Pupil pupil = (Pupil) session.get(Pupil.class, 5);
		Grade grade = pupil.getGrade();
		System.out.println("学生信息:"+pupil.toString());
		System.out.println("班级信息"+grade.toString());
	}
}
```

### 多对多双向关联查询
**对象模型**
`Employee`员工类,私有`Set<Project>`属性.
`Project`项目类,私有`Set<Employee>`属性.

**属性配置**

| 标签           | 属性    | 说明                 |
| -------------- | ------- | -------------------- |
| &lt;set&gt;          |         | 多方引用多方的集合   |
|                | name    |                      |
|                | table   |                      |
|                | cascade |                      |
| &lt;key&gt;          |         | 中间表对应的外键信息 |
|                | column  | 中间表的字段         |
| &lt;many-to-many&gt; |         | 多对多标签配置       |
|                | class   | 引用的多方的类       |
|                | column  | 多方的关联字段       |

`Employee`员工类配置

``` xml
<hibernate-mapping>
    <class name="bean.Project" table="PROJECT">
        <id name="proId" type="int">
            <column name="PROID" />
            <generator class="assigned" />
        </id>
        <property name="proName" type="java.lang.String">
            <column name="PRONAME" />
        </property>
        
        <!-- 配置多对多的关系 name:多方的属性 table:中间表 -->
        <set name="employees" table="PROEMP" cascade="all">
        	<!-- 外键:中间表的对应 -->
        	<key column="RPROID"/>
        	<many-to-many class="bean.Employee" column="rempId" />
        </set>
    </class>
</hibernate-mapping>
```


`Project`项目类配置

``` xml
<hibernate-mapping>
    <class name="bean.Employee" table="EMPLOYEE">
        <id name="empId" type="int">
            <column name="EMPID" />
            <generator class="assigned" />
        </id>
        <property name="empName" type="java.lang.String">
            <column name="EMPNAME" />
        </property>
        <set name="projects" table="PROEMP" inverse="true">
        	<!-- 中间表的外键 -->
            <key column="REMPID"/>
            <many-to-many class="bean.Project" column="RPROID" />
        </set>
    </class>
</hibernate-mapping>
```



**查询**

| 方法      | 介绍                 | 说明 |
| --------- | -------------------- | ---- |
| init()    | 获取Session,开启事物 |      |
| destory() | 提交事务,关闭资源    |      |
| add()     | 新增记录             |      |

``` java
public class EmployeeProjectTest {
	private  SessionFactory sessionFactory;
	private  Session session;
	private  Transaction transaction;
	
	/* 测试方法前 */
	@Before
	public void init(){
		//创建Configuration对象,读取Hibernate的哦配置文件
		Configuration configuration = new Configuration().configure();
		
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		//创建会话工厂
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//创建会话对象
		session = sessionFactory.openSession();
		//开启事物
		transaction = session.beginTransaction();
	}
	
	/*测试方法后执行*/
	@After
	public void destory() {
		//事物提交
		transaction.commit();
		//关闭会话 
		session.close();
		//关闭工厂
		sessionFactory.close();
	}
	
	/*关联关系由Project进行维护,同时保存Project时会进行级联保存*/
	@Test
	public void add(){
		Project project1 = new Project(1, "项目一");
		Project project2 = new Project(2, "项目二");
		Employee employee1 = new Employee(1, "张三");
		Employee employee2 = new Employee(2, "李四");
		
		//参加项目一的有张三和李四
		project1.getEmployees().add(employee1);
		project1.getEmployees().add(employee2);
		//参加项目二的只有李四
		project2.getEmployees().add(employee2);
		//保存项目,级联保存员工
		session.save(project1);	
		session.save(project2);
	}
}
```

### HQL查询语句
面型对象的查询语言,查询映射配置的持久化类及其属性
**注意:** 查询语句大小写敏感.

| 方法                          | 介绍                  | 说明                                                                |
| ----------------------------- | --------------------- | ------------------------------------------------------------------- |
| init()                        | 获取Session,开启事物  |                                                                     |
| destory()                     | 提交事务,关闭资源     |                                                                     |
| testFromStudent()             | 查找一方所有对象      | 返回一个List<student></student>                                     |
| testFromGrade()               | 查询多方所有对象      | 返回一个List<grade></grade>                                         |
| testSelectClauseObjectArray() | 查询对象属性,返回数组 | 封装每个对象属性到对象数组中,返回一个List&lt;Object[]&gt;           |
| testSelectClauseObject()      | 查询对象属性,返回对象 | 当查询属性唯一时,返回一个List<object></object>                      |
| testSelectClauseList()        | 查询对象属性,返回列表 | 封装每个对象属性到List中,返回List<list></list>                      |
| testSelectClauseMap()         | 查询对象属性,返回映射 | 封装每个对象属性到Map中,通过别名,属性名,顺序进行获取                |
| testSelectClauseConstructor() | 查询对象属性,返回对象 | 构造函数与查询属性一致,返回List<student></student>                  |
| testDistinct()                | 去重查询              | 使用<code>distinct</code>关键字去重查询                             |
| testWhereClauseMathLogic()    | 条件查询              | 根据查询条件进行数值逻辑查询                                        |
| testWhereClauseDecideNull()   | 空值查询              | <code>is null</code> 和 <code>is not null</code>进行空值查询        |
| testWhereClauseScope()        | 范围查询              | 使用<code>between .. and ..</code>进行查询,查询是一个闭区间         |
| testWhereClauseRange()        | 条件列表查询          | 使用<code>in</code> 和 <code>not in</code>进行条件查询              |
| testWhereClauseLike()         | 模糊查询              | 使用<code>like</code>配合<code>_</code>和<code>%</code>进行模糊查询 |
| testWhereClauseLogic()        | 逻辑查询              | 使用`and`,`or`,`not`进行逻辑查询                                    |
| testWhereClauseAssemble()     | 集合查询              | 使用`is empty`和`is not empty`对对象属性集合进行空值判断            |
| testWhereClauseArithmetic()   | 四则预算查询          | 可以对数值型属性进行数字条件约束                                    |
| testWhereClauseSingle()       | 单行记录查询          | 对返回结果唯一的进行查询                                            |
| testWhereClauseOrder()        | 排序查询              |    对查询结果进行排序   |

``` java
@SuppressWarnings({"unchecked","rawtypes"})
public class StudentHQLTest {

	//会话工厂
	private SessionFactory sessionFactory;
	//会话对象
	private Session session;
	//事物对象
	private Transaction transaction;
	
	/*测试方法前执行*/
	@Before
	public void init() {
		//创建配置对象
		Configuration configuration = new Configuration().configure();
		//创建服务注册对象
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		//创建会话工厂
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//创建会话对象
		session = sessionFactory.openSession();
		//开启事物
		transaction = session.beginTransaction();
	}
	
	/*测试方法后执行*/
	@After
	public void destory() {
		//事物提交
		transaction.commit();
		//关闭会话 
		session.close();
		//关闭工厂
		sessionFactory.close();
	}
	
	/*查询所有对象*/
	@Test
	public void testFromStudent() {
		//不需要指定全类型,直接引入类名;缺省由Hibernate框架的映射补全
		String hql = "from Student as s";			//可以使用别名
		Query query = session.createQuery(hql);		//查询结果封装在Query中
		List<Student> students = query.list();		//已知查询结果为List
		for (Student student : students) {
			System.out.println("查询到学生:"+student.toString());
		}
	}
	
	/*查询一方所有对象,包含多方对象,程序默认不查询,除非关闭懒加载;这里因为用到,所以都查询*/
	@Test
	public void testFromGrade() {
		String hql = "from bean.Grade";		//可以使用全类型
		Query query = session.createQuery(hql);		//查询结果封装在Query中
		List<Grade> grades = query.list();		//已知查询结果为List
		for (Grade grade : grades) {
			System.out.println("查询到学生:"+grade.toString());
		}
	}
	
	/*以对象数组形式返回一个List*/
	@Test
	public void testSelectClauseObjectArray(){
		String hql = "select s.sid,s.sname from Student s";		//省略as使用别名
		Query query = session.createQuery(hql);
		List<Object[]> list = query.list();
		for (Object[] objects : list) {
			System.out.println("学生编号:"+objects[0]+"\t"+"学生姓名:"+objects[1]);
		}
	}
	
	/*以对象形式返回一个List	当查询的为一个属性时,返回的为对象*/
	@Test
	public void testSelectClauseObject(){
		String hql = "select s.sname from Student s";		//省略as使用别名
		Query query = session.createQuery(hql);
		List<Object> list = query.list();
		for (Object object : list) {
			System.out.println("学生姓名:"+object);
		}
	}
	
	/*以List集合形式返回一个List*/
	@Test
	public void testSelectClauseList() {
		String hql = "select new list(s.sid,s.sname) from Student s";
		Query query = session.createQuery(hql);
		List<List> lists = query.list();
		for (List list : lists) {
			System.out.println("学生编号:"+list.get(0)+"\t"+"学生姓名:"+list.get(1));
		}
	}
	
	/*以Map集合形式返回一个List*/
	@Test
	public void testSelectClauseMap() {
		String hql = "select new map(s.sid as id,s.sname) from Student s";
		Query query = session.createQuery(hql);
		List<Map> lists = query.list();
		for (Map map : lists) {		//遍历每一个封装的map对象
			//可以通过别名或者序号获取Map的封装属性,注意传入的是一个String类型的
			System.out.println("学生编号:"+map.get("id")+"\t"+"学生姓名:"+map.get("1"));	
		}
	}
	
	/*以自定义类型返回一个List;需要在持久化类中创建相应的构造器,在select子句中调用定义的构造器*/
	@Test
	public void testSelectClauseConstructor() {
		String hql = "select new Student(s.sid ,s.sname) from Student s";
		Query query = session.createQuery(hql);
		List<Student> students = query.list();
		for (Student student : students) {
			System.out.println("学生编号:"+student.getSid()+"\t"+"学生姓名:"+student.getSname());
		}
	}	
	
	/*查询去重,使用distinct关键字*/
	@Test
	public void testDistinct(){
		String hql = "select distinct s.gender from Student s";
		Query query = session.createQuery(hql);
		List<Object> objects = query.list();
		for (Object object : objects) {
			System.out.println(object);
		}
	}
	
	/*查询条件子句,判断大小关系*/
	@Test
	public void testWhereClauseMathLogic(){
		String hql = "from Student s where s.sid<2";
		Query query = session.createQuery(hql);
		List<Student> students = query.list();
		for (Student student : students) {
			System.out.println("查询到学生:"+student.toString());
		}
	}
	
	/*查询条件子句,判断是否为空*/
	@Test
	public void testWhereClauseDecideNull(){
		String hql = "from Student s where s.sid is not null";
//		String hql = "from Student s where s.sid <> null";		//也可以
		Query query = session.createQuery(hql);
		List<Student> students = query.list();
		for (Student student : students) {
			System.out.println("查询到学生:"+student.toString());
		}
	}
	
	/*查询条件子句,范围测试*/
	@Test
	public void testWhereClauseScope(){
		String hql = "from Student s where s.sid between 1 and 2";	//查询是个闭区间
//		String hql = "from Student s where s.sid not between 1 and 2";	
		Query query = session.createQuery(hql);
		List<Student> students = query.list();
		for (Student student : students) {
			System.out.println("查询到学生:"+student.toString());
		}
	}
	
	/*查询条件子句,罗列条件测试*/
	@Test
	public void testWhereClauseRange(){
		String hql = "from Student s where s.sid in (1,3,5)";
//		String hql = "from Student s where s.sid not in (1,3,5)";
		Query query = session.createQuery(hql);
		List<Student> students = query.list();
		for (Student student : students) {
			System.out.println("查询到学生"+student.toString());
		}
	}
	
	/*查询条件子句,模糊查询,需要用单引号对其进行测试*/
	@Test
	public void testWhereClauseLike(){
//		String hql = "from Student s where s.sname like '张_'";
		String hql = "from Student s where s.sname like '%'";	//全部查询
		Query query = session.createQuery(hql);
		List<Student> students = query.list();
		for (Student student : students) {
			System.out.println("查询到学生:"+student.toString());
		}
	}
	
	/*查询条件子句,逻辑查询*/
	@Test
	public void testWhereClauseLogic(){
//		String hql = "from Student s where s.sname like '张_' and s.sid=2";		//逻辑与
		String hql = "from Student s where s.sname like '张_' or s.sid=1";		//逻辑或
		Query query = session.createQuery(hql);
		List<Student> students = query.list();
		for (Student student : students) {
			System.out.println("查询到学生:"+student.toString());
		}
	}
	
	/*查询条件子句,集合运算,对一对多的多方集合进行查询是否为空*/
	@Test
	public void testWhereClauseAssemble(){
		String hql = "from Grade g where g.pupils is not empty";		
		Query query = session.createQuery(hql);
		List<Grade> grades = query.list();
		for (Grade grade : grades) {
			System.out.println("查询到学生:"+grade.toString());
		}
	}
	
	/*查询条件子句,四则运算*/
	@Test
	public void testWhereClauseArithmetic(){
		String hql = "from Student s where s.sid < (2+1)*3";	
		Query query = session.createQuery(hql);
		List<Student> students = query.list();
		for (Student student : students) {
			System.out.println("查询到学生:"+student.toString());
		}
	}
	
	/*查询条件子句,单个查询,要求查询结果为单一或者为空*/
	@Test
	public void testWhereClauseSingle(){
		String hql = "from Student s where s.sid =1";		//注意条件的控制
		Query query = session.createQuery(hql);
		Student student = (Student) query.uniqueResult();
		System.out.println("查询到学生:"+student.toString());
	}
	
	/*查询条件子句,进行排序 desc:降序	asc:升序*/
	@Test
	public void testWhereClauseOrder(){
		String hql = "from Student s order by s.sid asc,s.birthday desc";	//多个条件间用逗号分隔	
		Query query = session.createQuery(hql);
		List<Student> students = query.list();
		for (Student student : students) {
			System.out.println("查询到学生:"+student.toString());
		}
	}
}
```

### 缓存配置
为了降低应用程序对物理数据源的访问的频次.从而提高应用程序的运行性能的一种策略.

**一级缓存**
- 又被称为`Session`缓存,`会话缓存`.
- 通过`Session`查询时,会将查询结果进行缓存,下一次查询时会直接从内存中查询,减少对数据库命中.
- 一级缓存生命周期与`Session`生命周期一致.
- 一级缓存适用范围仅限于当前会话中.
- 一级缓存默认开启
	- ` evict()`:将某个对象从缓存中删除
	- ` clear()`清除全部缓存

**二级缓存**
- 默认关闭,需要配置打开,支持多插件实现.默认EHCache实现,还有 Hashtable,JBoss等.

**两者对比**

|              | 一级缓存                      | 二级缓存                                 |
| ------------ | ----------------------------- | ---------------------------------------- |
| 缓存的范围   | 事务范围,每个事物具有一级缓存 | 应用范围,当前应用内所有事物共享          |
| 并发访问策略 | 不会出现并发问题              | 必须提供相应的并发策略                   |
| 数据过期策略 | 无数据过期策略                | 缓存对象的最大数目,最长时间,最长空闲时间 |
| 缓存实现     | 框架包含                      | 第三方提供,可插拔                        |
| 物理介质     | 内存                          | 内存和硬盘                               |
| 启用方式     | 默认开启,不可关闭             | 默认不启动,选择性开启                    |
注意:缓存策略为cache usage="read-only"

**开启方式**

 1. 加载相应jar包
 2. 在Hibernate配置文件中添加 Provider类描述
 3. 添加缓存文件配置
 4. 需要被缓存的表的配置文件中添加`<chche/>`标签

Provider类描述,将其追加在`hibernate.cfg.xml`下

``` xml
<!-- 使用二级缓存 -->
<property name="hibernate.cache.use_second_level_cache">true</property>
<!-- 二级缓存的工厂类 -->
<property name="hibernate.cache.region.factory_class">org.hibernate.cache.ehcache.EhCacheRegionFactory</property>
```

缓存匹配文件`ehcache.xml`,放在根目录下

``` xml
<?xml version="1.0" encoding="UTF-8"?>  
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="../config/ehcache.xsd">  
      
    <diskStore path="java.io.tmpdir/ehcache" />  
    <!-- DefaultCache setting. -->  
	<defaultCache  
            maxElementsInMemory="1000"  			内存最大缓存数目
            eternal="false"  						是否永久缓存
            timeToIdleSeconds="300"  				钝化时间
            timeToLiveSeconds="300"  				保存时间
            maxElementsOnDisk="1000000"  			硬盘最大保存数量
            overflowToDisk="true"   				是否保存在硬盘
            memoryStoreEvictionPolicy="LRU">  
    </defaultCache>
  	
  	<!-- 单独配置缓存	name:在配置缓存时指定 -->
    <cache   
         name="PUPIL"  
         maxElementsInMemory="2"  
         memoryStoreEvictionPolicy="LRU"   
         eternal="true"   
         diskPersistent="false"  
         overflowToDisk="false"   
         maxElementsOnDisk="1000000" /> 
  
</ehcache> 
```

映射文件中使用配置文件缓存

``` xml
<!-- 开启二级缓存,usage:缓存策略	include:是否包含懒加载	region:为该缓存起别名,用于单独控制 -->
<cache usage="read-only" include="non-lazy" region="PUPIL"/>
```

测试类

| 方法                                  | 介绍                 | 说明                                        |
| ------------------------------------- | -------------------- | ------------------------------------------- |
| init()                                | 获得Session,开启事物 |                                             |
| destory()                             | 提交事务,关闭资源    |                                             |
| testDoubleQueryBySame()               | 测试一级缓存         | 同一个对象第二次查询时为缓存查询            |
| testDoubleQueryByDifferent            | 测试一级缓存         | 同一个对象不同的Session查询的为不同引用地址 |
| testDoubleQueryListBySame             | 测试一级缓存         | 根据不同的操作,确定是否使用缓存             |
| testFirstLevelCache()                 | 对一级缓存进行操作   | 删除,清空一级缓存.                          |
| testTwoLevelCacheByDifferentFactory() | 对二级缓存进行操作   | 不同SessionFactory中不共享二级缓存          |
| testTwoLevelCacheBySameFactory()      | 对二级缓存进行操作   | 相同SessionFactory中共享二级缓存            |

``` java
public class CacheTest {

	//会话工厂
	private SessionFactory sessionFactory;
	//会话对象
	private Session session;
	//事物对象
	private Transaction transaction;
	
	/*测试方法前执行*/
	@Before
	public void init() {
		//创建配置对象
		Configuration configuration = new Configuration().configure();
		//创建服务注册对象
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		//创建会话工厂
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//创建会话对象
		session = sessionFactory.openSession();
		//开启事物
		transaction = session.beginTransaction();
	}
	
	/*测试方法后执行*/
	@After
	public void destory() {
		//事物提交
		transaction.commit();
		//关闭会话 
		session.close();
		//关闭工厂
		sessionFactory.close();
	}
	
	/*第二次同一个session查询同一个对象时,并不会再次执行数据库的查询,而是使用缓存;在缓存中每一个对象唯一*/
	@Test
	public void testDoubleQueryBySame(){
		init();		//初始化
		Student student1 =(Student) session.get(Student.class,1);
		Student student2 =(Student) session.get(Student.class,1);
		System.out.println("查询到学生:"+student1.toString());
		System.out.println("查询到学生:"+student2.toString());
		destory();	//销毁
	}
	
	/*第二次查询同一对象,使用不同的session,并没有使用缓存*/
	@Test
	public void testDoubleQueryByDifferent(){
		init();		//初始化
		Student student1 =(Student) session.get(Student.class,1);
		System.out.println("查询到学生:"+student1.toString());
		destory();	//销毁
		init();		//初始化
		Student student2 =(Student) session.get(Student.class,1);
		System.out.println("查询到学生:"+student2.toString());
		destory();	//销毁
	}
	
	/*第二次查询对象,使用相同的session,是否使用缓存取决于查询方式*/
	@Test
	public void testDoubleQueryListBySame(){
		init();		//初始化
		Query query = session.createQuery("from Student");
		//第一次查询
		List<Student> students = query.list();
		for (Student student : students) {
			System.out.println("查询到学生:"+student.toString());
		}
		//第二次查询,并没有实现缓存,再次查询数据库
		students = query.list();
		for (Student student : students) {
			System.out.println("查询到学生:"+student.toString());
		}		
		
		//使用迭代器,对缓存进行查询匹配,如果命中则调用缓存,否则查询数据库
		Iterator<Student> iterator = query.iterate();
		while (iterator.hasNext()) {
			Student student = (Student) iterator.next();
			System.out.println("查询到学生:"+student.toString());
		}		
		destory();	//销毁
	}
	
	/*针对一级缓存的操作*/
	@Test
	public void testFirstLevelCache(){
		init();		//初始化
		Student student1 =(Student) session.get(Student.class,1);
		System.out.println("查询到学生:"+student1.toString());
		//清除一级缓存区的指定内容
		session.evict(student1);
		//清除一级缓存区的所有内容
		session.clear();
		Student student2 =(Student) session.get(Student.class,1);
		System.out.println("查询到学生:"+student2.toString());
		destory();	//销毁
	}
	
	/*针对二级缓存的操作,使用不同的sessionFactory,发现不能使用二级缓存*/
	@Test
	public void testTwoLevelCacheByDifferentFactory(){
		init();		//初始化
		Pupil pupil1 =(Pupil) session.get(Pupil.class,5);
		System.out.println("查询到学生:"+pupil1.toString());
		destory();	//销毁
		init();		//初始化
		Pupil pupil2 =(Pupil) session.get(Pupil.class,5);
		System.out.println("查询到学生:"+pupil2.toString());
		destory();	//销毁
	}
	
	/*针对二级缓存的操作,使用相同的sessionFactory,可以使用二级缓存*/
	@Test
	public void testTwoLevelCacheBySameFactory(){

		//创建配置对象
		Configuration configuration = new Configuration().configure();
		//创建服务注册对象
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
		//创建会话工厂
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		//创建会话对象
		Session session = sessionFactory.openSession();
		//开启事物
		Transaction transaction = session.beginTransaction();
		//查询
		Pupil pupil1 =(Pupil) session.get(Pupil.class,5);
		System.out.println("查询到学生:"+pupil1.toString());
		//提交事务
		transaction.commit();
		//关闭session,但是不关闭工厂
		session.close();
		
		//再次查询
		//创建会话对象
		session = sessionFactory.openSession();
		//开启事物
		transaction = session.beginTransaction();
		//查询
		pupil1 =(Pupil) session.get(Pupil.class,5);
		System.out.println("查询到学生:"+pupil1.toString());
		//提交事务
		transaction.commit();
		//关闭session,sessionFactory
		session.close();
		sessionFactory.close();
	}
}
```

### 基于注解实现
为了简化复杂的ORM文件的配置.可以通过Hibernate实现数据库映射配置.
通常由`JPA`和`Hibernate`注解实现,前者为规范,后者为实现,为了程序移植考虑,推荐前者.

| 注解            | 属性               | 说明                                                                                                                         |
| --------------- | ------------------ | ---------------------------------------------------------------------------------------------------------------------------- |
| @ Entity        |                    | 表示该bean退应数据库某张表                                                                                                   |
|                 | name               | 数据库表名,缺省表示表名与类名一致                                                                                            |
| @Table          |                    | 表示映射数据的表的具体信息                                                                                                   |
|                 | name               | 数据库表名,缺省表示与类名一致                                                                                                |
|                 | catalog            | 表示Catalog的名称                                                                                                            |
|                 | schema             | 表示Schema的名称                                                                                                             |
| @Embeddable     |                    | 表示该类作为组件属性类                                                                                                       |
| @Embedded       |                    | 表示该属性为组件类的引用                                                                                                     |
| @Id             |                    | 表示该属性为主键                                                                                                             |
| @GeneratedValue |                    | 主键生成策略                                                                                                                 |
|                 | strategy           | 主键的生成策略,选择<code>GenerationType</code>的枚举                                                                         |
| @Column         |                    | 表示该属性映射为数据库字段                                                                                                   |
|                 | name               | 数据库字段名,缺省与属性名一致                                                                                                |
|                 | nullable           | 是否允许为空,默认允许                                                                                                        |
|                 | unique             | 是否唯一,默认不唯一                                                                                                          |
|                 | length             | 字段长度,默认255                                                                                                             |
|                 | insertable         | 该字段是够允许新增                                                                                                           |
|                 | updateable         | 该字段是否允许修改                                                                                                           |
| @EmbeddedId     |                    | 实现复合组件,组件类必须实现<code>Serializable</code>接口,默认的无参构造器,重写<code>equals</code>和<code>hashCode</code>方法 |
| @Transient      |                    |                                                                                                                              |
| @OneToOne       |                    | 一对一关系                                                                                                                   |
|                 | cascade            | 级联关系,选择`CascadeType`的枚举                                                                                             |
|                 | mappedBy           | 关联外表的类名,在主动方私有属性引用中注解                                                                                    |
| @JoinColumn     |                    | 关联补充表的关联键                                                                                                           |
|                 | name               | 属性名                                                                                                                       |
|				| referencedColumnName| 数据库字段名|
|                 | unique             | 是否唯一                                                                                                                     |
| @OneToMany      |                    | 一对多配置                                                                                                                   |
|                 | cascade            | 级联方式                                                                                                                     |
|                 | fetch              | 是否懒加载                                                                                                                   |
| @ManyToOne      |                    | 多对一配置                                                                                                                   |
|                 | cascade            | 级联方式                                                                                                                     |
|                 | fetch              | 是否懒加载                                                                                                                   |
| @ManyToMany     |                    | 多对多配置                                                                                                                   |
| @JoinTable      |                    | 多对多配置中间表                                                                                                             |
|                 | name               | 表名                                                                                                                         |
|                 | joinColumns        | 关联外键                                                                                                                     |
|                 | inverseJoinColumns | 控制维护方                                                                                                                   |

类文件列表
- `AAddress` 地址类,作为`AStudent`学生类的组件属性
- `AStudent` 学生类,与`AStudentCard`学生卡类构成一对一关系,并有`AStudentPK`联合主键
- `AStudentPK`联合主键类
- `AGrade`班级类,为多方与`APupil`学生类构成多对一
- `APupil`学生类,与`Teacher`老师类构成多对多
- `ATeacher`老师类,与`APuil`构成多对多