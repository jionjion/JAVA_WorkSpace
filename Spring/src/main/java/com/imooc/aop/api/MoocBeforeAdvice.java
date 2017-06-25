package com.imooc.aop.api;
/**通过接口,实现自定义的前置通知*/
import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;

public class MoocBeforeAdvice implements MethodBeforeAdvice {

	@Override
	public void before(Method method, Object[] args, Object target)
			throws Throwable {
		System.out.println("前置通知使用 : " + method.getName() + "     " + 
				 target.getClass().getName());
	}

}
