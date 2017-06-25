package springData.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import springData.bean.Student;


/**
 * 使用传统的方式访问数据库
 * */
public class TraditionalJDBC {

	
	/**获取JDBC连接*/
	public Connection getConnection() throws Exception {
		
		//第一步.创建常量
		//方式一:自定义常量
		String usr = "root";
		String pwd = "123456";
		String url = "jdbc:mysql:///springmvc";
		String driver = "com.mysql.jdbc.Driver";
		//方式二:从属性文件中获取常量
		InputStream inputStream = TraditionalJDBC.class.getClassLoader().getResourceAsStream("db.properties");
		Properties properties = new Properties();
		properties.load(inputStream);
		driver = properties.getProperty("jdbc.Driver");
		usr = properties.getProperty("jdbc.user");
		pwd = properties.getProperty("jdbc.password");
		url = properties.getProperty("jdbc.url");
		
		//第二步:动态加载类
		Class.forName(driver);
		//第三步:创建连接
		Connection connection = DriverManager.getConnection(url,usr,pwd);
		return connection;
	}
	
	/**释放连接*/
	public void release(ResultSet resultSet, Statement statement, Connection connection) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("结果集关闭出现异常");
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("执行SQL关闭出现异常");
			}
		}
		if (connection !=null) {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("连接关闭发生异常");
			}
		}
	}
	
	/**查询全部的学生对象*/
	public List<Student> queryStudentsList() {
		//连接对象
		Connection connection = null;
		//执行对象
		Statement statement = null;
		//结果集对象
		ResultSet resultSet = null;
		//SQL语句
		String sql = "select s.id,s.name,s.age from student s";
		//封装返回集合
		List<Student> list = new ArrayList<Student>();
		try {
			connection = getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);		//执行查询语句
			while(resultSet.next()){
				Integer id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				Integer age = resultSet.getInt("age");
				Student student = new Student(id, name, age);
				list.add(student);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("查询学生集合时出现问题");
		} finally {
			release(resultSet, statement, connection);
		}
		
		return list;
	}
	
	/**保存学生对象*/
	public void saveStudent(Student student) {
		//连接对象
		Connection connection = null;
		//预编译执行对象
		PreparedStatement preparedStatement = null;
		//结果集对象
		ResultSet resultSet = null;
		//SQL语句
		String sql = "insert into student(id,name,age) values(? ,? ,?)";
		//封装返回集合
		List<Student> list = new ArrayList<Student>();
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(sql);	//装载预编译执行语句,创建执行对象
			preparedStatement.setInt(1,student.getId());
			preparedStatement.setString(2,student.getName());
			preparedStatement.setInt(3, student.getAge());
			preparedStatement.execute();		//执行保存语句
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("保存学生时出现问题");
		} finally {
			release(resultSet, preparedStatement, connection);
		}
	}
	
	@Test
	/**测试数据库连接是否正常*/
	public void test() throws Exception{
		Connection connection = getConnection();
		//断言,类是否合适
		Assert.assertNotNull(connection);
		
	}
	
	@Test
	/**测试查询学生集合是否正常*/
	public void test2() throws Exception{
		List<Student> list =  queryStudentsList();
		System.out.println(list.toString());
	}
	
	@Test
	/**测试保存学生时是否正常*/
	public void	test3() throws Exception{
		Student student = new Student(4, "赵六", 23);
		saveStudent(student);
	}
}
