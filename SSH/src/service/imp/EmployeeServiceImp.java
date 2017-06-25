package service.imp;

import java.util.List;

import bean.Department;
import bean.Employee;
import bean.PageBean;
import dao.EmployeeDao;
import service.EmployeeService;

public class EmployeeServiceImp implements EmployeeService {

	private EmployeeDao employeeDao;
	public void setEmployeeDao(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	@Override
	public Employee login(Employee employee) {

		return employeeDao.findByUser(employee);
	}
	@Override
	public PageBean<Employee> findByPage(Integer currentPage) {
		
		PageBean<Employee> pageBean = new PageBean<Employee>();
		//封装每页显示记录数量
		int pageSize = 3;
		pageBean.setPageSize(pageSize);
		//封装总记录数
		int totalCount = employeeDao.findCount();
		pageBean.setTotalCount(totalCount);
		//封装总页数
		double tc = totalCount;
		Double num = Math.ceil(tc / pageSize);
		pageBean.setTotalPage(num.intValue());
		//封装当前页数
		pageBean.setCurrentPage(currentPage);
		//封装每页显示的数据
		int begin = (currentPage -1) * pageSize;	//MySQL开始查询的位置
		List<Employee> list = employeeDao.findByPage(begin,pageSize);
		pageBean.setList(list);
		
		return pageBean;
	}
	@Override
	public void save(Employee employee) {
		employeeDao.save(employee);
	}
	@Override
	public Employee findById(Integer eid) {
		return employeeDao.findById(eid);
	}
	@Override
	public void update(Employee employee) {
		employeeDao.update(employee);
	}
	@Override
	public void delete(Integer eid) {
		Employee employee = employeeDao.findById(eid);
		employeeDao.delete(employee);
	}
	
}
