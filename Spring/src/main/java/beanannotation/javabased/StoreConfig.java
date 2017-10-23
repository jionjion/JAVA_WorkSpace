package beanannotation.javabased;
/**	对使用注解的方式完成Bean的生成
 * */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration	//在类上注解,表示将其配置为Spring容器进行配置,包括对Bean的生成和属性文件的读取
@ImportResource("classpath:config.xml")	//在配置类中,引入对xml文件的读取,进而获取xml的引用的属性文件获取
public class StoreConfig {
	
	
	/**获取配置文件的值,使用${key}来获取文件中的值**/
	@Value("${url}")
	private String url;
	
	@Value("${jdbc.username}")	//注意名字为.风格,确保key的唯一性,而不会获取到系统的预设变量
	private String username;
	
	@Value("${password}")
	private String password;
	
	/**自定义的类,用来使用配置文件中的值,在构造器中有打印验证*/
	@Bean
	public MyDriverManager myDriverManager() {
		return new MyDriverManager(url, username, password);
	}
	
	/**使用@Bean,直接将方法返回的bean对象返回,完成IOC对象的获取.
	 * bean的名字为方法的名字**/
	@Bean
	public StringStore stringStore() {
		return new StringStore();
	}
	
	/**创建bean并指定名字,此时bean名称不与返回类一致
	 * 指定初始化方法和销毁方法均指定*/
//	@Bean(name = "stringStore", initMethod="init", destroyMethod="destroy")
//	public Store stringStore() {
//		return new StringStore();
//	}
	
	/**创建Bean并指定名字,
	 * 指定范围为请求IOC时创建新的对象,代理方式为类代理*/
//	@Bean(name = "stringStore")
//	@Scope(value="prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
//	public Store stringStore() {
//		return new StringStore();
//	}
	
	
	/**对Integer类型实现自动装配**/
	@Bean
	public IntegerStore integerStore() {
		return new IntegerStore();
	}
	
	
	/**容器,存入String类型**/
	@Autowired
	private Store<String> s1;
	
	/**容器,存入Integer类型**/
	@Autowired
	private Store<Integer> s2;
	
	/**对同一个接口的不同实现类的进行测试**/
	@Bean(name = "stringStoreTest")
	public Store stringStoreTest() {
		System.out.println("s1 的类名: " + s1.getClass().getName());
		System.out.println("s2 的类名: " + s2.getClass().getName());
		return new StringStore();
	}
	

}
