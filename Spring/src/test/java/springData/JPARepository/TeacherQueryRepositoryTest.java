package springData.JPARepository;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import base.UnitTestBase;
import springData.bean.Teacher;

/**
 *	使用SpringTemplate模板方法访问数据库
 *	Spring-Data框架
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class TeacherQueryRepositoryTest extends UnitTestBase {
	
	
	private TeacherQueryRepository teacherQueryRepository;
	
	public TeacherQueryRepositoryTest() {
		super("classpath:spring-data.xml");
	}

	/**
	 * 	在测试方法执行前,初始化
	 */
	@Before
	public void init() {
		//避免重复创建加载
		if (this.teacherQueryRepository == null) {
			this.teacherQueryRepository = super.getBean(TeacherQueryRepository.class);
		}
	}
	
	@Test
	public void testGetTeacherByMaxId() {
		try {
			Teacher teacher = teacherQueryRepository.getTeacherByMaxId();
			System.out.println(teacher);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetTeacherByParamName() {
		try {
			Teacher teacher = teacherQueryRepository.getTeacherByParamName("JionJion");
			System.out.println(teacher);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	@Test
	public void testGetTeacherByParamAddress() {
		try {
			Teacher teacher = teacherQueryRepository.getTeacherByParamAddress("鹤壁");
			System.out.println(teacher);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	@Test
	public void testGetCount() {
		try {
			Long count = teacherQueryRepository.getCount();
			System.out.println("总记录数:"+count);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}		
	
	@Test
	public void testUpdateSetName() {
		try {
			teacherQueryRepository.updateSetName(2, 18);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}		
}