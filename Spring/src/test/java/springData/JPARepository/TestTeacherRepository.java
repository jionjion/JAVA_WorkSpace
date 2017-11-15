package springData.JPARepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import base.UnitTestBase;
import junit.framework.Assert;
import springData.bean.Teacher;

/**
 *	使用SpringTemplate模板方法访问数据库
 *	Spring-Data框架
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class TestTeacherRepository extends UnitTestBase {
	
	public TestTeacherRepository() {
		super("classpath:spring-data.xml");
	}
	
	@Test
	public void testFindByName() {
		try {
			Object object = super.getBean("dataSource");
			//获得测试
			TeacherRepository teacherRepository = super.getBean(TeacherRepository.class);
			//是否为空
			Teacher teacher = teacherRepository.findByName("JionJion");
			System.out.println(teacher);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}