package mvcdemo.Interceptor;
/***
 * 	
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {

	/**拦截器执行前方法
	 * true:继续执行
	 * false:停止执行
	 * Object:表示被拦截的对象*/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		System.out.println("登录拦截执行前方法");
		//登录验证
		if(request.getSession().getAttribute("username") == null){
			//没有登录,返回到登录页面
			request.getRequestDispatcher("/login/loginPage").forward(request, response);
			return false;
		}
		return true;
	}

	/**拦截器执行方法
	 * ModelAndView:请求的视图,可以对其增加属性或者跳转改变*/
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView)
			throws Exception {
		modelAndView.setViewName("/Success");	//可以更改跳转页面
		modelAndView.addObject("MSG", "从拦截器获得数据");
		System.out.println("登录拦截器执行时");
	}
	
	/**拦截器通过的方法
	 * 多用于资源的销毁*/
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception)
			throws Exception {
		System.out.println("登录拦截器执行后");
	}


}
