package top.jionjion.demo.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 *  @author Jion
 *      JDBC 连接数据库
 *      参考备注 org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
 */
@Repository
public class EmployeeJDBC {

    @Autowired
    JdbcTemplate template;

    public Map<String, Object> queryOne(){
        List<Map<String, Object>> maps = template.queryForList("select * from employee");
        return maps.get(0);
    }
}
