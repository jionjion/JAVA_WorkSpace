package MicroServices.hotLoader.classloader;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 	加载manager接口的工厂类
 * @author JionJion
 */
public class ManagerFactory {

	/** 记录热加载的加载信息 */
	private static final Map<String, LoadInfo> loadTimeMap = new HashMap<String,LoadInfo>();
	
	/** 类所在的文件路径 */
	public static final String CLASSPATH = ManagerFactory.class.getResource("/").getPath();
	
	/** 准备热加载的类全路径*/
	public static final String MY_MANAGER = "MicroServices.hotLoader.classloader.MyManager";
	
	/** 获得接口类 */
	public static BaseManager getBaseManager(String className) {
		File loadFile = new File(CLASSPATH + className.replace(".", "/") + ".class");
		long lastModified = loadFile.lastModified();
		//map中不包含以类名为键的类信息,则从新加载
		if (loadTimeMap.get(className) == null) {
			load(className,lastModified);
		//时间戳发生变化,需要重新加载
		} else if(loadTimeMap.get(className).getLoadTime() != lastModified) {
			load(className,lastModified);
		}
		return loadTimeMap.get(className).getManager();
	}
	
	
	/** 加载类的方法 */
	private static void load(String className, long lastModified) {
		MyClassLoader classLoader = new MyClassLoader(CLASSPATH);
		Class<?> loadClass = null;
		try {
			loadClass = classLoader.findClass(MY_MANAGER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		BaseManager baseManager = newInstance(loadClass);
		LoadInfo loadInfo = new LoadInfo(classLoader, lastModified);
		loadInfo.setManager(baseManager);
		loadTimeMap.put(className, loadInfo);
	}

	/** 基于反射的方式创建类 */
	private static BaseManager newInstance(Class<?> loadClass) {
		try {
			return (BaseManager) loadClass.getConstructor(new Class[] {}).newInstance(new Object[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	public static void main(String[] args) {
		System.out.println(CLASSPATH + MY_MANAGER.replace(".", "/") + ".class");
	}
}
