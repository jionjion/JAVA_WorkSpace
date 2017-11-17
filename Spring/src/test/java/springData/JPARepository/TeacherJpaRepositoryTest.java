package springData.JPARepository;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import base.UnitTestBase;
import springData.bean.Teacher;

public class TeacherJpaRepositoryTest extends UnitTestBase{

	private TeacherJpaRepository teacherJpaRepository;
	
	public TeacherJpaRepositoryTest() {
		super("classpath:spring-data.xml");
	}	
	
	/**
	 * 	在测试方法执行前,初始化
	 */
	@Before
	public void init() {
		//避免重复创建加载
		if (this.teacherJpaRepository == null) {
			this.teacherJpaRepository = super.getBean(TeacherJpaRepository.class);
		}
	}	
	
	@Test
	public void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllSort() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllIterableOfID() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveIterableOfS() {
		fail("Not yet implemented");
	}

	@Test
	public void testFlush() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveAndFlush() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteInBatch() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteAllInBatch() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetOne() {
	//	Teacher teacher = teacherJpaRepository.getOne(1);	//懒加载,测试时异常
		Teacher teacher = teacherJpaRepository.findOne(1);
		System.out.println(teacher);
	}

}
