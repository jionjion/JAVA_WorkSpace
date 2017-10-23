package resource;
/**统一资源文件请求,实现对文件的自动注入
 * 所有的容器都实现了resource的接口**/
import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;


public class MoocResource implements ApplicationContextAware  {
	//1.私有属性
	private ApplicationContext applicationContext;
	//2.通过实现接口获得上下文容器
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	/**通过上下文对象获得资源文件对象,它是接口,实现类有很多,方法也很多*/
	public void resource() throws IOException {
		//1.通过classpath:config.txt方式获取
//		Resource resource = applicationContext.getResource("classpath:config.txt");
		//2.通过file:F:\\JAVA_WorkSpace\\Spring\\src\\main\\resources\\config.txt方式获取
//		Resource resource = applicationContext.getResource("file:F:\\JAVA_WorkSpace\\Spring\\src\\main\\resources\\config.txt");
		//3.空缺,默认使用ApplicationContext的构造方式
		Resource resource = applicationContext.getResource("config.txt");
		//4.通过url访问
//		Resource resource = applicationContext.getResource("url:https://baidu.com/");
		
		System.out.println(resource.getFilename());			//文件名
		System.out.println(resource.contentLength());		//文件长度
	}

}
