package top.jionjion.demo.dao;

import org.springframework.stereotype.Repository;
import top.jionjion.demo.entities.Department;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jion
 *  部门
 */
@Repository
public class DepartmentDao {

    private static Map<Integer, Department> departments = null;

    static {
        departments = new HashMap<>();
        departments.put(10,new Department(10,"研发"));
        departments.put(20,new Department(20,"销售"));
    }

    /** 全部 */
    public Collection<Department> getDepartments(){
        return departments.values();
    }

    /** 一个 */
    public Department getDepartment(Integer id){
        return departments.get(id);
    }
}
