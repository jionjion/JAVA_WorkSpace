package top.jionjion.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import top.jionjion.demo.entities.Department;
import top.jionjion.demo.entities.Employee;

import java.util.*;

/**
 * @author Jion
 * 员工
 */
@Repository
public class EmployeeDao {
    private static Map<Integer, Employee> employees = null;

    @Autowired
    private DepartmentDao departmentDao;

    static {
        employees = new HashMap<>();
        employees.put(1001, new Employee(1001, "张三", "1434@qq.com", "男",
                new Department(10, "开发"), new Date()));
        employees.put(1002, new Employee(1002, "李四", "2354@qq.com", "女",
                new Department(10, "开发"), new Date()));
        employees.put(1003, new Employee(1003, "王五", "6585@qq.com", "男",
                new Department(10, "开发"), new Date()));
        employees.put(1004, new Employee(1004, "赵六", "4893@qq.com", "女",
                new Department(10, "开发"), new Date()));
    }

    private static Integer initId = 1005;

    /** 保存一个 */
    public void save(Employee employee){
        if (employee.getId() == null){
            employee.setId(initId ++);
        }
        Department department = departmentDao.getDepartment(employee.getDepartment().getId());
        employee.setDepartment(department);
        employees.put(employee.getId(), employee);
    }

    /** 查询全部 */
    public Collection<Employee> getAll(){
        return employees.values();
    }

    /** 查询一个 */
    public Employee get(Integer id){
        return employees.get(id);
    }

    /** 删除一个 */
    public void delete(Integer id){
        employees.remove(id);
    }

}
