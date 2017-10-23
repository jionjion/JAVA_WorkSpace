package lifecycle;
/**通过接口实现初始化方法*/
import org.springframework.beans.factory.InitializingBean;

public class ExampleInitializingBean implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		//do something
		System.out.println("通过接口实现初始化方法");
	}

}
