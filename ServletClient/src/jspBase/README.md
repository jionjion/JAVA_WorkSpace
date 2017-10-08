# JSP模板引擎介绍

Tags : JDK8 Eclipse

---

[TOC]

---

## 简介
介绍Web项目中`JSP`模板的使用.

## 文件结构
- `baseI.jsp`                                   JSP页面的基本组成部分
- `baseII.jsp`                                  out内置对象
- `baseIII_01.jsp`,`baseIII_01.jsp`  request内置对象
- `baseIV.jsp`                                 response内置对象
- `baseV.jsp`                                  session内置对象
- `baseVI.jsp`                                 appliance内置对象
- `baseVII.jsp`                                page内置对象
- `baseVIII.jsp`                              pageContext内置对象
- `baseIX_01.jsp`,`baseIX_02.jsp`  exception内置对象,发生错误的页面及处理错误的页面
- `baseX_01.jsp`,`baseX_02.jsp`,`baseX_03.jsp`,`User.class`  JSP页面绑定JavaBean实例
- `baseXI_01.jsp`,`baseXI_02.jsp`  cookie的使用
- `baseXII_01.jsp`,`baseXII_02.jsp`,`baseXII_03_Unicode.txt`,`baseXII_03_UTF8.txt` include动作和标签的区别使用
- `baseXIII.jsp`                               动作标签`forward`和`param`的使用

## 相关资料
### Tomcat服务器目录

| 目录     | 说明                                |
| -------- | ----------------------------------- |
| /bin     | 各种平台下启动,停止服务器的脚本     |
| /conf    | 存放配置文件                        |
| /lib     | 存放Jar包                           |
| /logs    | 存放日志                            |
| /temp    | 临时文件                            |
| /webapps | 项目发布时,将项目拷贝到此文件夹下   |
| /work    | 解析JSP页面,生成Servlet放在该目录下 |

### WEB-INF目录结构
- WEB-INF是JAVA的WEB应用的安全目录,客户端无法访问,仅支持服务器端访问,'
- web.xml,项目部署文件
- classes文件夹,用于存放`*.class`文件.
- lib文件夹,用于存放需要的jar包.

### get/post区别
**get** 
- 以明文方式提交通过URL提交数据,数据在URL中可以看到.提交的数据最多不超过2KB.
- 安全性较低,但是效率较高,适合提交数据量不大,安全性不高的数据.如:检索,查询等..

**post**
- 将用户提交的信息封装在html header内.适合提交数据量大,安全性高的用户信息.如:注册,上传等..

### 请求/转发区别
**请求重定向**
客户端行为,`response.sendRedirect()`,从本质上讲等同于两次请求,前一次的请求对象不会保存,地址栏中的URL地址会改变.

**请求转发**
服务器行为,`request.getRequestDispatcher().forward(req,resp)`,前后看做同义词请求,请求参数保留,地址栏中的URL地址不会改变.

## JSP构成元素
JSP页面由指令,注释,脚本,声明,表达式,构成的.

### 指令元素
**page**
jsp页面的顶端,同一个页面可以有多个page指令

| 属性        | 描述                      | 默认值               |
| ----------- | ------------------------- | -------------------- |
| language    | JSP页面的脚本语言         | java                 |
| import      | 引用脚本中使用的类文件    | 无                   |
| contentType | 指定JSP页面采用的编码方式 | text/html,ISO-8859-1 |
| errorPage   | 指定错误处理页面          |                      |
| isErrorPage |    当前页面是否为错误页面     |                      |

**include**
将外部的JSP包含到当前页面中.

**taglib**
使用标签库中其他的标签.

### 注释
JSP中使用到的注释有html注释,jsp注释,脚本注释

``` html
<!-- HTML注释.客户端注释 -->
<%-- JSP注释.服务器可见 --%>
<%
	//单行注释
	/*	多行注释
		多行注释	
	*/
%>
```

### 脚本
具有一段JAVA代码的部分.

``` java
<%	//jsp脚本
	out.print("打印到页面");
	System.out.println("打印到控制台");
	String author = "张谦";		//全局变量
%>
```

### 声明
在代码片中进行变量的或者方法的创建

``` java
<%!	//JSP声明,注意分号结束
	String author = "JION";		//局部变量
	int sum( int x , int y ){return x+y;}
%>
```

### 表达式
对变或者声明进行使用

``` java
<%=this.author %>	<!-- 局部变量 -->
<%=author %>		<!-- 全局变量 -->
<%=sum(1,2) %>		<!-- 调用表达式 -->
```

## JSP生命周期
![JSP生命周期][1]
jspService()方法被用来处理客户端的请求.对于每一个请求,JSP引擎常创建一个新的线程来处理该请求,多个请求则使用多线程处理.Servlet常驻内存作为相应响应.

## JSP内置对象
内置对象即是在JSP页面中,不使用`new`关键词就可以直接使用的对象.
### out对象
out对象是`JspWriter`类的实例,是向客户端输出内容常用的对象.

常用方法如下:

| 返回值  | 方法            | 说明                                             |
| ------- | --------------- | ------------------------------------------------ |
| void    | println()       | 向客户端打印字符串                               |
| void    | clear()         | 清除缓冲区内容,如果在flush之后调用则抛出异常     |
| void    | clearBuffer()   | 清除缓冲区内容,如果在flush之后调用则不会抛出异常 |
| void    | flush()         | 将缓冲区内容输出到客户端                         |
| int     | getBufferSize() | 返回缓冲区字节数大小,如果不设置缓冲区则为0       |
| int     | getRemaining()  | 返回缓冲区剩余多少可用                           |
| boolean | isAutoFlush()   | 返回缓冲区满时,是自动清空还是抛出异常            |
| void    | close()         | 关闭输出流                                       |

### request对象
客户端的请求信息封装在request对象中,通过它可以了解到客户的需求,然后做出响应.
它是`HttpServletReques`t类的实例.`request`对象具有请求域,即完成客户端的请求之前,该对象一直有效.

| 返回值   | 方法                        | 说明                             |
| -------- | --------------------------- | -------------------------------- |
| String   | getParameter(String)        | 返回name指定参数的参数值         |
| String[] | getParameterValues(String)  | 返回包含参数name的所有值的数组   |
| void     | SetAttribute(String,Object) | 储存请求中的属性                 |
| Object   | getAttribute(String)        | 获得请求中的属性                 |
| String   | getContentType()            | 获得请求中的MIME类型             |
| String   | getProtocol()               | 返回请求中用到的协议类型及版本号 |
| String   | getServerName()             | 返回接收请求的服务器主机名       |

### response对象
response对象包含了响应客户请求的有关信息,它是`HttpServletResponse`类的实例.`response`对象具有页面作用域,仅对本次访问有效.

| 返回值      | 方法                   | 说明                       |
| ----------- | ---------------------- | -------------------------- |
| String      | getCharacterEncoding() | 返回响应的字符编码         |
| void        | setContentType(String) | 设置响应的MIME类型         |
| PrintWriter | getWriter()            | 返回向客户端输出字符的对象 |
| void        | sendRedirect(String)   |                            |

### session对象
**会话:**  客户端打开浏览器并连接到服务器开始,到客户关闭浏览器离开这个服务结束,被称为一个会话.
session在网站加载时自动创建,完成会话管理,便于在不同页面间进行切换管理.它是`HttpSession`的实例.

| 返回值   | 方法                        | 说明                                        |
| -------- | --------------------------- | ------------------------------------------- |
| long     | getCreationTime()           | 获得Session的创建时间                       |
| String   | getId()                     | 获得Session的ID                             |
| void     | setAttribute(String,Object) | 绑定参数到会话中                            |
| Object   | getAttribute(String)        | 获得参数在会话中                            |
| String[] | getValueNames()             | 返回一个包含本次Session中所有可用属性的数组 |
| int      | getMaxInactiveInterval()    |                                             |

**Session生命周期**
- 创建
当客户第一次发出请求时,服务器会为当前会话创建一个SessionId,记录请求者,每次客户端相服务器端发送请求时,都会将此SessionId携带,服务端会对此SessionId进行校验.
- 活动
某次会话中通过超链接打开的新页面属于同一次会话
只要当前会话页面没有全部关闭,重新打开浏览器访问同一项目资源时属于同一会话.
除非本次会话的所有页面都关闭后再重新访问,此时将会创建新的会话.
- 销毁
原有的会话在失去客户端校验时会继续保存在服务端,等待销毁.
当Session过期时或者调用`session.invalidate()`方法或者重启服务器时,将会完全销毁

### application对象
存放全局变量,实现不同用户间的数据共享,不同用户可以对application对象中的同一属性进行操作.
application开始于服务器启动,终止于服务器关闭.它是`ServletContext`类的实例.

| 返回值      | 方法                        | 说明                                    |
| ----------- | --------------------------- | --------------------------------------- |
| void        | setAttribute(String,Object) | 使用指定名称将对象绑定到此会话          |
| Object      | getAttribute(String)        | 返回与此会话中指定名称绑定在一起的对象. |
| Enumeration | getAttributeNames()         | 返回所有可以用的属性名枚举              |
| String      | getServerInfo               | 返回JSP/Servlet引擎和版本号             |

### page对象
指向当前JSP页面本身,类似this指针.是`Object`类的实例

| 返回值  | 方法           | 说明                      |
| ------- | -------------- | ------------------------- |
| class   | getClass()     | 获得当前类                |
| int     | hashCode()     | 获得当前类的hash值        |
| boolean | equals(Object) | 判断两个对象是否相等      |
| void    | copy(Object)   | 拷贝对象                  |
| Object  | clone()        | 克隆对象                  |
| String  | toString()     | 转为字符串                |
| void    | notify()       | 唤醒一个等待的线程        |
| void    | notifyAll()    | 唤醒所有的等待线程        |
| void    | wait(timeout)  | 挂起,等待时间到或者被唤醒 |
| void    | wait()         |                           |

### pageContext对象
提供了对JSP页面内所有对象及名字空间的访问.
它是`PageContext`类的实例.

| 返回值          | 方法                        | 说明                                      |
| --------------- | --------------------------- | ----------------------------------------- |
| JspWriter       | getOut()                    | 获得JspWriter流(out)                      |
| HttpSession     | getSession()                | 获得HttpSession对象(session)              |
| Object          | getPage()                   | 获得当前页面Object对象(page)              |
| ServletRequest  | getRequest()                | 获得当前页的ServletRequest对象(request)   |
| ServletResponse | getResponse()               | 获得当前页的Servletresponse对象(response) |
| void            | setAttribute(String,Object) | 设置属性,属性值                           |
| Object          | getAttributeScope(String)   | 获得某属性的作用范围                      |
| void            | forward(String)             | 跳转到另一个页面                          |
| void            | include(String)             | 当前位置包含另一个文件                    |

### config对象
JSP引擎为Servlet初始化时传递参数,通常由键值对构成.

| 返回值         | 方法                     | 描述                                       |
| -------------- | ------------------------ | ------------------------------------------ |
| ServletContext | getServletContext()      | 获得含有服务器相关信息的ServletContext对象 |
| String         | getInitParameter(String) | 获得初始化参数                             |
| Enumeration    | getInitParameterNames()  | 获得Servlet初始化所需要所有参数的枚举      |

### exception对象
exception对象是一个异常对象,当页面发成异常时,产生该对象.
如果一个JSP页面需要此对象,必须把isErrorPage属性设置为true,否则无法编译
它是`Throwable`类的对象.7

| 返回值    | 方法               | 描述               |
| --------- | ------------------ | ------------------ |
| String    | getMessage()       | 获得异常详细描述   |
| String    | toString()         | 获得异常简要描述   |
| void      | printStackTrace()  | 显示异常堆栈轨迹   |
| Throwable | fillInStackTrace() | 重写异常的执行轨迹 |

## JSP动作元素
动作元素为请求处理阶段提供信,遵循XML语法,具有开始标签,结束标签,文本内容,属性构成.

| 分类             | 列举                                                                                                                                                                                                      |
| ---------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| JavaBean相关     | <code>&lt;jsp:useBean&gt;</code> <code>&lt;jsp:setProperty&gt;</code>  <code>&lt;jsp:getProperty&gt;</code>                                                                                               |
| 动作元素         | <code>&lt;jsp:include&gt;</code> <code>&lt;jsp:forward&gt;</code>  <code>&lt;jsp:params&gt;</code> <code>&lt;jsp:param&gt;</code> <code>&lt;jsp:plugin&gt;</code> <code>&lt;jsp:fallback&gt;</code>       |
| JSP Document相关 | <code>&lt;jsp:root&gt;</code>  <code>&lt;jsp:declaration&gt;</code>  <code>&lt;jsp:scriptlet&gt;</code> <code>&lt;jsp:expression&gt;</code> <code>&lt;jsp:text&gt;</code> <code>&lt;jsp:output&gt;</code> |
| 生成XML元素      | <code>&lt;jsp:attribute&gt;</code> <code>&lt;jsp:body&gt;</code> <code>&lt;jsp:element&gt;</code>                                                                                                         |
| Tag File相关     | `<jsp:invoke>`  `<jsp:dobody>`                                                                                                                                                                            |

### JavaBean相关
1. 创建JavaBean实例
2. 在JSP页面中使用jsp动作标签使用JavaBean

**引用Bean**
``` xml
<jsp:useBean id="user" type="jspBase.bean.User" class="jspBase.bean.User" scope="page"/>
```

**赋值Bean**
方式一:
跟表单相关,关联属性,完全匹配
``` xml
<jsp:setProperty property="*" name="user"/>
```

方式二:
跟表单相关,关联属性,指定匹配
``` xml
<jsp:setProperty property="username" name="user"/>
```


方式三:
自定义属性匹配

``` xml
<jsp:setProperty property="username" name="user" value="Jion"/>
<jsp:setProperty property="password" name="user" value="123456"/>
```


方式四:
通过Post请求传递参数

``` xml
<jsp:setProperty property="username" name="user" param="URI"/>
	<jsp:setProperty property="password" name="user" param="URI"/> 
```

**使用Bean**

``` xml
<jsp:useBean id="myUser" type="jspBase.bean.User" class="jspBase.bean.User" scope="page"/>
用户:<%=user.getUsername() %><br>
密码:<%=user.getPassword() %><br>
```

## Cookie使用
Cookie是由服务器写入在本地浏览器端的一串长度有限的字符串,完成用来做为本地数据暂存.

| 返回值   | 方法                       | 说明                      |
| -------- | -------------------------- | ------------------------- |
| Cookie   | Cookie(String,Object)      | 创建Cookie对象            |
| void     | response.addCookie(Cookie) | 写入Cookie对象            |
| Cookie[] | request.getCookies()       | 获得Cookie对象            |
| void     | setMaxAge(int)             | 设置Cookie有效期,单位秒   |
| void     | setValue(String)           | 对cookie赋值              |
| String   | getName()                  | 获得cookie名称            |
| String   | getValue()                 | 获得cookie的值            |
| int      | getMaxAge()                | 获得cookie有效时间,单位秒 |

**session/cookie区别**

| session        | cookie           |
| -------------- | ---------------- |
| 服务器端保存   | 客户端保存       |
| Object类型     | String类型       |
| 会话结束而销毁 | 长期保存在客户端 |
| 保存重要信息   |  保存不重要信息 |


## 动作标签

### incluede动作
`<jsp:include>`实现将页面引入.

| include指令                                           | include动作                                     |
| ----------------------------------------------------- | ----------------------------------------------- |
| `<%@include  file="页面,文件" %>`                     | `<jsp:include  page="页面"  flush='true'>`      |
| include的指令支持的文本格式为Unicode格式,操作系统格式 | include的动作支持的文本格式为UTF-8,项目创建格式 |
|                                                       | 支持缓冲使用                                    |


### forward动作与param动作
`<jsp:forward>`实现页面跳转,与`<jsp:param>`实现转发.

``` xml
<jsp:forward page="baseXII_01.jsp">
	<jsp:param value="Jion" name="username"/>
	<jsp:param value="123456" name="password"/>
</jsp:forward>
```


  [1]: https://raw.githubusercontent.com/jionjion/Picture_Space/master/WorkSpace/Java/ServletClient/jspBase-01.jpg