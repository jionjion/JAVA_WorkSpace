package com.imooc.aop.api;
/**通过实现接口,完成异常处理通知**/
import java.lang.reflect.Method;

import org.springframework.aop.ThrowsAdvice;

public class MoocThrowsAdvice implements ThrowsAdvice {
	
	public void afterThrowing(Exception ex) throws Throwable {
		System.out.println("异常处理通知......");
	}
	
	public void afterThrowing(Method method, Object[] args, Object target, Exception ex) throws Throwable {
		System.out.println("异常处理中.....参数为:" + method.getName() + "       " + 
				target.getClass().getName());
	}

}
