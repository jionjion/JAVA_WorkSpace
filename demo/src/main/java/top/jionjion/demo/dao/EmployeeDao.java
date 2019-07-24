package top.jionjion.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import top.jionjion.demo.entities.Department;
import top.jionjion.demo.entities.Employee;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jion
 * 员工
 */
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
    }

    /** 删除一个 */
}
