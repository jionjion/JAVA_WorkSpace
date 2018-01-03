package MicroServices.hotLoader.classloader;
/**
 * 	封装加载类的信息
 * @author JionJion
 */
public class LoadInfo {

	/**自定义加载类*/
	private MyClassLoader classLoader;
	
	/**类加载时间戳,后台检测时间是否变化,当变化时重新加载,用于记录类加载的时间*/
	private long loadTime;
	
	/**检测类实现是否发生变化,变化后重新加载类*/
	private BaseManager manager;

	public LoadInfo(MyClassLoader classLoader, long loadTime) {
		super();
		this.classLoader = classLoader;
		this.loadTime = loadTime;
	}

	public MyClassLoader getClassLoader() {
		return classLoader;
	}

	public void setClassLoader(MyClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public long getLoadTime() {
		return loadTime;
	}

	public void setLoadTime(long loadTime) {
		this.loadTime = loadTime;
	}

	public BaseManager getManager() {
		return manager;
	}

	public void setManager(BaseManager manager) {
		this.manager = manager;
	}
	
	
	
}
