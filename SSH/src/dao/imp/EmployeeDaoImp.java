package dao.imp;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
/**持久化类，自动注入SessionFactory*/
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import bean.Department;
import bean.Employee;
import dao.EmployeeDao;

public class EmployeeDaoImp extends HibernateDaoSupport implements EmployeeDao {

	/**根据用户名和密码查询,并返回第一个结果*/
	@Override
	public Employee findByUser(Employee employee) {
		
		String hql = "from Employee where ename = ? and epassword = ? ";
		//传入参数和HQL进行查询
		List<Employee> employees = this.getHibernateTemplate().find(hql,employee.getEname(),employee.getEpassword());
		if(employees.size()>0){
			return employees.get(0);
		}
		return null; 
	}

	@Override
	public int findCount() {
		String hql = "select count(*) from Employee";
		List<Long> list = this.getHibernateTemplate().find(hql);
		//如果有数据
		if(list.size()>0){
			return list.get(0).intValue();
		}
		return 0;
	}

	@Override
	public List<Employee> findByPage(int begin, int pageSize) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class);
		//MySQL的分页查询语句
		List<Employee> list = this.getHibernateTemplate().findByCriteria(criteria, begin, pageSize);
		return list;
	}

	@Override
	public void save(Employee employee) {
		this.getHibernateTemplate().save(employee);
	}

	@Override
	public Employee findById(Integer eid) {
		return this.getHibernateTemplate().get(Employee.class, eid);
	}

	@Override
	public void update(Employee employee) {
		this.getHibernateTemplate().update(employee);
	}

	@Override
	public void delete(Employee employee) {
	
		this.getHibernateTemplate().delete(employee);
	}

}
