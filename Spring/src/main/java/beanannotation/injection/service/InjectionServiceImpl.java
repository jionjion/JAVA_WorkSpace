package beanannotation.injection.service;
/**
 * 	使用注解对Service层和需要的Dao层进行注解
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import beanannotation.injection.dao.InjectionDAO;

@Service	//Service层的专用注解
public class InjectionServiceImpl implements InjectionService {
	
	@Autowired	//可以注解在私有属性上
	private InjectionDAO injectionDAO;
	
//	@Autowired	//可以注解在构造器上
//	public InjectionServiceImpl(InjectionDAO injectionDAO) {
//		this.injectionDAO = injectionDAO;
//	}
	
//	@Autowired	//可以注解在set方法中
	public void setInjectionDAO(InjectionDAO injectionDAO) {
		this.injectionDAO = injectionDAO;
	}



	public void save(String arg) {
		//模拟业务操作
		System.out.println("Service接收参数：" + arg);
		arg = arg + ":" + this.hashCode();
		injectionDAO.save(arg);
	}
	
}
