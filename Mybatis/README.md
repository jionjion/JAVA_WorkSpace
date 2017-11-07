---
title : Mybatis的项目介绍

tags : JDK8,Tomcat8

---

[TOC]

---

# 简介

该项目为慕课网中介绍Mybatis的项目,通过该项目练习Mybatis的相关知识.

## 包结构

- `src`
	- `bean`
		- `Command.java`
		- `CommandContent.java`
		- `Constant.java`
		- `Message.java`
	- `config`
		- `sql`
			- `Command.xml`
			- `CommandContent.xml`
			- `IMessage.xml`
			- `Message.xml`
		- `Configuration.xml`
	- `dao`
		- `CommandDao.java`
		- `IMessage.java`
		- `MessageDao.java`
	- `db`
		- `DBAccess.java`
	- `entity`
		- `Page.java`
	- `interceptor`
		- `PageInterceptor.java`
	- `service`
		- `MessageListService.java`
		- `QueryService.java`
	- `servlet`
		- `AutoReplyServlet.java`
		- `DeleteBatchMessageServlet.java`
		- `DeleteOneMessageServlet.java`
		- `InitTalkServlet.java`
		- `ListServlet.java`
	- `log4j.properties`
- `WebContent`
	- `META-INF`
	- `resources`
	- `WEB-INF`
		- `jsp`
			- `back`
			- `front`
		- `lib`
		- `web.xml`
		- `index.jsp`


# 项目介绍

Mybatis是一款工作在数据持久化层的框架,通过它可以实现将Java对象代码转为数据库记录.
本项目通过模拟微信机器人聊天程序,实现对前台参数访问的请求响应.


## 相关知识

### OGNL表达式

| 取值范围            | 包装与基本数据类型                                   | 标签中的属性                   |             |
| ------------------- | ---------------------------------------------------- | ------------------------------ | ----------- |
| 取值写法            | String与基本数据类型                                 | \_parameter                     |             |
|                     | 自定义类型                                           | 属性名                         |             |
|                     | 集合                                                 | 数组:array                     |             |
|                     |                                                      | 列表:list                      |             |
|                     |                                                      | 映射:\_parameter                |             |
| 操作符              | java常用操作符                                       | +,-,\*,/,==,!=,||,&&            |             |
|                     | 特有操作符                                           | and,or,mod,in,not in           |             |
| 从集合中取数据      | 数组                                                 | array[索引]  或者  array[索引].属性名 |             |
|                     | 列表                                                 | list[索引]  或者  list[索引].属性名   |             |
|                     | 映射                                                 | \_parameter.key  或者  key.属性名      |             |
| 使用`<foreach>`标签 | `<foreach collection="array" index="i" item="item">` |                                |             |
|                     | 数组                                                 | i:索引,下标                    | item        |
|                     | 列表                                                 | i:索引,下标                    | item        |
|                     | 映射                                                 | i:key                          | item.属性名 |

### 常用标签

| 功能                                       | 标签名称        |
| ------------------------------------------ | --------------- |
| 定义SQL语句                                | `<insert>`      |
|                                            | `<select>`      |
|                                            | `<update>`      |
|                                            | `<delete>`      |
| 配置java对象属性与查询结果集中列名对应关系 | `<resultMap>`   |
| 控制动态SQL拼接                            | `<foreach>`     |
|                                            | `<if>`          |
|                                            | `<choose>`      |
| 格式化输出                                 | `<where>`       |
|                                            | `<set>`         |
|                                            | `<trim>`        |
| 配置关联关系                               | `<collection>`  |
|                                            | `<association>` |
| 定义常量                                   | `<sql>`         |
| 引用常量                                   | `<include>`     |


# 单表查询
## 单表查询JavaBean的构建

**`Message`实体类的构建**
消息对应的实体类,对应单表,`command`属性为前台传入的命令,`command`属性为对命令的描述,`content`封装消息返回的主体.
这里,我们重写了对象的`toString()`方法,方便日志输出.
``` java
public class Message {

	private Integer id;
	
	private String command;
	
	private String description;
	
	private String content;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Message(Integer id, String command, String description, String content) {
		super();
		this.id = id;
		this.command = command;
		this.description = description;
		this.content = content;
	}

	public Message() {
		super();
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", command=" + command + ", description=" + description + ", content=" + content
				+ "]";
	}
}
```

## 使用JDBC方式访问数据库
这里我们通过传入`command`(命令)或者`description`(命令描述)进而模糊查询单表中定义的命令和对应相应回复.

1. 通过类名加载MySQL驱动
2. 创建数据库连接
3. 创建SQL命令字符串,判断前台是否传入参数,进而拼接字符串.
	- `stringBuilder.append("and description like '%' ? '%'")`模糊查询的方法
4. 使用`PreparedStatement`预编译对象,对每个问号占位符进行设值.
	- SQL语句的?由1开始,而数组获取从0开始
5. 将查询结果遍历,封装进入`List<Message> `中.

``` java
public class MessageDao {
	public List<Message> queryMessagesListByJDBC(String command,String description ) {
		
		//连接数据库
		List<String> paramList = new ArrayList<>();	//参数变量临时保存
		try {
			//加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			//创建连接
			Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mybatis", "root", "123456");
			//使用可变的字符串书写SQL语句
			StringBuilder stringBuilder = new StringBuilder(" select id,command,description,content from message where 1=1 ");
			//对前台传入参数进行判断,不为空切不为空串时,则存入List中,计数的同时拼写SQL文
			if (command !=null && !"".equals(command.trim())) {
				//如果条件不为空
				stringBuilder.append(" and command = ? ");	//拼写SQL文字
				paramList.add(command);						//存入List中,0位置则是command的条件查询
			}
			if (description !=null && !"".equals(description.trim())) {
				//如果条件不为空
				stringBuilder.append("and description like '%' ? '%'");		//MySQL中间有空格,进行模糊查询
				paramList.add(description);
			}
			PreparedStatement preparedStatement = connection.prepareStatement(stringBuilder.toString());	//预加载的SQL执行对象,防止注入
			for(int i=0 ; i<paramList.size() ; i++){				//预编译SQL语句
				preparedStatement.setString(i+1, paramList.get(i));	//SQL语句的?由1开始,而数组获取从0开始
			}
			ResultSet resultSet = preparedStatement.executeQuery();	//执行查询,返回查询结果
			//封装进入List
			List<Message> messagesList = new ArrayList<Message>();	
			while (resultSet.next()) {
				Message message = new Message(resultSet.getInt("id"), resultSet.getString("command"), resultSet.getString("description"), resultSet.getString("content"));
				messagesList.add(message);
			}
			return messagesList;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;	//如果失败,则返回空
	}
}
```

## 使用Mybatis访问数据库

### 创建Mybatis框架配置文件.

创建`Configuration.xml`文件,里面配置了Mybatis的相关配置.
`<environments>`标签定义了数据库相关设置,其中`<environment>`定义每个数据库信息
`<transactionManager>`定义事务为JDBC事务
`<dataSource>`定义了数据连接相关信息
`<mappers>`中定义了每个数据库映射文件的路径,这这里可以配置相关SQL查询

``` xml
<configuration>
	<environments default="development">
		<environment id="development">
			<transactionManager type="JDBC" />
			<dataSource type="UNPOOLED">
				<property name="driver" value="com.mysql.jdbc.Driver" />
				<property name="url" value="jdbc:mysql://127.0.0.1:3306/mybatis?useUnicode=true&amp;characterEncoding=utf8" />
				<property name="username" value="root" />
				<property name="password" value="123456" />
			</dataSource>
		</environment>
	</environments>
	
	<mappers>
		<mapper resource="config/sql/Message.xml"/>
	</mappers>
</configuration>		
```

### 创建查询映射文件

在`Configuration.xml`文件的`<mapper>`的`resource`属性中指定的位置,创建查询映射文件,完成对数据库的查询.

**映射文件的组成**

#### 命名空间
`<mapper>`中的`namespace`属性为命名空间,多选定为数据库的对应表名

#### `<resultMap>`返回类型
对数据库中字段与返回Java类属性相映射,`type`标识返回类型对应的java类,`id`进行唯一区分.
`<id>`标签指定该属性对应数据库表的主键,`<result>`表示一般字段映射.`column`指向数据库字段名,`jdbcType`表示其数据库字段类型,`property`对应的属性名

#### `<sql>`自定义SQL片段
使用`<sql>`包裹自定义SQL片段,使用`id`属性进行唯一区分,使用`<include>`中的`refid`引用当前SQL片段,简化SQL编写代码量.

#### `<select>`查询语句
在标签内书写查询语句,`id`该查询语句唯一标识,`parameterType`标识传入参数的类型,可以为基本类型或者引用类型或者其他数据结构,`resultMap`映射返回类型
可以在SQL语句中使用其他动态拼接标签和格式化输出标签,共同组成SQL语句.

#### `<insert`插入语句
执行插入操作

#### `<update>`更新语句
执行更新操作

#### `<delete>`删除语句
执行删除操作

``` xml
<!-- 命名空间,区分ID,多为数据库表名 -->
<mapper namespace="Message"> 
	<!-- 配置返回值的数据类型 	type:对应java对象实体类	id:唯一区分	--> 
    <resultMap type="bean.Message" id="MessageResult">  
    	<!-- 主键配置属性 	column:数据库字段名	jdbcType:数据类型  property:java类的属性名-->
        <id column="id"  property="id" />
        <!-- 非主键配置属性 -->  
        <result column="COMMAND" jdbcType="VARCHAR" property="command" />
        <result column="DESCRIPTION" jdbcType="VARCHAR" property="description"/>
        <result column="CONTENT" jdbcType="VARCHAR" property="content"/>
    </resultMap>
  
    <!-- 类似于常量定义,将经常使用到的SQL片段自定义使用 -->
    <sql id="deleteMessageSQL">delete from message</sql>
  
  	<!-- id:唯一区分,与Dao层方法相同	parameterType:传入参数类型,为对象地址  	resultMap:返回类型,为封装后的对象 -->
    <select id="queryMessagesList" parameterType="bean.Message" resultMap="MessageResult">  
        select id,command,description,content from message 
		<!-- where根据关键字实现自动条件匹配和检索 -->
		<where>
			<!-- if:判断,成立则追加,判断是否为空.	""转义为&quot;&quot;	通过.equals调用方法			#{属性名称}填充数据 -->
	        <if test="command != null and  ! &quot;&quot;.equals(command.trim())"> and command = #{command} </if>
	        																						<!-- like '%' #{属性名} '%' 进行模糊查询-->
	        <if test="description != null and  ! &quot;&quot;.equals(description.trim())"> and description like '%' #{description} '%' </if>
    	</where> 
    </select>
    
    <!-- parameterType:为基本数据类型  #{_parameter}实现基本类型的注入 -->
    <delete id="deleteOneMessage" parameterType="int">
    	delete from message
    	where id = #{_parameter}
    </delete>
    
    <!-- 批量删除,此时为 parameterType:引用的类全路径-->
    <delete id="deleteBatchMessage" parameterType="java.util.List">
    	<!-- 使用SQL引用标签,实现对SQL代码的重复使用 -->
    	<include refid="deleteMessageSQL"/>
    	where id in (
    		<!-- foreach循环遍历,中间用逗号分隔 -->
    		<foreach collection="list" item="item" separator=",">
    			#{item}
    		</foreach>
    	)
    </delete>
    
  	<!-- @TODO -->
  	<!-- 修改,使用set标签,完成对多个条件的修改 -->
  	
  	
  	<!-- trim标签,前后缀修改 -->
  	<!-- 
		<trim prefix="" prefixOverrides="" suffix="" suffixOverrides=""></trim>		
  	 -->
  	 
  	<!-- choose标签,多重选择 -->
  	<!-- 
  		<choose>
  			<when test=""></when>
  			<when test=""></when>
  			<otherwise></otherwise>
  		</choose>
  	 --> 
</mapper>  
```
