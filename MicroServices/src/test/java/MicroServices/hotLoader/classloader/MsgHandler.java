package MicroServices.hotLoader.classloader;

/** 后台启动线程,然后不断刷新加载实现了热加载的类 */
public class MsgHandler implements Runnable {

	@Override
	public void run() {
		while(true) {
			BaseManager  baseManager  = ManagerFactory.getBaseManager(ManagerFactory.MY_MANAGER);
			baseManager.logic();
			try {
				// 睡眠一秒
				Thread.sleep(1000);			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
