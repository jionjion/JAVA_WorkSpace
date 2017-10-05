package interceptor;

import com.opensymphony.xwork2.ActionSupport;

public class SpendTimeAction extends ActionSupport{

	private static final long serialVersionUID = 1L;

	@Override
	public String execute() throws Exception {
		for(int i=0;i<10000;i++){
			System.out.println("loading....");
		}
		return SUCCESS;
	}
	
}
