# JAVA中JDBC连接MySQL数据库

Tags : JDK8 Eclipse jdbc

---

[TOC]

---

## 简介
使用jdbc连接MySQL数据库,完成基本的增删改查,事务控制和存储过程的调用.

## 包结构

* `bean`  封装对象模型

## 子包描述
### `bean`包
包含一个`User`类,里面封装了用户模型类的属性,其中`code`属性为将来验证邮箱时交由客户端携带验证的哈希码.

``` java
public class User {
	//用户ID
	private int uid;
	//用户名
	private String username;
	//账号密码
	private String password;
	//账号昵称
	private String nickname;
	//邮箱
	private String email;
	//账号状态
	private int state;
	//验证hash
	private String code;
......	
}	
```

### `dao`包
