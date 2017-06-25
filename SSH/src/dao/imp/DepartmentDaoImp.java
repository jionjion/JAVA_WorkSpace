package dao.imp;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
/**
 * 	Department的持久化类*/
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import bean.Department;
import dao.DepartmentDao;

public class DepartmentDaoImp extends HibernateDaoSupport implements DepartmentDao {

	/**分页查询*/
	@Override
	public List<Department> findByPage(int begin, int pageSize) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Department.class);
		//MySQL的分页查询语句
		List<Department> list = this.getHibernateTemplate().findByCriteria(criteria, begin, pageSize);
		return list;
	}

	/**统计所有的部门总数*/
	@Override
	public int findCount() {
		String hql = "select count(*) from Department";
		List<Long> list = this.getHibernateTemplate().find(hql);
		//如果有数据
		if(list.size()>0){
			return list.get(0).intValue();
		}
		return 0;
	}
	
	/**保存新部门*/
	@Override
	public void save(Department department) {
		this.getHibernateTemplate().save(department);
	}


	/**根据ID查询
	 * @return */
	@Override
	public Department findById(Integer did) {
		return this.getHibernateTemplate().get(Department.class, did);
	}

	@Override
	public void update(Department department) {
		this.getHibernateTemplate().update(department);
	}

	@Override
	public void delete(Integer did) {
		
		this.getHibernateTemplate().delete(
				this.getHibernateTemplate().get(Department.class, did));
	}

	@Override
	public List<Department> findAll() {
		String hql = "from Department";
		return this.getHibernateTemplate().find(hql);
	}
}
