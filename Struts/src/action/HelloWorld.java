package action;

import com.opensymphony.xwork2.ActionSupport;

public class HelloWorld extends ActionSupport {

	/**默认的方法,默认必须执行*/
	@Override
	public String execute() throws Exception {
		
		System.out.println("执行操作");
		return SUCCESS;		//返回成功的界面
	}
	
	
	/**新增的方法*/
	public String add() {
		System.out.println("执行新增方法");
		return SUCCESS;
	}
	
	/**修改的方法*/
	public String update() {
		System.out.println("执行修改方法");
		return SUCCESS;
	}
}
