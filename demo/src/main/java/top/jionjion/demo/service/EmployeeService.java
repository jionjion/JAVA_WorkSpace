package top.jionjion.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jionjion.demo.dao.EmployeeDao;
import top.jionjion.demo.entities.Employee;

import java.util.Collection;

/**
 * @author Jion
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    /** 保存一个 */
    public void save(Employee employee){
        employeeDao.save(employee);
    }

    /** 查询全部 */
    public Collection<Employee> getAll(){
        return employeeDao.getAll();
    }

    /** 查询一个 */
    public Employee get(Integer id){
        return employeeDao.get(id);
    }

    /** 删除一个 */
    public void delete(Integer id){
        employeeDao.delete(id);
    }

}
