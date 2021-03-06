package jspBase.bean;


/**
 * 	JavaBean的设计原则
 * 	1.属性私有
 * 	2.访问器方法
 * 	3.无参构造方式
 * 	4.共有类
 * 	5.实现序列化接口*/
public class User{


	private String username;
	
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + "]";
	}
	
	
}
