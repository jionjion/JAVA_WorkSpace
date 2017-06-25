package com.imooc.beanannotation.multibean;
/***
 * 	通过注解对泛型进行指定类
 */
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class BeanInvoker {
	
	@Autowired	//自动注解泛型中BeanInterface的类
	private List<BeanInterface> list;
	
	@Autowired
	private Map<String, BeanInterface> map;
	
	@Autowired
	@Qualifier("beanImplTwo")	//通过@Qualifier注解,指定接口的实现类
	private BeanInterface beanInterface;
	
	/**对自动注入的类进行遍历,由于默认为单例模式,所以只是注册了一个类类型*/
	public void say() {
		if (null != list && 0 != list.size()) {
			System.out.println("读取list中...");
			for (BeanInterface bean : list) {
				System.out.println(bean.getClass().getName());
			}
		} else {
			System.out.println("List<BeanInterface> list 为空 !!!!!!!!!!");
		}
		
		System.out.println();
		
		if (null != map && 0 != map.size()) {
			System.out.println("读取map中...");
			for (Map.Entry<String, BeanInterface> entry : map.entrySet()) {
				System.out.println(entry.getKey() + "      " + entry.getValue().getClass().getName());
			}
		} else {
			System.out.println("Map<String, BeanInterface> map 为空 !!!!!!!!!!");
		}
		
		System.out.println();
		if (null != beanInterface) {
			System.out.println("通过@Qualifier注解,指定接口的实现类为:"+beanInterface.getClass().getName());
		} else {
			System.out.println("beanInterface 为空...");
		}
		
		
	}

}
