package com.imooc.ioc.injection.service;
/**Service层实现*/
import com.imooc.ioc.injection.dao.InjectionDAO;


public class InjectionServiceImpl implements InjectionService {
	
	//必须,首先私有Dao层
	private InjectionDAO injectionDAO;
	
	/**构造器注入,如果用设置注入,则不用构造器,注意构造器内的传入参数的名字需要和xml中的bean的Id一致*/
	public InjectionServiceImpl(InjectionDAO injectionDAO) {
		this.injectionDAO = injectionDAO;
	}
	
	/**设值注入,只需要get()方法即可,如果用构造器,则不需要set()*/
//	public void setInjectionDAO(InjectionDAO injectionDAO) {
//		this.injectionDAO = injectionDAO;
//	}

	public void save(String arg) {
		//模拟业务操作
		System.out.println("Service接收参数：" + arg);
		arg = arg + ":" + this.hashCode();
		injectionDAO.save(arg);
	}
	
}
