package action;

/**
 * 	通过ModelDriven接口实现
 * 	不需要get/set方法,但是需要实例化
 */
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import bean.User;

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
