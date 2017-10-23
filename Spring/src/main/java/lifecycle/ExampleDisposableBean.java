package lifecycle;
/**通过接口实现销毁方法*/
import org.springframework.beans.factory.DisposableBean;

public class ExampleDisposableBean implements DisposableBean {

	@Override
	public void destroy() throws Exception {
		//do something
		System.out.println("通过接口实现销毁方法");
	}

}
