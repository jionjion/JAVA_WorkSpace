package bean;
/**Bean的作用域的测试*/
public class BeanScope {
	
	public void say() {
		System.out.println("这个Bean的哈希值 : " + this.hashCode());
	}
	
}
