package springData.JDBCTemplate;


import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.util.Assert;

import base.UnitTestBase;
import springData.bean.Student;

/**
 *	使用SpringTemplate模板方法访问数据库
 *	Spring-Data框架
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class TestStudentTemplate extends UnitTestBase {
	
	public TestStudentTemplate() {
		super("classpath:spring-data.xml");
	}
	
	@Test
	public void testQueryStudentList() {
		DriverManagerDataSource dataSource = super.getBean("dataSource");
		JdbcTemplate jdbcTemplate = super.getBean("jdbcTemplate");
		try {
			//是否为空
			Assert.notNull(dataSource);
			Assert.notNull(jdbcTemplate);
			//执行查询
			
			StudentTemplate studentTemplate = super.getBean("studentTemplate");
			List<Student> students = studentTemplate.queryStudentList();
			System.out.println("查询学生群体:" + students.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSaveStudent() {
		try {
			//执行插入
			StudentTemplate studentTemplate = super.getBean("studentTemplate");
			Student students = new Student();
			students.setId(3);
			students.setName("Per");
			students.setAge(20);
			students.setAddress("杭州");
			students.setBirthday(new Date());
			studentTemplate.saveStudent(students);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}


/*	public static void main(String[] args) throws Exception{

InputStream inputStream = StudentTemplate.class.getClassLoader().getResourceAsStream("jdbc.properties");
Properties properties = new Properties();
properties.load(inputStream);

String username = properties.getProperty("jdbc.username");
String password = properties.getProperty("jdbc.password");
String url = properties.getProperty("jdbc.url");
String driverClass = properties.getProperty("jdbc.driverClass");

System.out.println(username + driverClass);
}*/