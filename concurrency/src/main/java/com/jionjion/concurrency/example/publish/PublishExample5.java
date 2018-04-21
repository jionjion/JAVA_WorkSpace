package com.jionjion.concurrency.example.publish;


/**
 * 	实例
 * 		使用单利模式,正确发布对象
 * 	双重同步锁单例模式,线程不安全
 * 		1.私有化构造函数
 * 		2.私有静态对象
 * 		3.提供对外实例化方法
 * 		4.注意使用synchronized修饰单利对象和创建方法
 */
public class PublishExample5 {

	//私有构造函数
	private PublishExample5() {
		super();
	}
	
	//单利对象
	private static PublishExample5 instance = null;
	
	
	/*	JVM实例化对象过程
	 * 		1.分配对象的内存空间
	 * 		2.实例化对象
	 * 		3.设置instance指向分配的内存
	 * 
	 * 	当发生指令重排时:
	 * 		1 -> 3 -> 2
	 * 
	 * 	当线程A执行到中间第三步时,线程B调用方法,发现A已经为对象分配了内存,就直接返回对象;
	 * 	此时线程A尚未完成单例实例化,则程序调用抛出异常.
	 * 
	 */
	
	
	//返回方法
	public static PublishExample5 getInstance() {
		if(instance == null) {							//B
			//修饰调用对象,将调用对象时线程安全
			synchronized (PublishExample5.class) {
				if(instance == null) {
					instance = new PublishExample5();	//A-3
				}
			}
		}
		return instance;
	}
	
}
