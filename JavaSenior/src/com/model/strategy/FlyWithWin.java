package com.model.strategy;

/**	飞行的一个扩展类*/
public class FlyWithWin implements FlyStrategy {

	@Override
	/**使用翅膀进行飞行*/
	public void performFly() {
		System.out.println("使用翅膀进行飞行");
	}

}
