# Servlet,Filter,Listener介绍

Tags : JDK8 Eclipse

---

[TOC]

---

## 简介
介绍Web项目中,最为基础的`Servlet`,`Filter`,`Listener`类的使用.

## 文件结构
- `bean`                                  数据库对象模型
- `filter`                                   过滤器
- `listener`                              监听器
- `servlet`                               服务程序
## 相关资料
### Tomcat容器等级
1.  Engine
2.  Host
3.  Servlet容器
4.  Context容器,存放不同的Web项目

### Servlet生命周期
- Servlet容器在启动时自动加载某些Servlet,通过在web.xml文件中的<Servlet></Servlet>标签间添加<loadon-startup>1</loadon-startup>,数字越小,优先级越高
- Servlet文件在更新后,默认重新加载
- Servlet容器启动后,客户端首次向Servlet发送请求.

![生命周期][1]

### Servlet与JSP内置对象

| JSP内置对象 | Servlet类                   |
| ----------- | --------------------------- |
| out         | response.getWriter()        |
| request     | service()方法中request参数  |
| response    | service()方法中response参数 |
| session     | request.getSession()        |
| application | getServletContext()         |
| exception   | Throwable                   |
| page        | this                        |
| pageContext | PageContext                 |
| config      |    getServletConfig()    |


### 过滤器定义
作为服务器端组件,截取用户端的请求和响应信息,并对这些信息进行过滤.

针对同一个URL请求的多个过滤器可以组成过滤器链,根据声明顺序实现依次过滤.

### 过滤器生命周期
1. 实例化,在加载`web.xml`中进行创建
2. 初始化,在`init()`方法中执行
3. 过滤.执行业务逻辑`doFilter()`方法
4. 销毁,调用` destroy()`方法

![过滤器生命周期][3]

### 过滤器转发模式

| 名称    | 说明                                                     |
| ------- | -------------------------------------------------------- |
| REQUEST | 用户直接访问页面时,会调用过滤器                          |
| FORWARD | 通过 requestDispatcher的forward访问时,该过滤器将会被调用 |
| INCLUDE | 通过 requestDispatcher的include访问时,该过滤器将会被调用 |
| ERROR   | 通过异常声明处理机制调用时,过滤器将会被调用              |
| ASYNC   | 异步处理时将会被调用                                     |

### 过滤器属性

| 属性            | 类型            | 描述                                              |
| --------------- | --------------- | ------------------------------------------------- |
| filterName      | String          | 指定过滤器的名字,等价于`<filter-name>`            |
| value           | String[]        | URL中携带参数,等价于`urlPatterns`属性             |
| urlPatterns     | String[]        | 匹配的URL,等价于`<url- pattern>`                  |
| servletNames    | String[]        | 过滤器应用的Servlet类                             |
| dispatcherTypes | DispatcherType  | 过滤器的转发模式 (REQUEST/FORWARD/INCLUDE/ERROR/ASYNC)   |
| initParams      | WebInitParams[] | 过滤器初始化参数,等价于`<init-param>`             |
| asyncSupported  | boolean         | 是否支持异步,等价于`<async-supported>`            |
| description     | String          | 过滤器描述信息,等价于`<description>`              |
| displayName     | String          | 过滤器显示名,配合工具使用,等价于`< display-name>` |


### 监听器定义
对其他对象上发生的事件或者状态改变进行监听和处理的对象,当被监听对象发生改变时,立即采取相应的行为.
多个监听器之间根据注册顺序进行执行.
监听器优先级最高,最先执行;过滤器次之,Servlet最后执行.

### 监听器分类
监听器根据监听对象分类,可以分为`ServletContextListener`,` HttpSessionListener`和` Servlet RequestListener`

**Context监听器**
实现`ServletContextListener`, `ServletContextAttributeListener`接口
可以配置多个监听器,对Web项目中上下文对象进行监听.
- `public void contextInitialized(ServletContextEvent)` 容器初始化时调用
- `public void contextDestroyed(ServletContextEvent)` 容器销毁时调用
- `public void attributeAdded(ServletContextAttributeEvent)` 容器属性增加时调用
- `public void attributeReplaced(ServletContextAttributeEvent)` 容器属性修改时调用
- `public void attributeRemoved(ServletContextAttributeEvent)`容器属性删除时调用


**Session监听器**
实现`HttpSessionListener`,`HttpSessionAttributeListener`,					`HttpSessionBindingListener`,` HttpSessionActivationListener `接口
- `public void sessionCreated(HttpSessionEvent)` 会话创建时调用
- `public void sessionDestroyed(HttpSessionEvent)` 会话销毁时调用
- `public void valueBound(HttpSessionBindingEvent)` 会话绑定对象时调用
- `public void valueUnbound(HttpSessionBindingEvent)` 会话解绑对象时调用
- `public void attributeAdded(HttpSessionBindingEvent)` 会话属性增加时调用
- `public void attributeRemoved(HttpSessionBindingEvent)` 会话属性删除时调用
- `public void attributeReplaced(HttpSessionBindingEvent)` 会话属性修改时调用
- `public void sessionWillPassivate(HttpSessionEvent)` 会话钝化时调用
- `public void sessionDidActivate(HttpSessionEvent)` 会话活化时调用

*Session钝化/活化* :把服务器中不常使用的Session对象暂时序列到系统文件系统或者数据库中,需要时再反序列化到内存中.


**Request监听器**
实现`ServletRequestListener`,`ServletRequestAttributeListener`接口.
- `public void requestInitialized(ServletRequestEvent)` 请求创建时调用
- `public void requestDestroyed(ServletRequestEvent)` 请求销毁时调用
- `public void attributeAdded(ServletRequestAttributeEvent)` 请求属性增加时调用
- `public void attributeReplaced(ServletRequestAttributeEvent)` 请求属性修改时调用
- `public void attributeRemoved(ServletRequestAttributeEvent)` 请求属性删除时调用

## 编码

### 手写Servlet
1. 继承`HttpServlet`类
2. 重写`doGet()`或者`doPost`方法
3. 在`Web.xml`中注册该`Servlet`


### 基于web.xml创建Servlet
![创建流程][2]


### 基于注解创建Serevlet

- 继承`HttpServlet`类,使用`@WebServlet()`指定需要对应的映射路径
``` java

@WebServlet("/servletBase/servlet/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegisterServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		doGet(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		User user = new User();
		String username = null;
		String password = null;
		String email = null;
		String sex = null;
		Date birthday = null;
		String[] hobby  = null;
		String introduce = null;
		boolean flag = false;
		try {
			//获取前台参数
			username = request.getParameter("username");
			password = request.getParameter("password");
			email = request.getParameter("email");
			sex = request.getParameter("sex");
			birthday = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birthday"));
			hobby = request.getParameterValues("hobby");
			introduce = request.getParameter("introduce");
			if( request.getParameterValues("flag")!= null ){
				flag  = true;
			}
			//封装对象属性
			user.setUsername(username);
			user.setPassword(password);
			user.setEmail(email);
			user.setSex(sex);
			user.setBirthday(birthday);
			user.setHobby(hobby);
			user.setIntroduce(introduce);
			user.setFlag(flag);
			//保存到session中
			request.getSession().setAttribute("user", user);
			//跳转请求
			request.getRequestDispatcher("/modules/servletBase/userinfo.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("获取表单参数中出现异常.....");
		}
	}
}
```
- 对应注册页面

``` xml
<html>
  <head>
    <title>登录界面</title>
  </head>
  
  <body>
    <h1>用户注册</h1>
    <form name="regForm" action="<%=basePath %>servletBase/servlet/RegisterServlet" method="post" >
			    	用户名：
			    	<input type="text" name="username" /><br>
			    	密码：
			    	<input type="password" name="password" ><br>
			    	确认密码：
			    	<input type="password" name="confirmpass" ><br>
			    	电子邮箱：
			    	<input type="text" name="email" ><br>
			    	性别：
			    	<input type="radio" name="sex" checked="checked" value="Male">男
			    	<input type="radio" name="sex" value="Female">女<br>
			    	出生日期：
			    	  <input name="birthday" type="date"/><br>
			    	爱好：
				    	<input type="checkbox" name="hobby" value="nba"> NBA &nbsp;
				    	<input type="checkbox" name="hobby" value="music"> 音乐 &nbsp;
				    	<input type="checkbox" name="hobby" value="movie"> 电影 &nbsp;
				    	<input type="checkbox" name="hobby" value="internet"> 上网 &nbsp;<br>
			    	自我介绍：
			    		<textarea name="introduce" rows="10" cols="40"></textarea><br>
			    	接受协议：
			    		<input type="checkbox" name="flag" value="true">是否接受霸王条款<br>
	
	
			    		<input type="submit" value="注册"/>&nbsp;&nbsp;
			    	    <input type="reset" value="取消"/>&nbsp;&nbsp;<br>
			</form>
  </body>
</html>
```


- 对应跳转页面

``` xml
<html>
  <head>
    <title>用户信息显示页面</title>
  </head>
  
  <body>
    <h1>用户信息</h1>
    <hr>
    	<jsp:useBean  id="user" class="servletBase.bean.User" scope="session"/>   
          	用户名：&nbsp;<jsp:getProperty name="user" property="username"/><br>
          	密码：&nbsp;<jsp:getProperty name="user" property="password"/><br>
          	性别：  &nbsp;<jsp:getProperty name="user" property="sex"/><br>
          	E-mail：  &nbsp;<jsp:getProperty name="user" property="email"/><br>
         	出生日期：&nbsp; <%=new SimpleDateFormat("yyyy年MM月dd日").format(user.getBirthday())%><br>
         	爱好：&nbsp;
	            <% 
	               String[] hobby = user.getHobby();
	               for(String h:hobby)
	               {
	            %>
	        <%=h%> &nbsp;&nbsp;
	            <% 
	               }
	            %><br>
          	自我介绍： &nbsp;<jsp:getProperty name="user" property="introduce"/><br>
        	是否介绍协议：&nbsp;<jsp:getProperty name="user" property="flag"/>
  </body>
</html>

```

### 基于web.xml创建过滤器

在`web.xml`中进行配置过滤器,默认为对所有请求进行过滤
``` xml
<!-- 过滤器的配置 -->
<filter>
<filter-name>encodeFilter</filter-name>
<filter-class>servletBase.filter.EncodeFilter</filter-class>
</filter>
<filter-mapping>
<filter-name>encodeFilter</filter-name>
<url-pattern>/*</url-pattern>
<dispatcher>REQUEST</dispatcher>
</filter-mapping>
```
编写过滤器类

``` java
public class EncodeFilter implements Filter {

	/**初始化方法*/
	public void init(FilterConfig fConfig) throws ServletException {
	}

	/**过滤方法*/
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		chain.doFilter(request, response);
	}

	/**销毁方法*/
	public void destroy() {
	}
}
```

### 基于注解创建过滤器

``` xml
@WebFilter(filterName="AsynFilter",value={"/servletBase/*"},dispatcherTypes={DispatcherType.ASYNC,DispatcherType.REQUEST})
public class AsynFilter implements Filter {

    public AsynFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("执行异步过滤器前------------");
		chain.doFilter(request, response);
		System.out.println("执行异步过滤器后------------");
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
```


### 基于web.xml创建监听器

- 配置`web.xml`文件

``` xml
  <!-- 监听器的配置 -->
  <listener>
  	<listener-class>servletBase.listener.ContextListener</listener-class>
  </listener>
```

- 创建对应的监听器类

``` java
public class ContextListener implements ServletContextListener, ServletContextAttributeListener {

    public ContextListener() {
    }

    public void contextInitialized(ServletContextEvent arg0)  { 
    	System.out.println("context容器的初始化方法");    
    }
    public void contextDestroyed(ServletContextEvent arg0)  { 
    	System.out.println("context容器的销毁方法");
    }
    
    public void attributeAdded(ServletContextAttributeEvent arg0)  { 
    	System.out.println("context容器的对象属性增加");
    }
    public void attributeReplaced(ServletContextAttributeEvent arg0)  { 
    	System.out.println("context容器的对象属性修改");
    }
    public void attributeRemoved(ServletContextAttributeEvent arg0)  {
    	System.out.println("context容器的对象属性删除");
    }
	
}
```

### 基于注解创建监听器

在需要的监听器类上使用`WebListener`注解进行配置

``` java
@WebListener//使用注解,完成配置
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener, 
											HttpSessionBindingListener, HttpSessionActivationListener {

    public SessionListener() {
    }

    public void sessionCreated(HttpSessionEvent arg0)  { 
    	System.out.println("Session创建");
    }
    public void sessionDestroyed(HttpSessionEvent arg0)  { 
    	System.out.println("Session销毁");
    }
    public void valueBound(HttpSessionBindingEvent arg0)  { 
    	System.out.println("Session绑定对象");
    }
    public void valueUnbound(HttpSessionBindingEvent arg0)  { 
    	System.out.println("Session解接触绑定对象");
    }
    public void attributeAdded(HttpSessionBindingEvent arg0)  { 
    	System.out.println("Session属性增加");
    }
    public void attributeRemoved(HttpSessionBindingEvent arg0)  { 
    	System.out.println("Session属性删除");
    }
    public void attributeReplaced(HttpSessionBindingEvent arg0)  { 
    	System.out.println("Session属性修改");
    }
    public void sessionWillPassivate(HttpSessionEvent arg0)  { 
    	System.out.println("Session执行钝化");
    }
    public void sessionDidActivate(HttpSessionEvent arg0)  { 
    	System.out.println("Session执行活化");
    }
	
}
```



  [1]: https://raw.githubusercontent.com/jionjion/Picture_Space/master/WorkSpace/Java/ServletClient/servletBase-01.jpg
  [2]: https://raw.githubusercontent.com/jionjion/Picture_Space/master/WorkSpace/Java/ServletClient/servletBase-02.jpg
  [3]: https://raw.githubusercontent.com/jionjion/Picture_Space/master/WorkSpace/Java/ServletClient/servletBase-03.jpg