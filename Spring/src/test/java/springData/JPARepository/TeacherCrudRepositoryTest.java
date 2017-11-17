package springData.JPARepository;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import base.UnitTestBase;
import springData.bean.Teacher;

public class TeacherCrudRepositoryTest extends UnitTestBase{

	private TeacherCrudRepository teacherCrudRepository;
	
	public TeacherCrudRepositoryTest() {
		super("classpath:spring-data.xml");
	}	
	
	/**
	 * 	在测试方法执行前,初始化
	 */
	@Before
	public void init() {
		//避免重复创建加载
		if (this.teacherCrudRepository == null) {
			this.teacherCrudRepository = super.getBean(TeacherCrudRepository.class);
		}
	}
	
	@Test
	public void testCount() {
		Long count = teacherCrudRepository.count();
		System.out.println("总数:"+count);
	}

	@Test
	public void testDeleteAll() {
		fail("Not yet implemented");
	}

	@Test
	@Transactional
	public void testSaveS() {
		Teacher teacher = new Teacher();
		teacher.setId(5);
		teacher.setName("Per");
		teacher.setAge(20);
		teacher.setAddress("开封");
		teacher.setWorkday(new Date());
		teacherCrudRepository.save(teacher);
	}

	@Test
	public void testSaveIterableOfS() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindOneInteger() {
		fail("Not yet implemented");
	}

	@Test
	public void testExistsInteger() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllIterableOfInteger() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteInteger() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteTeacher() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteIterableOfQextendsTeacher() {
		fail("Not yet implemented");
	}

}
