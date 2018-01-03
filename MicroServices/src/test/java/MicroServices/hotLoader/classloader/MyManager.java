package MicroServices.hotLoader.classloader;
/**
 * 	当发生改变时,完成热加载
 * @author JionJion
 */
public class MyManager implements BaseManager {

	@Override
	public void logic() {
		System.out.println("java类的热加载....");
	}
}
