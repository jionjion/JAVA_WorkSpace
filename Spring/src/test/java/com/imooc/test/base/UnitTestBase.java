package com.imooc.test.base;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.BeansException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

public class UnitTestBase {
	
	private ClassPathXmlApplicationContext context;
	
	private String springXmlpath;		//xml文件的位置
	
	public UnitTestBase() {}
	
	public UnitTestBase(String springXmlpath) {
		this.springXmlpath = springXmlpath;		//构造器传入xml文件的位置
	}
	
	@Before
	public void before() {
		if (StringUtils.isEmpty(springXmlpath)) {		//如果变量对象为空,加载全部配置文件
			springXmlpath = "classpath*:spring-*.xml";
		}
		try {							//如果不为空则字符串分隔后进行加载
			context = new ClassPathXmlApplicationContext(springXmlpath.split("[,\\s]+"));
			context.start();			//容器启动
		} catch (BeansException e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void after() {
		context.destroy();			//执行后销毁
	}
	
	/**
	 * @param beanId bean在配置时的名字
	 * @return 返回对象
	 */
	@SuppressWarnings("unchecked")
	protected <T extends Object> T getBean(String beanId) {
		try {
			return (T)context.getBean(beanId);
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param clazz 传入类的类类型
	 * @return	返回对象结果
	 */
	protected <T extends Object> T getBean(Class<T> clazz) {
		try {
			return context.getBean(clazz);
		} catch (BeansException e) {
			e.printStackTrace();
			return null;
		}
	}

}
