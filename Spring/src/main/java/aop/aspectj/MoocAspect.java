package aop.aspectj;
/**通过注解的方式实现各种通知的调用***/
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component	//通用bean的注入
@Aspect		//将其转为切面类,不进行代理
public class MoocAspect {
	
	//接入点的配置,指定其扫描包路径
	@Pointcut("execution(* com.imooc.aop.aspectj.biz.*Biz.*(..))")
	public void pointcut() {}
	
	//切入点的配置,确定连接的范围
	@Pointcut("within(com.imooc.aop.aspectj.biz.*)")
	public void bizPointcut() {}
	
	//前置切入点
	@Before("pointcut()")
	public void before() {
		System.out.println("前置通知开始....");
	}
	
	@Before("pointcut() && args(arg)")		//前置通知,并传入参数arg,参数名和方法中的一致
	public void beforeWithParam(String arg) {
		System.out.println("前置通知并传入参数:" + arg);
	}
	
	@Before("pointcut() && @annotation(moocMethod)")	//传入参数,参数的注解的值和方法中的对象名一致
	public void beforeWithAnnotaion(MoocMethod moocMethod) {
		System.out.println("前置通知,并用注解获取方式:" + moocMethod.value());
	}
	
	//后置通知
	@AfterReturning(pointcut="bizPointcut()", returning="returnValue")	//后置通知,并有返回值
	public void afterReturning(Object returnValue) {
		System.out.println("后置通知,收到参数 : " + returnValue);
	}
	
	//抛出异常后的通知
	@AfterThrowing(pointcut="pointcut()", throwing="e")	//抛出异常,并传递异常参数
	public void afterThrowing(RuntimeException e) {
		System.out.println("抛出异常通知 : " + e.getMessage());
	}
	
	//最终通知
	@After("pointcut()")
	public void after() {
		System.out.println("最终通知开始......");
	}

	//环绕通知
	@Around("pointcut()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("环绕通知在方法执行前.");
		Object obj = pjp.proceed();
		System.out.println("环绕通知在方法执行后.");
		System.out.println("环绕通知在方法执行是返回对象为: " + obj);
		return obj;
	}
	
}
