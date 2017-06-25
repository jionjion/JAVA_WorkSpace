package com.imooc.aop.aspectj;
/**业务逻辑模块**/
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)	//运行时注解
@Target(ElementType.METHOD)		
public @interface MoocMethod {
	
	String value();

}
