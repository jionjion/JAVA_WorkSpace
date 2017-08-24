package com.model.strategy;
/**	红头鸭*/
public class RedheadDuck extends Duck {

	
	
	@Override
	public void quack() {
		super.quack();
	}

	@Override
	public void display() {
		System.out.println("我是红头的鸭子");
	}

}
