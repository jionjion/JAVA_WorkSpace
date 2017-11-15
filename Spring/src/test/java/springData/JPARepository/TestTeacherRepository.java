package springData.JPARepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import base.UnitTestBase;
import junit.framework.Assert;

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
	public void testQueryStudentList() {
		Object dataSource = super.getBean("dataSource");
		try {
			//是否为空
			Assert.assertNotNull(dataSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}