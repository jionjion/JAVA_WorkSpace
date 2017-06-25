package com.imooc.aware;
/**通过BeanNameAware和ApplicationContextAware接口实现获取bean的名字和上下文对象*/
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class MoocBeanName implements BeanNameAware, ApplicationContextAware {

	private String beanName;	//需要私有名称
	
	@Override	//根据使用接口的Bean,获取Bean的名字
	public void setBeanName(String name) {
		this.beanName = name;
		System.out.println("获取Bean的名字 : " + name);
	}

	@Override	//根据设置的Bean的名字,有上下文对象获取Bean
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		System.out.println("获取Bean的上下文对象 : " + applicationContext.getBean(this.beanName).hashCode());
	}

}
