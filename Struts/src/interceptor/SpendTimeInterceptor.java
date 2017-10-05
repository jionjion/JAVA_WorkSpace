package interceptor;
/**
 *	计算执行Action时所花费的时间 
 *	1.创建拦截器的类
 *	2.配置拦截器
 */
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SpendTimeInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		
		//执行Action之前的时间
		long start = System.currentTimeMillis();
		//执行下一个拦截器,如果是最后一个,则执行目标Action
		String result = actionInvocation.invoke();
		//执行Action之后
		long end = System.currentTimeMillis();
		System.out.println("执行所花费的时间:"+(end-start)+"ms");
		//返回结果视图
		return result;
	}

}
