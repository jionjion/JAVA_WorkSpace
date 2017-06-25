package com.imooc.aop.schema.advice.biz;
/**模拟业务类,在运行时触发通知**/
public class AspectBiz {
	
	public void biz() {
		System.out.println("业务正在进行.");
//		throw new RuntimeException();		//抛出异常,调用异常通知.而后置通知不再通知
	}
	
	public void init(String bizName, int times) {
		System.out.println("业务中传入参数: " + bizName + "   " + times);
	}

}
