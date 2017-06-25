package action;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import bean.Department;
import bean.Employee;
import bean.PageBean;
import service.DepartmentService;
import service.EmployeeService;

/***
 *	员工的管理类
 */
public class EmployeeAction extends ActionSupport implements ModelDriven<Employee>{

	
	
	//模型驱动使用的对象
	private  Employee employee = new Employee(); 
	@Override
	public Employee getModel() {
		return employee;
	}
	
	private EmployeeService employeeService;
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}
	
	private DepartmentService departmentService;
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	/**登录执行的方法*/
	public String login() {
		
		Employee existEmployee = employeeService.login(employee);
		
		if (existEmployee == null) {
			//登录失败
			this.addActionError("用户名或者密码错误");
			return this.INPUT;
		}else{
			//登录成功
			ActionContext.getContext().getSession().put("existEmployee", existEmployee);
			return this.SUCCESS;
		}
	}
	
	//当前页数
	private Integer currentPage = 1;
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	
	/**根据当前页数判断总人数,将Bean放入值栈中,只需直接调用属性名即可*/
	public String findAll() {
		
		PageBean<Employee> pageBean = employeeService.findByPage(currentPage);
		//将其存入到值栈中
		ActionContext.getContext().getValueStack().push(pageBean);
		return "findAll";
	}
	
	/**跳转到新增加员工的列表**/
	public String savePage() {
		
		List<Department> list = departmentService.findAll();
		//放入值栈,集合用set,对象用put
		ActionContext.getContext().getValueStack().set("list", list);
		return "savePage";
	}
	
	/**添加新员工**/
	public String save() {
		//不使用级联保存,避免URL中对引用属性的修改
		employeeService.save(employee);
		return "save";
	}
	
	/**修改员工,并返回.默认模型驱动类直接在值栈中显示,在前台使用model.属性进行获取*/
	public String edit(){
		
		List<Department> list = departmentService.findAll();
		//放入值栈,集合用set,对象用put
		ActionContext.getContext().getValueStack().set("list", list);
		//使用模型驱动,保存查询要修改的对象
		employee = employeeService.findById(employee.getEid());
		return "edit";
	}
	
	/**修改员工**/
	public String update() {
		employeeService.update(employee);
		return "update";
	}
	
	/**删除员工*/
	public String delete() {
		employeeService.delete(employee.getEid());
		return "delete";
	}
}
