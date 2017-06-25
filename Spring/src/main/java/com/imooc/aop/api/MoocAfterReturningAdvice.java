package com.imooc.aop.api;
/**后置通知**/
import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;

public class MoocAfterReturningAdvice implements AfterReturningAdvice {

	@Override
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		System.out.println("后置通知....." + method.getName() + "     " + 
			target.getClass().getName() + "       " + returnValue);
	}

}
