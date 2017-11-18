package springData.JPARepository;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import base.UnitTestBase;
import springData.bean.Teacher;

public class TeacherJpaRepositoryTest extends UnitTestBase{

	private TeacherJpaRepository teacherJpaRepository;
	
	public TeacherJpaRepositoryTest() {
		super("classpath:spring-data.xml");
	}	
	
	/**
	 * 	在测试方法执行前,初始化
	 * 	避免重复加载
	 */
	@Before
	public void init() {
		if (this.teacherJpaRepository == null) {
			this.teacherJpaRepository = super.getBean(TeacherJpaRepository.class);
		}
	}	
	
	/**
	 * 	查询全部
	 */
	@Test
	public void testFindAll() {
		List<Teacher> teachers = teacherJpaRepository.findAll();
		System.out.println("查询全部:" + teachers.toString());
	}

	/**
	 * 	查询全部
	 * 	排序规则:根据ID降序
	 */
	@Test
	public void testFindAllSort() {
		Order orders = new Order(Sort.Direction.DESC, "id");
		Sort sort = new Sort(orders);
		List<Teacher> teachers = teacherJpaRepository.findAll(sort);		//传入排序对象
		System.out.println("主键降序查询全部" + teachers);			
	}


	@Test
	public void testFlush() {
		teacherJpaRepository.flush();
	}

	/**
	 * 	保存单条对象,立刻并写入数据库
	 * 	数据库主键设计为自增模式
	 */
	@Test
	public void testSaveAndFlush() {
		Teacher teacher = new Teacher(6,"Elor",new Date(),"安阳",21);
		teacherJpaRepository.saveAndFlush(teacher);			//保存并写入数据库
	}


	/**
	 * 	删除全部的方法
	 */
	@Test
	public void testDeleteAllInBatch() {
		teacherJpaRepository.deleteAll();
	}

	/**
	 * 	通过getOne()或者findOne()方法查询主键对应记录
	 * 	getOne()为懒加载方式
	 */
	@Test
	public void testGetOne() {
	//	Teacher teacher = teacherJpaRepository.getOne(1);	//懒加载,测试时异常
		Teacher teacher = teacherJpaRepository.findOne(1);
		System.out.println(teacher);
	}

}
