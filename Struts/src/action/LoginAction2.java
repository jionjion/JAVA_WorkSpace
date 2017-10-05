package action;

/**
 * 	通过Action的对象进行接收前台
 * 
 * 	如果表单验证失败,或者对象类型不匹配,重定向到登录界面
 * 	验证有两种方式:
 * 		1.在业务逻辑中验证,返回INPUT或者ACCESS
 * 		2.继承方法,在validate()中验证
 */
import com.opensymphony.xwork2.ActionSupport;
import bean.User;

public class LoginAction2 extends ActionSupport {
//继承后可以调用常量返回结果
	
	private User user;
	
	/**URL:http://localhost:8080/Struts/jsp/login2.jsp */
	public String login() {
		System.out.println("获得前台传入参数:");
		System.out.println("用户:"+user.getUsername());
		System.out.println("密码:"+user.getPassword());
		System.out.println("年龄:"+user.getAge());
		
//		//表单验证部分
//		if(user.getUsername() == null || "".equals(user.getUsername().trim())){
//			this.addFieldError("username", "用户名为空");
//			return INPUT;
//		}
		
		return SUCCESS;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
	/**表单验证方法,如果失败,则跳转回登录界面*/
	@Override
	public void validate() {
		super.validate();
		if(user.getUsername() == null || "".equals(user.getUsername().trim())){
			this.addFieldError("username", "用户名为空");
		}
	}

	
	
	

}
