package mvcdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import mvcdemo.bean.User;

/**登录相关的控制类*/
@Controller
@RequestMapping("/login")
public class LoginController {

	/**	登录页面的展示
	 * 	URL:http://localhost:8080/SpringMVC/login/loginPage*/
	@RequestMapping(value="loginPage",method=RequestMethod.GET)
	public String LoginShow() {
		
		return "Login";
	}
	
	/**登录页面*/
	@RequestMapping(value="login")
	public String Login(@ModelAttribute User user)  {
		
		System.out.println("获取用户信息:"+user.toString());
		return "Success";
	}
}
