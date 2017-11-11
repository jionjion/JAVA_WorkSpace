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
		- `Command.java`                                                       主表,命令类
		- `CommandContent.java`                                          从表,内容类
		- `Constant.java`                                                         常量类
		- `Message.java`                                                         单表查询,命令和消息类
	- `config` 
		- `sql`   
			- `Command.xml`                                                    主表,命令类的映射文件
			- `CommandContent.xml`                                       从表,命令类的映射文件
			- `IMessage.xml`                                                     基于接口映射文件
			- `Message.xml`                                                      基于命名空间的映射文件
		- `Configuration.xml`
	- `dao`
		- `CommandContentDao.java`                                  实现类,从表的维护
		- `ICommandContent.java`                                        接口,从表的维护
		- `CommandDao.java`                                               主表的维护
		- `IMessage.java`                                                      面向接口编程,消息类的维护
		- `MessageDao.java`                                                 面向命名空间,消息类的维护
	- `db`
		- `DBAccess.java`                                                      工具类,获取`SqlSession`
	- `entity`
		- `Page.java`                                                               分页类,封装分页查询的信息.
	- `interceptor`
		- `PageInterceptor.java`                                              分页拦截器
	- `service`
		- `MessageListService.java`                                      消息列表服务类,用于维护
		- `QueryService.java`                                                对话服务类
	- `servlet`
		- `AutoReplyServlet.java`                                          自动回复功能
		- `DeleteBatchMessageServlet.java`                        批量删除功能
		- `DeleteOneMessageServlet.java`                           单条删除功能
		- `InitTalkServlet.java`                                                初始化对话功能
		- `ListServlet.java`                                                     消息列表维护功能
	- `log4j.properties`                                                        日志格式配置
- `WebContent`
	- `META-INF`
	- `resources`
	- `WEB-INF`
		- `jsp`                                                                      
			- `back`                                                                  列表维护页面
			- `front`                                                                  前台对话页面
		- `lib`
		- `web.xml`                                                                站点配置文件
		- `index.jsp`                                                               主页


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
`<id>`标签指定该属性对应数据库表的主键,`<result>`表示一般字段映射.`column`指向数据库查询结果集字段名或其别名,`jdbcType`表示其数据库字段类型,`property`对应的属性名

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
</mapper>  
```

### 将映射文件添加到Mybatis配置中

使用`<mapper>`标签中`resource`属性完成
``` xml
	<mappers>
		<mapper resource="config/sql/Message.xml"/> 
	</mappers>
```



### 创建SqlSession
通过读取配置文件,创建Session会话

``` xml
public class DBAccess {
	/**获取SQLSession对象的类*/
	public SqlSession getSqlSession() throws Exception{
		//1.读取配置文件
		Reader reader = Resources.getResourceAsReader("config/Configuration.xml");
		//2.创建SQLSessionFactory
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		//3.通过SQLSessionFactory创建一个数据库会话
		SqlSession sqlSession = sqlSessionFactory.openSession();
		//返回一个会话实例
		return sqlSession;
	}
}
```
### 使用Mybatis访问数据库
通过使用`SqlSession`类的`selectList(String arg0, Object arg1)`方法,传入封装后的对象信息,调用(`Message.queryMessagesList`)命名空间.方法的ID,执行相关查询语句.

``` java
public class MessageDao {
	public List<Message> queryMessagesList(String command,String description ) {
		DBAccess dbAccess = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = dbAccess.getSqlSession();
			//通过SQLSession执行SQL语句,直接在方法中追加传入参数.参数只能传一个.多个参数封装为Map或者对象
			//封装对象,传入查询
			Message message = new Message();
			message.setCommand(command);
			message.setDescription(description);
			List<Message> list = sqlSession.selectList("Message.queryMessagesList",message);
			return list;
		} catch (Exception e) {
			System.err.println("SQLSession连接获取出现问题");
			e.printStackTrace();
		} finally {
			//session关闭
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return null;
	}
}	
```

## 基于接口编程的Mybatis访问数据库

### 创建接口类

- 将命名空间改为接口的路径
- 将方法名与ID相一致
- 返回类型与XML配置文件一致
- 参数类型与XML配置文件一致

``` java
public interface IMessage {

	/**查询消息列表的方法*/
	public List<Message> queryMessagesList(Map<String, Object> parameters);

	/**查询模糊条件的总条数的方法*/
	public int count(Message message);
	
	/**使用拦截完成分页查询*/
	public List<Message> queryMessagesListByPageInterceptor(Map<String, Object> parameters);
}
```

### 创建映射文件

映射文件整体与之前无异
1. `<mapper>`标签中命名空间`namespace`指向Dao层的接口
2. `<select>`,`<insert>`,`<update>`,`<delete>`中`id`属性指向接口中的接口方法,注意参数类型与返回值类型相一致.
3. 这里传入的查询参数不在不再是java对象而是Map类,Map类中封装了对象,因此直接使用`对象.属性`进行获取.

``` xml
<!-- 通过接口式编程,实现对其的访问,命名空间,为接口的路径 -->
<mapper namespace="dao.IMessage"> 
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
    <select id="queryMessagesList" parameterType="java.util.Map" resultMap="MessageResult">  
        select id,command,description,content from message 
		<!-- where根据关键字实现自动条件匹配和检索 -->
		<where>
			<!-- 传入类型为两个引用对象的类型,因此需要采用对象.属性的方式获取属性值 -->
			<!-- if:判断,成立则追加,判断是否为空.	""转义为&quot;&quot;	通过.equals调用方法			#{属性名称}填充数据 -->
	        <if test="message.command != null and  ! &quot;&quot;.equals(message.command.trim())"> and command = #{message.command} </if>
	        																						<!-- like '%' #{属性名} '%' 进行模糊查询-->
	        <if test="message.description != null and  ! &quot;&quot;.equals(message.description.trim())"> and description like '%' #{message.description} '%' </if>
    	</where> 
    	order by ID limit #{page.start},#{page.pace}
    </select>
</mapper>  
```

### 使用基于接口编程的Mybatis访问数据库

``` java
public class MessageDao {
	public List<Message> queryMessagesListByInterface (Map<String, Object> parameters) {
		DBAccess dbAccess = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = dbAccess.getSqlSession();
			//通过SQLSession执行SQL语句,直接在方法中追加传入参数.参数只能传一个.多个参数封装为Map或者对象
			
			//面向接口的编程
			IMessage iMessage = sqlSession.getMapper(IMessage.class);
			List<Message> list = iMessage.queryMessagesList(parameters);	//传入Map集合,自定义分页
			return list;
		} catch (Exception e) {
			System.err.println("SQLSession连接获取出现问题");
			e.printStackTrace();
		} finally {
			//session关闭
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return null;
	}
}	
```


## 分页查询

### 分页类的构建
根据MySQL的分页查询语句进行编写,传总页数和查询第几页,默认5条一分页,进行计算,最终计算出查询语句中的起始位置和偏移量.
``` java
public class Page {

	/**总条数*/
	private int totalNumber;
	/**当前页*/
	private int currentPage;
	/**总页数*/
	private int totalPage;
	/**每页显示条数,默认5页*/
	private int pageNumber = 5;
	/**开始查询条数*/
	private int start;	
	/**偏移量*/
	private int pace;
	
	/**计算总页数*/
	public void count() {
		int totalPageTemp = (totalNumber / pageNumber) + ((totalNumber % pageNumber) ==0 ? 0 : 1); 
		//当前页数小于1
		if (totalPageTemp <=0 ) {
			totalPageTemp = 1;
		}
		this.totalPage = totalPageTemp;
		//总页数小于当前页数
		if(this.totalPage < this.currentPage){
			this.currentPage = this.totalPage;
		}
		//当前页数小于1时,设置为1
		if (this.currentPage < 1) {
			this.currentPage = 1;
		}
		//计算limit传入分页参数
		this.start = (this.currentPage -1) * this.pageNumber;
		this.pace = this.pageNumber;
	}
	
	public int getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getPace() {
		return pace;
	}
	public void setPace(int pace) {
		this.pace = pace;
	}
}
```

### 分页服务层编写

接收到逻辑控制层中传入的分页对象,传递了要查询的页数和模糊查询的参数.
这里需要调用Dao层中查询当前总记录数的方法

``` java
public class MessageListService {

	/**传入参数进行模糊查询,如果没有参数则是全部查询**/
	public List<Message> queryMessagesList(String command,String description, Page page ) {
		//封装查询条件,为一个对象,进行总条数的查询
		Message message = new Message();
		message.setCommand(command);
		message.setDescription(description);
		MessageDao messageDao = new MessageDao();
		int totalNumber = messageDao.count(message);	//查询总页数
		page.setTotalNumber(totalNumber);				//初始化分页对象
		page.count();									//计算分页详情
		//由于Mybatis只能封装一种引用数据类型,这里采用Map进行封装
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("page", page);
		parameters.put("message", message);
		List<Message> messagesList = messageDao.queryMessagesListByInterface(parameters);
		return messagesList;
	}
}
```

### 查询Servlet类编写

接收前台传入的模糊查询参数和要求分页查询的页数
``` java
public class ListServlet extends HttpServlet {
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取传入模糊查询
		request.setCharacterEncoding("UTF-8");
		String command = request.getParameter("command");
		String description = request.getParameter("description");
		//分页查询
		String currentPage = request.getParameter("currentPage");
		Page page = new Page();
		Pattern pattern = Pattern.compile("[0-9]{1,9}");	//正则表达式,前端只能传入[0,9]数字
		if (currentPage == null || currentPage.trim().equals("") || !pattern.matcher(currentPage).matches()) {
			page.setCurrentPage(1);
		}else{
			page.setCurrentPage(Integer.parseInt(currentPage));
		}
		//分页,模糊查询
		MessageListService messageListService = new MessageListService();
		List<Message> messagesList = messageListService.queryMessagesList(command, description,page);
		request.setAttribute("messagesList",messagesList);
		//向页面传递参数:条件,分页
		request.setAttribute("command", command);
		request.setAttribute("description", description);
		request.setAttribute("page", page);
		//跳转转发页面
		request.getRequestDispatcher("/WEB-INF/jsp/back/list.jsp").forward(request, response);
	}
}
```

## 基于拦截器的分页查询
### 创建分页拦截器
- 实现`Interceptor`接口中的`plugin()`拦截前执行方法,`intercept`拦截后执行方法.
- `@Intercepts`注解标识该类为拦截器类,`@Signature`表示注册,`type`注册拦截器类,`method`拦截方法,`args`参数.
- 在拦截器方法`intercept()`中,获得拦截对象,对SQL语句进行反射操作.
``` java
/**分页拦截器*/
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class PageInterceptor implements Interceptor{

	/**拦截后的方法*/
	@SuppressWarnings("unchecked")
	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		//获得拦截的对象的代理
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaObject = MetaObject.forObject(statementHandler, SystemMetaObject.DEFAULT_OBJECT_FACTORY,SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY, new DefaultReflectorFactory());
		MappedStatement mappedStatement  = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
		//获得SQL语句的ID
		String id = mappedStatement.getId();
		if (id.matches(".+ByPageInterceptor$")) {
			
			BoundSql boundSql = statementHandler.getBoundSql();
			String SQL = boundSql.getSql();	//获得原始的SQL
			String countSQL = " select count(*) from (" + SQL + ") a";
			Map<String, Object> parameters = (Map<String, Object>) boundSql.getParameterObject();	//获得传入的SQL参数
			Page page = (Page) parameters.get("page");		//获得page实体类
			
			Connection connection = (Connection) invocation.getArgs()[0];	//获得传入参数,只有一个,因此取第一个
			PreparedStatement preparedStatement = connection.prepareStatement(countSQL);
			ParameterHandler parameterHandler = (ParameterHandler) metaObject.getValue("delegate.parameterHandler");
			parameterHandler.setParameters(preparedStatement);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				page.setTotalNumber(resultSet.getInt(1));	//获得总条数
			}
			String pageSQL = SQL + " limit " + page.getStart() + " , " + page.getPace();
			metaObject.setValue("delegate.boundSql.sql", pageSQL);	//修改SQL为自定义的
			
		}
		return invocation.proceed();
	}

	/**	执行拦截前的方法,将注解声明的类进行拦截
	 * 	如果是符合条件的代理类,则完成返回拦截的对象的代理*/
	@Override
	public Object plugin(Object target) {
		
		return Plugin.wrap(target, this);	//拦截,拦截的类
	}

	/**	获得配置文件执行时的设置属性 */
	@Override
	public void setProperties(Properties properties) {
		String info = properties.getProperty("info");
		System.out.println(info);
	}
}
```


### 拦截器注册
在`Configuration.xml`文件的开始处,添加
`<plugin>`标签的`interceptor`属性指向拦截器的实现类
`<property>`中的`name`和`value`属性便于拦截器类通过`setProperties()`方法,获得XML中配置的值
``` xml
	<!-- 引入拦截器,必须放在首部 -->
	<plugins>
		<plugin interceptor="interceptor.PageInterceptor">
			<property name="info" value="this is my pageInterceptor...."/>
		</plugin>
	</plugins>
```


# 关联查询

## 主表和从表的JavaBean构建
**主表`Command`命令**
在主表中设定唯一主键,并配置其他字段必须的信息
这里使用`List<CommandContent>`引用多方的集合

``` java
public class Command {
	/**唯一主键*/
	private int id;
	/**命令*/
	private String name;
	/**描述*/
	private String description;
	/**对应的多方列表*/
	private List<CommandContent> contentsList; 
	
	public List<CommandContent> getContentsList() {
		return contentsList;
	}

	public void setContentsList(List<CommandContent> contentsList) {
		this.contentsList = contentsList;
	}

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

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
```

**从表`CommandContent`命令回复内容**
这里为从表设定唯一主键和其他字段
私有主表的对象,进行双向关联.

``` java
public class CommandContent {
	/**唯一主键*/
	private int id;
	/**内容*/
	private String content;
	/**外键*/
	private int commandId;
	/**主表的引用*/
	private Command command;
	
	public int getCommandId() {
		return commandId;
	}

	public void setCommandId(int commandId) {
		this.commandId = commandId;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
```


## 主表和从表的映射文件
**从表的映射文件**
从表的映射文件
- 命名空间`namespace`保持唯一,一般与数据库表名一致
- `<resultMap>`标签指向从表的JavaBean类,配置`<id>`标签与`<result>`标签
- 在多方使用`<association>`标签,`property`属性指向从表中引用主表的Bean属性,`resultMap`属性指向主表的映射文件中的`<resultMap>`标签的ID

``` xml
<mapper namespace="CommandContent"> 
	  <resultMap type="bean.CommandContent" id="CommandContentResult">
	  	<id column="ID" jdbcType="INTEGER" property="id"/>
	  	<result column="CONTENT" jdbcType="VARCHAR" property="content"/>
	  	<result column="COMMAND_ID" jdbcType="INTEGER" property="commandId"/>
	  	<!-- 多对一,一方的XML引用 -->
	  	<association property="command" resultMap="Command.CommandResult"></association>
	  </resultMap>
</mapper>
```

**主表的映射文件**
主表的映射文件
- 命名空间`namespace`保持唯一,一般为数据库的表名
- `<resultMap>`表示配置主键和其他字段属性映射
- 在一方使用`<collection>`标签,`property`属性为主表配置的多方的List集合对象,`resultMap`属性指向从表映射文件中`<resultMap>`配置的返回类
- 使用`<select>`进行查询,`parameterType`属性指向参数类型,为主表的Bean类型,`resultMap`指向返回类型,为`<resultMap>`配置的标签ID.
- 联合查询的SQL语句中可以使用数据库函数或者伪列别名.
``` xml
<mapper namespace="Command"> 
	  <resultMap type="bean.Command" id="CommandResult">
	  	<!-- column:对应数据库查询时的字段名,如果查询语句用到了别名,则为别名 -->
	  	<id column="A_ID" jdbcType="INTEGER" property="id"/>
	  	<result column="NAME" jdbcType="VARCHAR" property="name"/>
	  	<result column="DESCRIPTION" jdbcType="VARCHAR" property="description"/>
	  	<!-- 一对多，引用多方的XML集合 -->
	  	<collection property="contentsList" resultMap="CommandContent.CommandContentResult"/>
	  </resultMap>
	  
	  <!-- 表别名不用在字段映射中直接出现,为了方便查询,可以使用字段别名后修改对应映射文件进行修改 -->
	  <select id="queryCommandsList" parameterType="bean.Command" resultMap="CommandResult">
	  	SELECT  a.id as a_id,a.name,a.description,b.content 
	  	FROM command a 
		LEFT JOIN command_content b
		ON a.id=b.command_id
	  </select>
</mapper>  
```

## 基于接口编程的Mybatis访问
这里使用命名空间调用映射文件中的方法
返回联合查询的结果.
``` java
public class CommandDao {

	/**使用Mybatis实现对数据库的访问查询,查询指令列表*/
	public List<Command> queryCommandsList(String name,String description ) {
		DBAccess dbAccess = new DBAccess();
		SqlSession sqlSession = null;
		try {
			sqlSession = dbAccess.getSqlSession();
			//通过SQLSession执行SQL语句,直接在方法中追加传入参数.参数只能传一个.多个参数封装为Map或者对象
			//封装对象,传入查询
			Command command = new Command();
			command.setName(name);
			command.setDescription(description);
			List<Command> list = sqlSession.selectList("Command.queryCommandsList",command);
			return list;
		} catch (Exception e) {
			System.err.println("SQLSession连接获取出现问题");
			e.printStackTrace();
		} finally {
			//session关闭 
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return null;
	}
}
```

