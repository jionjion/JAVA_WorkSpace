package top.jionjion.demo.dao.jdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author Jion
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeJDBCTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    EmployeeJDBC employeeJDBC;


    @Test
    public void testGetConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testQueryOne(){
        Map<String, Object> map = employeeJDBC.queryOne();
        System.out.println(map);
    }
}