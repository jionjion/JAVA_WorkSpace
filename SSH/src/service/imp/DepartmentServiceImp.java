package service.imp;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import bean.Department;
import bean.PageBean;
import dao.DepartmentDao;
import service.DepartmentService;

@Transactional
public class DepartmentServiceImp implements DepartmentService {

	private DepartmentDao departmentDao;
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}
	
	@Override
	public PageBean<Department> findByPage(Integer currentPage) {
		
		PageBean<Department> pageBean = new PageBean<Department>();
		//封装每页显示记录数量
		int pageSize = 3;
		pageBean.setPageSize(pageSize);
		//封装总记录数
		int totalCount = departmentDao.findCount();
		pageBean.setTotalCount(totalCount);
		//封装总页数
		double tc = totalCount;
		Double num = Math.ceil(tc / pageSize);
		pageBean.setTotalPage(num.intValue());
		//封装当前页数
		pageBean.setCurrentPage(currentPage);
		//封装每页显示的数据
		int begin = (currentPage -1) * pageSize;	//MySQL开始查询的位置
		List<Department> list = departmentDao.findByPage(begin,pageSize);
		pageBean.setList(list);
		
		return pageBean;
	}

	@Override
	public void save(Department department) {

		departmentDao.save(department);
	}

	@Override
	public Department findById(Integer did) {
		return departmentDao.findById(did);
	}

	@Override
	public void update(Department department) {
		departmentDao.update(department);
	}

	@Override
	public void delete(Integer did) {
		departmentDao.delete(did);
	}

	@Override
	public List<Department> findAll() {
		return departmentDao.findAll();
	}
	
	
}
