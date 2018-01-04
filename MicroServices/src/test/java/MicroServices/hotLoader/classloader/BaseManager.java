package MicroServices.hotLoader.classloader;

/**
 * 	接口,子类实现
 * 	当子类动态更新时,实现动态加载
 * @author JionJion
 */
public interface BaseManager {

	/** 自定义的方法,被修改后重新加载类 */
	void logic();
}
