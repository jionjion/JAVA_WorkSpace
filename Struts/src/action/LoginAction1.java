package action;
/**
 * 	通过Action的属性参数进行接收前台
 */
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction1 extends ActionSupport {
//继承后可以调用常量返回结果
	
	private String username;
	private String password;
	
	/**URL:http://localhost:8080/Struts/jsp/login1.jsp */
	public String login() {
		System.out.println("获得前台传入参数");
		System.out.println("用户:"+username);
		System.out.println("密码:"+password);
		return SUCCESS;
	}

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

}
