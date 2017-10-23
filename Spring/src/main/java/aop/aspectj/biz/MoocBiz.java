package aop.aspectj.biz;
/**通过注解方式,被切面类横切**/
import org.springframework.stereotype.Service;

import aop.aspectj.MoocMethod;


@Service	//作为服务层,被切面横切
public class MoocBiz {
	
	@MoocMethod("MoocBiz save with MoocMethod.")	//指定传入参数的值
	public String save(String arg) {
		System.out.println("业务保存参数 : " + arg);
//		throw new RuntimeException(" Save failed!");
		return " 保存成功!";
	}

}
