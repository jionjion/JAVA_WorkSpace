package dao;

import java.util.List;

import bean.Department;

public interface DepartmentDao {

	public List<Department> findByPage(int begin, int pageSize);

	public int findCount();

	public void save(Department department);

	public Department findById(Integer did);

	public void update(Department department);

	public void delete(Integer did);

	public List<Department> findAll();

}
