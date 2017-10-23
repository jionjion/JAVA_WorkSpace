package lifecycle;
/**同时实现接口,初始化销毁方法
 * 自定义实现start初始化方法,stop结束方法
 * 优先级:首先使用接口的方法,随后使用自定义的销毁方法,非空时被覆盖*/
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class BeanLifeCycle implements InitializingBean, DisposableBean {
	
	/**自定义全局的初始化方法*/
	public void defautInit() {
		System.out.println("自定义Bean的全局初始化方法.");
	}
	
	/**自定义全局的销毁方法*/
	public void defaultDestroy() {
		System.out.println("自定义Bean的全局销毁方法.");
	}

	@Override
	public void destroy() throws Exception {
		System.out.println("接口实现Bean的销毁方法");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("接口实现Bean的初始化方法");
	}
	
	public void start() {
		System.out.println("自定义Bean开始方法.");
	}
	
	public void stop() {
		System.out.println("自定义Bean结束方法.");
	}
	
}
