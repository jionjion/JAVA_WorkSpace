package com.model.strategy;
/**
 *	鸭子模型
 */
public abstract class Duck {

	
	/**
	 * 	模仿鸭子的发出叫声
	 * 	通用行为,由超类实现
	 */
	public void quack() {
		System.out.println("模仿鸭子的叫声.....嘎嘎");
	}
	
	
	/**
	 * 显示不同的鸭子外观,由子类实现
	 */
	public abstract void display();
	
	
	/**
	 *	使用组合方式,私有引用接口,调用接口的子类而获得接口的方法 	
	 */
	public void fly() {
		flyStrategy.performFly();
	}
	
	private FlyStrategy flyStrategy;

	/**将实现接口的子类注入*/
	public void setFly(FlyStrategy flyStrategy) {
		this.flyStrategy = flyStrategy;
	}
	
	
	
}
