package MicroServices.hotLoader.classloader;
/**
 * 	测试类加载方法
 * @author JionJion
 */
public class ClassLoaderTest {

	public static void main(String[] args) {
		new Thread(new MsgHandler()).start();
	}
}
