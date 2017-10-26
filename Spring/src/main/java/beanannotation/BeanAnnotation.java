package beanannotation;
/**基础的Bean的类型*/
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

//@Component("bean")	//通用注解,并指定注bean的名字
@Scope("prototype")					//作用范围,默认单例
@Component
public class BeanAnnotation {
	
	public void say(String arg) {
		System.out.println("使用注解 : " + arg);
	}
	
	/**通过注解,实现作用范围*/
	public void myHashCode() {
		System.out.println("使用注解 : " + this.hashCode());
	}
}
