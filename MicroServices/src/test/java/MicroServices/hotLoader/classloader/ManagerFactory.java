package MicroServices.hotLoader.classloader;

import java.util.HashMap;
import java.util.Map;

/**
 * 	加载manager的工厂类
 * @author JionJion
 */
public class ManagerFactory {

	/** 记录热加载的加载信息 */
	private static final Map<String, LoadInfo> loadTimeInfo = new HashMap<String,LoadInfo>();
	
	/** 类所在的文件路径 */
	public static final String CLASSPATH = ManagerFactory.class.getResource("/").getPath();
	
	public static void main(String[] args) {
		System.out.println(CLASSPATH);
	}
}
