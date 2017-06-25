package com.imooc.aop.schema.advice;
/**实现通知代理**/
public class FitImpl implements Fit {

	@Override
	public void filter() {
		System.out.println("接口实现通知代理.");
	}

}
