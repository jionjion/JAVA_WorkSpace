package com.jionjion.concurrency.example.publish;


/**
 * 	实例
 * 		使用单利模式,正确发布对象
 * 	饿汉模式,默认实现线程安全
 * 		1.私有化构造函数
 * 		2.私有静态对象,并实例化
 * 		3.提供对外实例化方法
 * 	注意静态代码块的书写顺序
 */
public class PublishExample4 {

	//私有构造函数
	private PublishExample4() {
		super();
	}
	
	//单利对象,当然也可以使用静态代码块实现
	private static volatile PublishExample4 instance = new PublishExample4();
	
	//返回方法
	public static synchronized PublishExample4 getInstance() {
		return instance;
	}
	
}
