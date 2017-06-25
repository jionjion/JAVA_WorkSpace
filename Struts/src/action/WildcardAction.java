package action;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 	通过使用Action的不同返回值,进行方法的执行和页面的跳转
 */
public class WildcardAction extends ActionSupport {

	
	
	@Override
	public String execute() throws Exception {
		return super.execute();
	}

	/**新增的方法 */
	public String add() {
		System.out.println("执行新增方法");
		return "add";
	}
	
	/**修改的方法*/
	public String update() {
		System.out.println("执行修改方法");
		return "update";
	}
}
