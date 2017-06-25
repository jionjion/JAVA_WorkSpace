package com.imooc.aop.api;
/**接口的实现类,模拟早期的配置文件*/
public class BizLogicImpl implements BizLogic {
	
	public String save() {
		System.out.println("正在进行保存.....");
		return "返回处理结果为.....";
//		throw new RuntimeException();
	}

}
