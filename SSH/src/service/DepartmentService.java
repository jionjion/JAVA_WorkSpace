package service;

import java.util.List;

import bean.Department;
import bean.PageBean;

public interface DepartmentService {

	public PageBean<Department> findByPage(Integer currentPage);

	public void save(Department department);

	public Department findById(Integer did);

	public void update(Department department);

	public void delete(Integer did);

	public List<Department> findAll();

}
