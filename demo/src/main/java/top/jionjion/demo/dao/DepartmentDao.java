package top.jionjion.demo.dao;

import top.jionjion.demo.entities.Department;

import javax.xml.crypto.Data;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jion
 *  部门
 */
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
