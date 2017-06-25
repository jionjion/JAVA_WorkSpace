package mvcdemo.Interceptor;
/***
 * 	所有的拦截器,默认执行优先级更高
 */
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class AllInterceptor implements HandlerInterceptor {

	/**拦截器执行前方法
	 * true:继续执行
	 * false:停止执行
	 * Object:表示被拦截的对象*/
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		request.setCharacterEncoding("UTF-8");	//编码处理
		response.setContentType("text/html;charset=UTF-8");	
		System.out.println("执行拦截器前的方法");
		System.out.println();
		return true;
	}

	/**拦截器执行方法
	 * ModelAndView:请求的视图,可以对其增加属性或者跳转改变*/
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object, ModelAndView modelAndView)
			throws Exception {
		System.out.println("执行拦截器时的方法");
//		modelAndView.setViewName("/Success");	//可以更改跳转页面
//		modelAndView.addObject("MSG", "从拦截器获得数据");//当跳转为视图时,可以添加,但是响应为返回字符串时,不能使用
	}
	
	/**拦截器通过的方法
	 * 多用于资源的销毁*/
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object, Exception exception)
			throws Exception {
		System.out.println("执行拦截器后的方法");
	}


}
