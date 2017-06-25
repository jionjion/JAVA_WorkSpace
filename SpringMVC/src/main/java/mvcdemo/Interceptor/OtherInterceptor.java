package mvcdemo.Interceptor;
/***
 * 	拦截器的其他实现方式
 */
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

public class OtherInterceptor implements WebRequestInterceptor {

	/**拦截器执行前,不能终止请求*/
	@Override
	public void preHandle(WebRequest request) throws Exception {

		System.out.println("其他拦截器执行前");
	}

	/**拦截器执行时*/
	@Override
	public void postHandle(WebRequest request, ModelMap model) throws Exception {

		System.out.println("其他拦截器执行时");
	}

	/**拦截器执行后*/
	@Override
	public void afterCompletion(WebRequest request, Exception ex) throws Exception {

		System.out.println("其他拦截器执行后");
	}

}
