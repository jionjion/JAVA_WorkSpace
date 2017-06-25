package dao;

import java.util.List;

import bean.Employee;

/**员工持久化类**/
public interface EmployeeDao {


	public Employee findByUser(Employee employee);

	public int findCount();

	public List<Employee> findByPage(int begin, int pageSize);

	public void save(Employee employee);

	public Employee findById(Integer eid);

	public void update(Employee employee);

	public void delete(Employee employee);

}
