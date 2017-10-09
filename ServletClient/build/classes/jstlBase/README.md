# JSTL标签介绍

Tags : JDK8 Eclipse

---

[TOC]

---

## 简介
介绍Web项目中`JSTL`标签的使用.

## 文件结构
- `baseI.jsp`                                    out标签的使用
- `baseII.jsp`                                   set标签的使用
- `baseIII.jsp`                                  remove标签的使用
- `baseIV.jsp`                                  catch标签的使用
- `baseV.jsp`                                   if标签的使用
- `baseIX.jsp`                                  import标签的使用
- `baseVI.jsp`                                  choose标签的使用
- `baseVII.jsp`                                 forEach标签的使用
- `baseVIII.jsp`                                forTokens标签的使用
- `baseX_01.jsp`,`baseX_02.jsp`   redirect标签的使用
- `baseXI.jsp`                                 url标签的使用
- `baseXII.jsp`                               常用函数

## 相关资料
### `JSTL`介绍
`JSTL`是java中一个定制标记库集,它的引入可以提高代码可读性和复用性.
通过在页面引入`<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>`实现

### `EL`介绍
**背景**
全称为 *Expression  Language* 是一种表达式语言,经常与`JSTL`标签库一起使用,便于数据的获取.
**格式**
用美元符号`$`开始,内容包含在`{}`花括号中.
使用`${对象.属性}`或者`${对象["属性"]}`方式获取数据从响应中获取数据
取值范围依次从`PageScope`,`RequestScope`,`SessionScope`,`ApplicationScope`中进行检索.

**内置对象**

| 对象             | 说明                                        |
| ---------------- | ------------------------------------------- |
| pageContext      | PageContext实例对应的当前页面的处理         |
| pageScope        | 页面作用域的Map类                           |
| requestScope     | 请求作用域的Map类                           |
| sessionScope     | 会话作用域的Map类                           |
| applicationScope | 应用程序作用域的Map类                       |
| param            | 名称储存请求参数的主要值的Map类             |
| paramValues      | 将请求参数的所有值作为String数组储存的Map类 |
| header           | 按名称储存请求头的Map类                     |
| headerValues     | 将请求头的所有值作为String数组储存的Map类   |
| cookie           | 按名称储存请求附带的cookie的Map类           |
| initParam        | 按名称储存Web应用上下文初始化参数的Map类    |

**EL运算符**
运算符允许数据和文本进行组合,比较

| 类别       | 运算符                                            |
| ---------- | ------------------------------------------------- |
| 算术运算符 | + , - , * , /(div) , %(mod)                       |
| 关系运算符 | ==(eq) , !=(ne) , <(lt) , >(gt) , <=(le) , >=(ge) |
| 逻辑运算符 |     empty               |

## JSTL核心标签

| 功能           | 举例                                   |
| -------------- | -------------------------------------- |
| 表达式控制标签 | `out` , `set` , `remove` , `catch`     |
| 流程控制标签   | `if` , `choose` , `when` , `otherwise` |
| 循环标签       | `forEach` , `forTokens`                |
| URL操作标签    | `import` , `url` , `redirect`          |

### `out`标签
- 可以在`value`属性中定义常量,用来输出
- 变量不存在时配合`default`属性输出默认值,还可以通过`escapeXml`控制转义字符的输出方式

``` xml
<!-- 直接输出 -->
<c:out value="通过out标签实现输出"></c:out>
<br>

<% request.setAttribute("username", "Jion"); %>
<!-- 使用EL对象输出变量 -->
<c:out value="${username}"></c:out>
<br>
<!-- 当没有值的时候输出默认值 -->
<c:out value="${nothing}" default="默认值"></c:out>
<br>

<!-- 使用转义字符,默认不支持.	escapeXml="false"开启转义功能 -->
<c:out value="&lt;out标签&gt;" escapeXml="false"></c:out>

<!-- EL运算符 -->
<c:out value="是否为空或空串:${empty username }"></c:out>
```


### `set`标签
- 存放值到Scope中.

``` xml
<!-- 第一种,存值到Scope中 -->
<c:set value="我是Session中的变量" var="param1" scope="session"></c:set>
<c:out value="${param1 }"></c:out>
<br>

<!-- 第二种,存值到Application中 -->
<c:set var="param2" scope="application">我是Application中的变量</c:set>
<c:out value="${param2 }"></c:out>
<br>

<!-- 第三种,存值到JavaBean中 -->
<jsp:useBean id="user" class="jspBase.bean.User"></jsp:useBean>
<c:set target="${user}" property="username" value="Jion"></c:set>
<c:out value="${user.username}"></c:out>
<c:set target="${user}" property="password">123456</c:set>
<c:out value="${user.password}"></c:out>
```


### `remove`标签
- 删除某个变量,`var`属性必填,`scope`选填.

``` xml
<c:set var="param" value="我是准备删除的变量"  scope="request"></c:set>
<c:remove var="param"/>
<c:out value="${param}"></c:out>
```


### `catch`标签
- 包含那些容易出错的JSTL标签,逻辑

``` xml
<!-- 使用默认的error关键字,保存错误信息 -->
<c:catch var="error">
	<c:set target="语法错误.抛出异常..."/>
</c:catch>
<!-- 输出错误信息 -->
<c:out value="${error }"/>
```


### `if`标签
- 实现分支条件控制 `test`属性存放判断的条件,一般使用EL表单时编写;`var`属性指定名称来存放变量,判断结果为`true`或`false`;`scope`用来存放`var`属性的取值范围.

``` xml
<!-- if进行成绩判断 -->
<c:if test="${param.score >= 90}" var="result1">
	<c:out value="恭喜,成绩优秀"></c:out>
</c:if>
<c:if test="${param.score >= 60 && param.score < 90}" var="result2">
	<c:out value="你还好...."></c:out>
</c:if>
<c:if test="${param.score < 60}" var="result3">
	<c:out value="滚去学习.."></c:out>
</c:if>
```



### `choose`,`when`,`otherwise`标签的用法
- 通常联合使用, `choose`标签作为父标签,嵌套两者
-  `choose`和`when`也可以一起使用

``` xml
<c:choose>
	<c:when test="${param.score >= 90}">
		<c:out value="恭喜,成绩优秀"></c:out>
	</c:when>

	<c:when test="${param.score >= 60 && param.score < 90}">
		<c:out value="你还好...."></c:out>
	</c:when>

	<c:otherwise>
		<c:out value="滚去学习.."></c:out>
	</c:otherwise>
</c:choose>
```


### `forEach`标签
- 根据循环条件遍历集合.
	- `var`设定设定变量名用于储存集合中取出的元素
	- `items` 指定要遍历的集合
	- `begin`,`end`指定遍历的起始位置和结束位置,默认全部遍历
	- `step`循环的步长,默认为1
	- `varStatus`通过`index`,`count`,`first`,`last`几个状态值,描述`begin`和`end`子集中的元素状态.

``` xml
<% 
	List<String> lists = new ArrayList<String>();
	lists.add("元素一");
	lists.add("元素二");
	lists.add("元素三");
	lists.add("元素四");
	lists.add("元素五");
	request.setAttribute("lists", lists);
%>

<!-- 使用forEach遍历全部 -->
<c:forEach var="list" items="${lists }">
	<c:out value="${list}"></c:out><br>
</c:forEach>
<c:out value="------------------"></c:out>
<br>

<!-- 使用forEach遍历部分,[0,2]计3个元素 -->
<c:forEach var="list" items="${lists }" begin="0" end="2">
	<c:out value="${list}"></c:out><br>
</c:forEach>
<c:out value="------------------"></c:out>
<br>

<!-- 使用forEach,从0开始,每两个进行一次遍历. -->
<c:forEach var="list" items="${lists }" step="2">
	<c:out value="${list}"></c:out><br>
</c:forEach>
<c:out value="------------------"></c:out>
<br>

<!-- 使用forEach遍历全部,并输出属性值 -->
<c:forEach var="list" items="${lists }" varStatus="li">
	<c:out value="${list}的四个属性"></c:out>
	<c:out value="index属性:${li.index}"></c:out>		<!-- 从0开始 -->
	<c:out value="index属性:${li.count}"></c:out>		<!-- 计数,从1开始 -->
	<c:out value="first属性:${li.first}"></c:out>		<!-- 第一个返回为true -->
	<c:out value="last属性:${li.last}"></c:out>		<!-- 最后一个返回为true -->
	<br>
</c:forEach>
<c:out value="------------------"></c:out>
```

### `forTokens`标签的用法
- 用于浏览字符串,并根据指定的字符将字符串截取
	- `items`指定被迭代的字符串
	- `delims`指定使用的分隔符
	- `var`指定用来存放遍历到的成员
	- `begin`,`end`指定遍历的开始位置,结束位置
	- `step`遍历的步长,大于0的整形
	- `varStatus`通过`index`,`count`,`first`,`last`几个状态值,描述`begin`和`end`子集中的元素状态

``` xml
<!-- 对指定字符串进行拆分 -->
<c:forTokens items="2017-7-18" delims="-" var="item">
	<c:out value="${item}"/><br>
</c:forTokens>
```



### `import`标签
- 可以把其他静态或者动态文件包含到本JSP页面中
	- `url`被导入资源的URL路径
	- `context`相同服务器下的其他web资源,必须以`/`开头
	- `var`以String类型存入被包含的文件的内容
	- `scope`取值的范围
	- `charEncoding`引入文件的字符编码
	- `varReader`以Reader类型储存被包含的文件内容

**`<c:import>`和`<jsp:include>`区别**
`<jsp:include>`只能包含同一个Web应用中的文件.
`<c:import>`可以包含其他Web应用中的文件,甚至是网络上的资源.需要修改tomcat配置文件`context.xml`中的`crossContext="true"`;


``` xml
<!-- 使用catch标签结合out标签进行异常捕获 -->
<c:catch var="error">	

	<!-- 导入网络上的绝对路径 -->
	<c:import url="http://www.baidu.com" charEncoding="UTF-8"/>

	<!-- 导入相对路径 -->
	<c:import url="baseIX.text" charEncoding="UTF-8"/>

	<!-- 导入其他项目的文件,注意先后运行顺序 -->
	<%-- <c:import url="baseIX.text" context="其他的" charEncoding="UTF-8"/> --%>

</c:catch>
<c:out value="${error}"/>
```


### `redirect`标签
- 实现请求的重定向,同时可以在URL中加入指定的参数
	- `url`指定重定向的地址,可以是相对地址或者绝对地址
	- `context`用来导入其他Web应用中的页面

``` xml
<!-- 重定向服务,url路径改变 -->
<c:redirect url="baseX_02.jsp">
	<!-- 传入参数 -->
	<c:param name="username">Jion</c:param>
	<c:param name="password">123456</c:param>
</c:redirect>

<!-- 在另一个页面中 -->
<c:out value="用户:${param.username }"></c:out>
<c:out value="密码:${param.password }"></c:out>	
```



### `url`标签
- 动态生成一个String类型的URL,可以同`<c:param>`标签共同使用
	- `value`表示url路径
	- `var`将url路径储存在变量中
	- `scope`变量的储存范围

``` xml
<!-- 动态生成URL -->
<c:if test="${1<2}">
	<!-- 成立,设置新的变量 -->
	<c:set var="changeParam">username=jion</c:set>
</c:if>
<!-- 在URL中添加新的URL -->
<c:url var="newURL" value="http://localhost:8080/ServletClient?${changeParam}" scope="session"></c:url>
<a href="${newURL}"	>新的URL</a>
```

### 常用函数
引入标签库
`<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>`
``` xml
<!-- 判断一个字符串中是否包含另一个字符串 -->
<c:out value="'锄禾日当午'中是否包含词语'锄禾':${fn:contains('锄禾日当午','锄禾') }"></c:out>
<br>

<!-- 判断一个字符串中是否包含另一个字符串,忽略大小写 -->
<c:out value="'hello world'中是否包含词语'HELLO':${fn:containsIgnoreCase('锄禾日当午','锄禾') }"></c:out>
<br>

<!-- 判断一个字符串是否以XX结尾 -->
<c:out value="'hello world'中是否以'he'开头:${fn:startsWith('hello world','he') }"></c:out><br>

<!-- 判断一个字符串是否以XX结尾 -->
<c:out value="'hello world'中是否以'ld'结尾:${fn:endsWith('hello world','ld') }"></c:out><br>

<!-- 判断一个字符串中子串的位置 -->
<c:out value="'hello world'中ll的位置:${fn:indexOf('hello world','ll') }"></c:out><br>

<!-- 输出xml文件 -->
<c:out value="${fn:escapeXml('<book>我不喜欢这个世界,我只喜欢你</book>')}"></c:out><br>
<c:out value="<book>我不喜欢这个世界,我只喜欢你</book>"></c:out>
```
