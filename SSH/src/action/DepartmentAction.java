package action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import bean.Department;
import bean.PageBean;
import service.DepartmentService;

public class DepartmentAction extends ActionSupport implements ModelDriven<Department>{

	//模型驱动
	private Department department = new Department();
	@Override
	public Department getModel() {
		return department;
	}

	//依赖注入
	private DepartmentService departmentService;
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	//当前页数
	private Integer currentPage = 1;
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}


	/**根据当前页数判断总人数,将Bean放入值栈中,只需直接调用属性名即可*/
	public String findAll() {
		
		PageBean<Department> pageBean = departmentService.findByPage(currentPage);
		//将其存入到值栈中
		ActionContext.getContext().getValueStack().push(pageBean);
		return "findAll";
	}
	
	/**添加新部门**/
	public String savePage() {
		
		return "savePage";
	}
	
	/**添加新部门**/
	public String save() {
		departmentService.save(department);
		return "save";
	}
	
	/**修改员工,并返回.默认模型驱动类直接在值栈中显示,在前台使用model.属性进行获取*/
	public String edit(){
		
		department = departmentService.findById(department.getDid());
		return "edit";
	}
	
	/**修改员工**/
	public String update() {
		departmentService.update(department);
		return "update";
	}
	
	/**删除员工*/
	public String delete() {
		departmentService.delete(department.getDid());
		return "delete";
	}
}
