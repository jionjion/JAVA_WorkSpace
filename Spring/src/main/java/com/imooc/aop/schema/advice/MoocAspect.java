package com.imooc.aop.schema.advice;
/**各种通知的方法,在执行其他类的时候进行调用,**/
import org.aspectj.lang.ProceedingJoinPoint;

public class MoocAspect {
	
	public void before() {
		System.out.println("在执行方法前,进行前置通知.");
	}
	
	public void afterReturning() {
		System.out.println("在执行前方法后,进行后置通知.");
	}
	
	public void afterThrowing() {
		System.out.println("抛出异常了,进行通知.");
	}
	
	public void after() {
		System.out.println("在最后一步前,进行通知");
	}
	
	/**环绕通知一般使用*/
	public Object around(ProceedingJoinPoint pjp) {
		Object obj = null;
		try {
			System.out.println("在类中所有方法执行前,进行环绕通知.");
			obj = pjp.proceed();
			System.out.println("在类中所有方法执行后,进行环绕通知.");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return obj;
	}
	
	/**匹配参数的环绕通知,针对特定参数列表进行匹配**/
	public Object aroundInit(ProceedingJoinPoint pjp, String bizName, int times) {
		System.out.println(bizName + "   " + times);
		Object obj = null;
		try {
			System.out.println("获得环绕通知传入参数.");
			obj = pjp.proceed();
			System.out.println("程序执行后,返回参数.");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return obj;
	}
	
}
