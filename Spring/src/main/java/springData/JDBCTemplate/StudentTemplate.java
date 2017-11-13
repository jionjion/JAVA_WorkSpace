package springData.JDBCTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

import springData.bean.Student;

/**
 *	使用SpringTemplate模板方法访问数据库
 *	Spring-Data框架
 */
public class StudentTemplate {

	private JdbcTemplate jdbcTemplate;

	
	public List<Student> queryStudentList() {
		//匿名内部变量
		final List<Student> students = new ArrayList<Student>();
		String sql = "select id , name , age , address , birthday from student";
		//执行查询,并对返回结果行进行匿名函数处理
		jdbcTemplate.query(sql,new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet resultSet) throws SQLException {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				int age = resultSet.getInt("age");
				String address = resultSet.getString("address");
				Date birthday = resultSet.getDate("birthday");
				
				Student student = new Student();
				student.setId(id);
				student.setName(name);
				student.setAge(age);
				student.setAddress(address);
				student.setBirthday(birthday);
				students.add(student);
			}
			
		});
		return students;
	}
	
	public void saveStudent(Student student) {
		String sql = "insert into student(id,name,age,address,birthday) values (? , ? , ? , ? , ?)";
		jdbcTemplate.update(sql, new Object[]{student.getId(),student.getName(),student.getAge(),student.getAddress(),student.getBirthday()});
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
