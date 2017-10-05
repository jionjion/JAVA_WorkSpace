# Struts的项目介绍

Tags : JDK8 Eclipse

---

[TOC]

---

## 简介

该项目是Struts框架的搭建练习,涉及到框架的搭建,数据的绑定,控制器方法的调用,拦截器与自定义拦截器的实现.

![Struts2工作原理][1]
## 框架的搭建
整体采用`web`动态项目,通过引入jar包和`web-xml`配置,实现`Struts2`的搭建.

## 包结构

* `action`                    控制层
* `bean`                      数据库映射层
* `interceptor`             拦截器层
* `*.xml`                     各种配置

## 模块编码
### 框架搭建

**web-xml配置**

在Web项目中,通过`web.xml`配置各种控制器,实现加载框架的`servlet`.
对于Struts而言,我们需要加载`StrutsPrepareAndExecuteFilter`过滤器,过滤所有请求
其中的`<struts.action.extension>`标签设定URL的后缀名,当然,也可以在`struts.xml`中配置.


``` xml
  <!-- 配置过滤器 -->
  <filter>
  	<filter-name>struts2</filter-name>
  	<filter-class>org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter</filter-class>
  <init-param>
	<param-name>struts.action.extension</param-name>
	<param-value>html</param-value>
  </init-param>
  </filter>
  <filter-mapping>
  	<filter-name>struts2</filter-name>
  	<url-pattern>/*</url-pattern>
  </filter-mapping>
```

**Action映射文件配置**

`struts.xml`默认放在项目类文件下,在项目加载时读取.
文件定义了整体的全局属性,用户请求和响应Action的关系及其可能用到参数,拦截器和拦截器栈的配置,其他子配置文件的引入.


| 标签                              | 属性      | 说明                                                                                   |
| --------------------------------- | --------- | -------------------------------------------------------------------------------------- |
| &lt;struts&gt;                    |           | 根标签                                                                                 |
| &lt;constant&gt;                  |           | Struts配置标签,通过键值对配置框架属性                                                  |
|                                   | name      | 属性名                                                                                 |
|                                   | value     | 属性值                                                                                 |
| &lt;include&gt;                   |           | 包含标签,将其他子模块的标签进行包含                                                    |
| &lt;package&gt;                   |           | 映射配置,将不同的URL请求映射为Action控制层的某一个控制器的某个方法                     |
|                                   | name      | 包名,在一个xml下可以有多个包,但要求包名唯一                                            |
|                                   | namespace | URL请求的命名空间,<code>/</code>默认在项目根目录下添加                                 |
|                                   | extends   | 继承的父类包名,默认<code>struts-default</code>父包                                     |
|                                   | abstract  | 是否为抽象类,默认为<code>false</code>,抽象类不能作为Action                             |
| &lt;default-action-ref&gt;        |           | <code>&lt;package&gt;</code>子标签,当该命名空间下,URL请求错误时,默认的跳转请求         |
|                                   | name      | 请求错误时,跳转的<code>&lt;action&gt;</code>                                           |
| &lt;global-results&gt;            |           | <code>&lt;package&gt;</code>子标签,作为该包下的局部跳转请求                            |
| &lt;result&gt;                    |           | <code>&lt;global-results&gt;</code>子标签,定义每一个局部请求                           |
|                                   | name      | 对应局请求的跳转Action                                                                 |
| &lt;global-exception-mappings&gt; |           | <code>&lt;package&gt;</code>子标签,抛出异常后的跳转页面                                |
| &lt;exception-mapping&gt;         |           | <code>&lt;global-exception-mappings&gt;</code>子标签,定义异常种类及其跳转的action      |
|                                   | exception | 异常种类                                                                               |
|                                   | result    | 跳转的action                                                                           |
| \<action\>                        |           | <code>&lt;package&gt;</code>子标签,当该命名空间下,URL请求及其对清的action类            |
|                                   | name      | 请求URL的执行类                                                                        |
|                                   | class     | 对应控制类                                                                             |
|                                   | method    | 执行方法                                                                               |
| \<result\>                        |           | <code>&lt;action&gt;</code>子标签,对Action类中的返回字符串进行匹配,跳转对应的模板页面. |
|                                   | name      | 匹配Action类的返回结果                                                                 |
| \<param\>                         |           | <code>&lt;result&gt;</code>子标签,对返回跳转进行控制                                   |
|                                   | name      | 跳转请求时的预设属性                                                                   |


**struts.properties属性文件**
`struts.properties`属性文件配置了框架有关的参数,当然也可以通过`< constant>`标签配置

**访问Servet的API**
- ActionContext类
- 实现XXXXAware()接口
- ServletActionContext类

**跳转请求分析**
当请求URL不匹配时,会默认寻找对应的包中的`<action>`中的`name`,如果匹配则返回;否则继续向上搜索,如果有默认全局处理,则匹配;否则抛出异常.

### 演示实例

**编写action类**
`Struts2`框架通过继承,实现与框架的耦合.
`execute()`方法为类必须执行的方法,每当请求经过该控制时,均会执行该方法.
`add()`和`update()`方法为用户自定义的方法,模拟用户请求操作,其中返回字符串`SUCCESS`为父类的预定义常量,通过匹配返回字符串实现调用不同的视图页面.

其他内置处理结果

| 返回字符串 | 含义                          |
| ---------- | ----------------------------- |
| SUCCESS    | 程序正常执行,返回视图         |
| NONE       | 程序正常执行,无返回视图       |
| ERROR      | 执行失败,返回失败错误处理视图 |
| LOGIN      | 没有验证,返回验证视图         |
| INPUT      |   后续执行需要前台参数,input代表获取参数的前台页面  |

``` java
public class HelloWorld extends ActionSupport {

	private static final long serialVersionUID = 1L;

	/**默认的方法,默认必须执行*/
	@Override
	public String execute() throws Exception {
		
		System.out.println("执行操作");
		return SUCCESS;		//返回成功的界面
	}
	
	
	/**新增的方法*/
	public String add() {
		System.out.println("执行新增方法");
		return SUCCESS;
	}
	
	/**修改的方法*/
	public String update() {
		System.out.println("执行修改方法");
		return SUCCESS;
	}
}

```

**action中方法的调用**
在`<action>`中指定 **`method`属性**

``` xml
<!-- URL:http://localhost:8080/Struts/operateAction!add -->
<action name="addAction" class="action.HelloWorld" method="add" > 
	<!-- 对于继承ActionSupport的类不需要配置各种属性 -->
	<result>/jsp/add.jsp</result>
</action>
<action name="updateAction" class="action.HelloWorld" method="update" > 
	<!-- 对于继承ActionSupport的类不需要配置各种属性 -->
	<result>/jsp/update.jsp</result>
</action>
```

**访问**
通过在浏览器中输入`http://localhost:8080/Struts/addAction`和`http://localhost:8080/Struts/updateAction`实现访问


### 拆分映射文件
映射文件可以根据不同模块,拆分为小的xml文件,分别保存,以便于扩展.
1. 根据模块划分文件

``` xml
<struts>

	<!-- 子包类,被包含 -->
    <!-- 编码格式 -->
	<constant name="struts.i18n.encoding" value="UTF-8"></constant>
	<!--  name:可以有多个,包名唯一	namespace:url命名空间,在根项目下添加		extends:继承父类的包名  abstract:抽象包不能定义action -->
    <package name="helloWorld" namespace="/" extends="struts-default" abstract="false">
 		<!-- 	URL:http://localhost:8080/Struts/4sas/51sdsf/helloWorld.action
 				URL:http://localhost:8080/Struts/helloWorld.action
 				URL:http://localhost:8080/Struts/helloWorld
 				由于框架的容错性,以上均可访问
 		 -->
		<!-- 跳转配置	name:url中的映射		class:url进入哪一个类进行处理    method:调用的方法,默认执行execute()-->
        <action name="helloWorld" class="action.HelloWorld"> 
        	<!--返回结果集	name:返回结果	type:跳转类型 -->
        	<!-- 对于继承ActionSupport的类不需要配置各种属性 -->
        	<result>/jsp/HelloWorld.jsp</result>
        	<!-- 定义的参数,通过getXXX()在action对应的类进行获取 -->
        	<param name="url">www.baidu.com</param>
        </action>
    </package>
</struts>
```


2. 将文件整合
通过在`Struts`文件中包含,完成配置

``` xml
<include file="HelloWorld.xml"/> 
```


### URL请求Action的方法

**action文件**
这里将返回值改为自定义,通过在XML配置`<action>`标签,实现指定视图的返回.

``` java
public class OperateAction extends ActionSupport {

	
	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	/**新增的方法 */
	public String add() {
		System.out.println("执行新增方法");
		return "add";
	}
	
	/**修改的方法*/
	public String update() {
		System.out.println("执行修改方法");
		return "update";
	}
}
```

**映射文件配置**

通过在映射文件中配置`<action>`标签,实现对控制层中类的调用.

 1. 在`<action>`中指定 **`method`属性**

针对每一个URL都需要进行配置.不推荐 
``` xml
<!-- URL:http://localhost:8080/Struts/operateAction!add -->
<action name="addAction" class="action.HelloWorld" method="add" > 
	<!-- 对于继承ActionSupport的类不需要配置各种属性 -->
	<result>/jsp/add.jsp</result>
</action>
<action name="updateAction" class="action.HelloWorld" method="update" > 
	<!-- 对于继承ActionSupport的类不需要配置各种属性 -->
	<result>/jsp/update.jsp</result>
</action>
```


 2. 在URL中使用 **!感叹号调用** 

需要开启配置,使用!的方式进行方法的调用和视图的转换

``` xml
<!-- URL:http://localhost:8080/Struts/operateAction!add -->
<action name="operateAction" class="action.OperateAction">
	<result>/jsp/HelloWorld.jsp</result>
	<result name="add">/jsp/add.jsp</result>
	<!-- 对result的参数的书写 -->
	<result name="update">
		<!-- 跳转链接 -->
		<param name="location">/jsp/update.jsp</param>
		<!-- 不试用OGNL表达式,默认开启 -->
		<param name="parse">false</param>
	</result>
</action>
```

 3. 通配 **符方式**调用
使用通配符的形式进方法的调用的和视图的转换,`*`由URL中的单词代替,并换替换`{}`,注意大小写
``` xml
<!-- URL:http://localhost:8080/Struts/wildcardAction_add -->
<action name="wildcardAction_*" class="action.OperateAction" method="{1}">
	<result>/jsp/HelloWorld.jsp</result>
	<result name="add">/jsp/{1}.jsp</result>
	<result name="update">/jsp/{1}.jsp</result>
</action>  
```

**默认跳转的配置**

通过在XML中配置全局结果,用于当抛出异常或者跳转通用页面时使用.
``` java
<!-- 默认的项目跳转页面,在url拼错时跳转 -->
<default-action-ref name="addAction" /> 	

<!-- 全局返回结果,及其跳转页面 -->
 <global-results> 							
	<!-- 全局"错误"界面 -->
	 <result name="error">/jsp/error.jsp</result>
 </global-results>

<!-- 全局抛出异常跳转页面 -->
 <global-exception-mappings>
	<!-- 全局空指针异常抛出页面 -->
	<exception-mapping exception="java.lang.Exception" result="error"/>
 </global-exception-mappings>
```

**处理结果**
子标签`<param>`有两个属性
		- `location`视图对应的实际位置
		- `parse`是否支持OGNL表达式,默认支持

`<result>` 中 `type`属性

| 属性值    | 说明                      |
| --------- | ------------------------- |
| chain     | 跳转到另一个action        |
| redirect  | 重定向                    |
| plainText | 显示原始代码              |
| stream    | 上传下载时,返回一个数据流 |

### 修改默认后缀
- 可以在`web-xml`中修改

通过在框架加载时修改`struts.action.extension`变量指定为`html`后缀名
``` xml
  <filter>
  	<filter-name>struts2</filter-name>
  	<filter-class>org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter</filter-class>
  	<!-- 修改默认后缀名 -->
   	<init-param>
  		<param-name>struts.action.extension</param-name>
  		<param-value>html</param-value>
  	</init-param>
  </filter>
  <filter-mapping>
  	<filter-name>struts2</filter-name>
  	<url-pattern>/*</url-pattern>
  </filter-mapping>
```

- 可以在`struts.properties`配置文件中修改

修改为逗号分隔的一组参数,表示请求后缀可以为`.action`,`.html`或者空时执行跳转.
``` 
struts.action.extension=action,,html
```

- 可以在`struts.xml`中修改

增加`<constant>`标签配置初始化参数
``` xml
<constant name="struts.action.extension" value="html"></constant>
```


### 参数传递

传递的对象为`User`类,属性如下

``` java
public class User {
	private String username;
	private String password;
	private int age;
	private List<String> books; 
	private List<User> users;
	.......
}
```


1. 可以使用 **`<action>`属性**接收参数
接收的为前台传入的参数

前台使用`name`属性向后台传值
``` html
<h1 style="text-align: center;">登录系统</h1>
<form action="loginAction1.action" method="post"><br>
	用户:<input type="text" name="username">
	密码:<input type="password" name="password">
	<input type="submit" value="提交">
</form>  
```

配置文件

``` xml
<!-- 通过Action属性获得传入参数 -->
<action name="loginAction1" class="action.LoginAction1" method="login"> 
	<result>/jsp/success.jsp</result>
</action>
```

后台

``` java
/**URL:http://localhost:8080/Struts/jsp/login1.jsp */
public String login() {
	System.out.println("获得前台传入参数");
	System.out.println("用户:"+username);
	System.out.println("密码:"+password);
	return SUCCESS;
}
```

2. 可以使用 **`DomainMode`** 接收属性

前台使用`<input>`中通过对象属性方式传入值.
`Struts`标签`<fielderror>`,为表单验证,用来接收验证失败时的后台返回值

``` html
<h1 style="text-align: center;">登录系统</h1>
<form action="loginAction2.action" method="post"><br>
	用户:<input type="text" name="user.username"><s:fielderror name="username"/>
	密码:<input type="password" name="user.password">
	年龄:<input type="text" name="user.age">
	<input type="submit" value="提交">
</form>  
```

配置文件,这里采用通配符的方式进行映射匹配
``` xml
<action name="*Action2" class="action.LoginAction2" method="{1}"> 
	<result>/jsp/success.jsp</result>
	<result name="input">/jsp/login2.jsp</result>
</action>
```

后台,私有对象属性,并提供`get/set()`方法.同时,配合

``` java
private User user;
public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}

/**URL:http://localhost:8080/Struts/jsp/login2.jsp */
public String login() {
	System.out.println("获得前台传入参数:");
	System.out.println("用户:"+user.getUsername());
	System.out.println("密码:"+user.getPassword());
	System.out.println("年龄:"+user.getAge());

	//表单验证部分
	if(user.getUsername() == null || "".equals(user.getUsername().trim())){
		this.addFieldError("username", "用户名为空");
		return INPUT;
	}
	return SUCCESS;
}
```

通过重写`validate()`方法还可以根据验证结果,跳转回表单页面.

``` java
@Override
public void validate() {
	super.validate();
	if(user.getUsername() == null || "".equals(user.getUsername().trim())){
		this.addFieldError("username", "用户名为空");
	}
}
```


3. 可以使用 **` ModelDriven`** 接收参数

前台,使用`<input>`标签传递对象的属性,通过!感叹号指定方法

``` html
<h1 style="text-align: center;">登录系统</h1>
<form action="loginAction3!login" method="post"><br>
	用户:<input type="text" name="username">
	密码:<input type="password" name="password">
	<input type="submit" value="提交">
</form>  
```

配置文件,这里支持使用!感叹号触发事件

``` xml
        <action name="loginAction3" class="action.LoginAction3"> 
        	<result>/jsp/success.jsp</result>
        </action>
```

后台,实现`ModelDriven`接口的`getModel()`方法,前台只需要传递对象属性值,后台就可以组装为对象属性.

``` java
public class LoginAction3 extends ActionSupport implements ModelDriven<User>{
//继承后可以调用常量返回结果
	private User user = new User();
	@Override
	public User getModel() {
		return user;
	}
	/**URL:http://localhost:8080/Struts/jsp/login3.jsp */
	public String login() {
		System.out.println("获得前台传入参数:");
		System.out.println("用户:"+user.getUsername());
		System.out.println("密码:"+user.getPassword());
		System.out.println("书一:"+user.getBooks().get(0));
		System.out.println("书二:"+user.getBooks().get(1));
		System.out.println("人一:"+user.getUsers().get(0).getUsername());
		System.out.println("人二:"+user.getUsers().get(1).getUsername());
		return SUCCESS;
	}
}
```

扩展:前台传入列表

``` xml
<h1 style="text-align: center;">登录系统</h1>
<form action="loginAction3!login" method="post"><br>
	用户:<input type="text" name="username"><br>
	密码:<input type="password" name="password"><br>
	书一:<input type="text" name="books[0]"><br>
	书二:<input type="text" name="books[1]"><br>
	<input type="submit" value="提交">
</form>  
```

扩展:前台传入列表对象

``` html
<h1 style="text-align: center;">登录系统</h1>
<form action="loginAction3!login" method="post"><br>
	用户:<input type="text" name="username"><br>
	密码:<input type="password" name="password"><br>
	书一:<input type="text" name="books[0]"><br>
	书二:<input type="text" name="books[1]"><br>
	人一:<input type="text" name="users[0].username"><br>
	人二:<input type="text" name="users[1].username"><br>
	<input type="submit" value="提交">
</form>  
```


## 拦截器

### 拦截器
![拦截器工作原理][1]
`HttpServletRequest`通过Struts2的各种过滤器,`到达ActionMapper`,判断是否要调用某一个action,如果请求调用的是html,css等静态资源,则不需要调用action;否则,将控制权转交给`ActionProxy`,代理通过`ConfigurationManager`(配置管理)读取`Struts.xml`,找到需要调用的Action,并创建`ActionInvocation`实例,实例中包含了`Action`和`Result`以及前后的`Interceptor`拦截器,依次执行拦截器后进入业务处理,并返回一个结果,经由`Template`来调用视图或者另外一个Action,随后反向执行拦截器,最终通过`HttpServletResponse`响应请求.

方法在Action执行之前或者之后执行.

### 拦截器栈的概念
**结构:** 拦截器栈相当于多个拦截器的组合
**功能:** 拦截器栈也是拦截器-
**工作过程:** 相当于递归的过程	拦截器1->拦截器2->execute->result->拦截器2->拦截器1

### 实现方式
- 通过`Interceptor`接口实现
	- `init()` 拦截器初始化方法
	-  `destroy()` 拦截器销毁方法
	-  ` intercept( ActionInvocation ai)` 实现拦截器功能;ActionInvocation参数获取Actionn状态;返回result字符串作为逻辑视图

- 继承`AbstractInterceptor`抽象类实现
	- 提供`init()`和`destroy()`的空实现
	- 只需要实现`intercept()`方法即可

**步骤:**
1. 创建一个继承自AbstractInterceptor的类
2. 实现类的Intercept方法
3. Struts.xml中注册拦截器
4. 在相应的Action中进行引用

### 内置拦截器和拦截器栈

**内置拦截器**
| 内置拦截器          | 拦截器作用                                               |
| ------------------- | -------------------------------------------------------- |
| params拦截器        | 负责将请求参数设置为Action属性                           |
| static Params拦截器 | 将配置文件中 action元素的子元素param参数设置为Action属性 |
| servletConfig拦截器 | 将ServletAPI的各种对象注入到Action,必须实现对应接口      |
| fileUpload拦截器    | 文件上传提供支持,将文件和元数据设置到对应的Action属性    |
| exception拦截器     | 异常捕获,映射到自定义的错误页面                          |
| validation拦截器    | 调用框架验证的拦截器                                     |

**默认拦截器栈**

在` struts-default.xml`中定义一个`default Stack`拦截器栈,并将其指定为默认拦截器
只要在自定义的包中继承`struts-default`包,则将其拦截器栈作为默认拦截器栈配置
当为包中某个action显示指定了某个拦截器,则默认拦截器不再会起作用
拦截器栈中各个拦截器的顺序很重要

### 自定义拦截器
首先自定义拦截器类

`SpendTimeInterceptor`计算执行Action的时间

``` java
public class SpendTimeInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		
		//执行Action之前的时间
		long start = System.currentTimeMillis();
		//执行下一个拦截器,如果是最后一个,则执行目标Action
		String result = actionInvocation.invoke();
		//执行Action之后
		long end = System.currentTimeMillis();
		System.out.println("执行所花费的时间:"+(end-start)+"ms");
		//返回结果视图
		return result;
	}
}
```
对应Action类

``` java
public class SpendTimeAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		for(int i=0;i<10000;i++){
			System.out.println("loading....");
		}
		return SUCCESS;
	}
}
```


`PrivilegeManagementInterceptor`验证用户是否登录
通过从Session中获取用户信息判断用户是否登录

``` java
public class PrivilegeManagementInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		
		//获取上下文
		ActionContext actionContext = ActionContext.getContext();
		//获取封装了session的map中的用户信息
		Map<String, Object> session = actionContext.getSession();
		if (session.get("userInfo") != null ) {
			String result =actionInvocation.invoke();
			return result;	//用户已登录
		}else{
			return "login";	//用户未登录,需要登录
		}
	}
}
```

对应Action,
实现`SessionAware`接口,并私有`Map<String, Object>`作为Session的容器

``` java
public class PrivilegeManagementAction extends ActionSupport implements SessionAware{

	private static final long serialVersionUID = 1L;

	private User user;
	
	private Map<String, Object> session;	//session的维护容器,实现SessionAware接口获得
	
	
	@Override
	public String execute() throws Exception {
		System.out.println("进入后台");
		
		if("admin".equals(user.getUsername().trim()) && "123456".equals(user.getPassword().trim())){
			session.put("userInfo", user);
			System.out.println("保存用户:"+user.toString());
			return SUCCESS;
		}else{
			session.put("loginError", "用户或密码错误!");
			return ERROR;
		}
	}
	
	public Map<String, Object> getSession() {
		return session;
	}
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
```


注册拦截器类映射文件,

1. 在默认映射文件中包含该文件
2. 注册拦截器
3. Action配置

``` xml
<struts>

	<!-- 子包类,被包含 -->

    <!-- 编码格式 -->
	<constant name="struts.i18n.encoding" value="UTF-8"></constant>

	
    <package name="spendTime" namespace="/" extends="struts-default" abstract="false">
        <!-- 2.拦截器的注册 -->
        <interceptors>
        	<!-- name:拦截器的名字	class:拦截器的实现类 -->
        	<interceptor name="timeSpend" class="interceptor.SpendTimeInterceptor"></interceptor>
        </interceptors>

		<!-- 通过Action属性获得传入参数 -->
        <action name="spendTimeAction" class="interceptor.SpendTimeAction"> 
        	<result>/jsp/success.jsp</result>
        	<!-- 3.引用拦截器 -->
        	<!-- 默认拦截器放在自定义拦截器之前 -->
        	<interceptor-ref name="defaultStack"/>
        	<interceptor-ref name="timeSpend"/>
        </action>
        
    </package>
        <package name="privilege" namespace="/" extends="struts-default" abstract="false">
       
       	<interceptors>
        	<!-- 登录验证拦截器 -->
        	<interceptor name="privilegeManagement" class="interceptor.PrivilegeManagementInterceptor"></interceptor>
        	<!-- 自定义拦截器栈 -->
        	<interceptor-stack name="myStack">
        		<interceptor-ref name="defaultStack"/>				<!-- 系统的 -->
        		<interceptor-ref name="privilegeManagement"/>		<!-- 自己的 -->
        	</interceptor-stack>
       	</interceptors> 

		<!-- 通过URL直接进入,需要拦截器,这里不经过业务逻辑方法 -->
		<action name="url">
			<result>/jsp/manage.jsp</result>
			<result name="login">/jsp/manageLogin.jsp</result>
			<!-- 引用自定义拦截器 -->
			<interceptor-ref name="myStack"/>
		</action>

		<!-- 登录入口,不需要拦截 -->
        <action name="privilegeManagementAction" class="interceptor.PrivilegeManagementAction"> 
        	<result name="success">/jsp/manage.jsp</result>
        	<result name="error">/jsp/manageLogin.jsp</result>
        	<interceptor-ref name="defaultStack"/>
        </action>
        
    </package>

	

</struts>
```


  [1]: https://raw.githubusercontent.com/jionjion/Picture_Space/master/WorkSpace/Java/Struts/Struts-01.png