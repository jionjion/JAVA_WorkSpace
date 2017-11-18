package springData.JPARepository;

import java.util.Date;
import java.util.Iterator;
import org.junit.Before;
import org.junit.Test;

import base.UnitTestBase;
import springData.bean.Teacher;

public class TeacherCrudRepositoryTest extends UnitTestBase{

	private TeacherCrudRepository teacherCrudRepository;
	
	public TeacherCrudRepositoryTest() {
		super("classpath:spring-data.xml");
	}	
	
	/**
	 * 	在测试方法执行前,初始化
	 * 	避免重复创建加载
	 */
	@Before
	public void init() {
		if (this.teacherCrudRepository == null) {
			this.teacherCrudRepository = super.getBean(TeacherCrudRepository.class);
		}
	}
	
	/**
	 * 	查询当前总记录数
	 */
	@Test
	public void testCount() {
		Long count = teacherCrudRepository.count();
		System.out.println("总记录数:" + count);
	}

	/**
	 * 	删除当前数据表
	 */
	@Test
	public void testDeleteAll() {
		teacherCrudRepository.deleteAll();
	}

	/**
	 * 	保存实体
	 */
	@Test
	public void testSaveS() {
		Teacher teacher = new Teacher();
		teacher.setId(7);
		teacher.setName("Per");
		teacher.setAge(20);
		teacher.setAddress("开封");
		teacher.setWorkday(new Date());
		teacherCrudRepository.save(teacher);
	}

	/**
	 * 	通过主键查询
	 */
	@Test
	public void testFindOneInteger() {
		Teacher teacher = teacherCrudRepository.findOne(1);
		System.out.println("查询第一个:" + teacher);
	}

	/**
	 * 	通过主键判断
	 */
	@Test
	public void testExistsInteger() {
		Boolean exists = teacherCrudRepository.exists(1);
		System.out.println("第一个是否存在:" + exists);
	}

	/**
	 * 	查询全部方法
	 * 	输出一个实现了Iterable<T>接口的实体对象,这里直接调用其迭代器进行迭代输出
	 */
	@Test
	public void testFindAll() {
		Iterator<Teacher> teachers = teacherCrudRepository.findAll().iterator();
		while(teachers.hasNext()) {
			System.out.println("迭代下一个:" + teachers.next());
		}
	}


	/**
	 * 	根据主键删除
	 */
	@Test
	public void testDeleteInteger() {
		teacherCrudRepository.delete(7);
	}

	/**
	 *	通过实体删除
	 */
	@Test
	public void testDeleteTeacher() {
		Teacher teacher = teacherCrudRepository.findOne(8);
		teacherCrudRepository.delete(teacher);
	}

	/**
	 * 	通过迭代器删除全部
	 */
	@Test
	public void testDeleteIterableOfQextendsTeacher() {
		Iterable<Teacher> teachers = teacherCrudRepository.findAll(); 
		teacherCrudRepository.delete(teachers);
	}

}
