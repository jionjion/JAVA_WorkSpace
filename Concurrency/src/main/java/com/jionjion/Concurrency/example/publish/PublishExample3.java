package com.jionjion.concurrency.example.publish;


/**
 * 	实例
 * 		使用单例模式,正确发布对象
 * 	懒汉模式
 * 		1.私有化构造函数
 * 		2.私有静态对象
 * 		3.提供对外实例化方法
 * 		4.注意使用synchronized修饰单例对象和创建方法,程序开销较大
 */
public class PublishExample3 {

	//私有构造函数
	private PublishExample3() {
		super();
	}
	
	//单例对象
	private static PublishExample3 instance = null;
	
	//返回方法
	public static synchronized PublishExample3 getInstance() {
		if(instance == null) {
			instance = new PublishExample3();
		}
		return instance;
	}
	
}
