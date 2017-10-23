package aware;
/**
 * 	实现ApplicationContextAware接口,获取上下文对象
 * 	
 */
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


public class MoocApplicationContext implements ApplicationContextAware  {
	
	/**接口唯一的方法,获取上下文对象后获得Bean对象**/
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		System.out.println("获取上下文对象,MoocApplicationContext : " + applicationContext.getBean("moocApplicationContext").hashCode());
	}
	
}
