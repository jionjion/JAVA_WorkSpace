package beanannotation.javabased;
/***继承自自定义的接口类,实现对接口类针对String的方法*/
public class StringStore implements Store<String> {
	
	public void init() {
		System.out.println("自定义初始化方法.");
	}
	
	public void destroy() {
		System.out.println("自定义销毁方法.");
	}
	
}
