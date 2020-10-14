package iterator;

import java.util.Iterator;

/**
 *  学院类, 维护内部的学系
 *
 * @author Jion
 */
public interface College {

    /** 获得学院描述 */
    String getName();

    /** 增加学系 */
    void addDepartment(String name, String description);

    /** 返回迭代器,遍历 */
    Iterator<Department> iterator();
}
