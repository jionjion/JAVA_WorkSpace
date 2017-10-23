package aop.api;
/**环绕通知*/
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class MoocMethodInterceptor implements MethodInterceptor {

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("方法执行前进行环绕通知..........: " + invocation.getMethod().getName() + "     " + 
				invocation.getStaticPart().getClass().getName());
		 Object obj = invocation.proceed();
		 System.out.println("方法执行后进行环绕通知..........: " + obj);
		 return obj;
	}

}
