package beanannotation.jsr;
/**对Service层的生命周期注解和初始化和销毁注解进行验证**/
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.stereotype.Service;

@Service
//@Named
public class JsrServie {
	
//	@Inject		//autoWire相等
	@Resource	//私有属性注入实例,指定其生命周期,也可以使用autoWire
	private JsrDAO jsrDAO;
	
//	@Inject		//autoWire相等
//	@Resource	//set方法注入实例,指定其生命周期,也可以使用autoWire
	//@name注解指定类名
	public void setJsrDAO(@Named("jsrDAO") JsrDAO jsrDAO) {
		this.jsrDAO = jsrDAO;	
	}
	
	@PostConstruct	
	public void init() {
		System.out.println("初始化注解.");
	}
	
	@PreDestroy
	public void destroy() {
		System.out.println("销毁注解");
	}

	/**调用保存的方法**/
	public void save() {
		jsrDAO.save();
	}
	
}
