package interceptor;
import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
/**
 * 	权限登录管理拦截器
 */
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

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
