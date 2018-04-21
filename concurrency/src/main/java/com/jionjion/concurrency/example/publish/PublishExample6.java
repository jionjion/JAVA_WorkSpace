package com.jionjion.concurrency.example.publish;


/**
 * 	实例
 * 		使用单利模式,正确发布对象
 * 	双重同步锁限制指令重排单例模式,线程安全
 * 		1.私有化构造函数
 * 		2.私有静态对象
 * 		3.提供对外实例化方法
 * 		4.注意使用synchronized修饰单利对象和创建方法
 */
public class PublishExample6 {

	//私有构造函数
	private PublishExample6() {
		super();
	}
	
	//单利对象 , volatile + 双重检测机制 -> 限制指令重排
	private volatile static PublishExample6 instance = null;
	
	
	/*	JVM实例化对象过程
	 * 		1.分配对象的内存空间
	 * 		2.实例化对象
	 * 		3.设置instance指向分配的内存
	 */
	
	
	//返回方法
	public static PublishExample6 getInstance() {
		if(instance == null) {			
			//修饰调用对象,将调用对象时线程安全
			synchronized (PublishExample6.class) {
				if(instance == null) {
					instance = new PublishExample6();
				}
			}
		}
		return instance;
	}
	
}
