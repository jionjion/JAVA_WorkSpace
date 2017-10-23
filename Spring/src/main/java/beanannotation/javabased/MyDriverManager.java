package beanannotation.javabased;
/**自定义的类,其属性通过xml从配置文件中获取*/
public class MyDriverManager {
	
	public MyDriverManager(String url, String userName, String password) {
		//验证方法
		System.out.println("读取配置文件中url : " + url);
		System.out.println("读取配置文件中userName: " + userName);
		System.out.println("读取配置文件中password: " + password);
	}

}
