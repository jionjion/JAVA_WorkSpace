package springData.JPARepository;

import static org.junit.Assert.fail;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import base.UnitTestBase;
import springData.bean.Teacher;

public class TeacherJpaSpecificationExecutorRepositoryTest extends UnitTestBase{

	private TeacherJpaSpecificationExecutorRepository teacherJpaSpecificationExecutorRepository;
	
	public TeacherJpaSpecificationExecutorRepositoryTest() {
		super("classpath:spring-data.xml");
	}	
	
	/**
	 * 	在测试方法执行前,初始化
	 */
	@Before
	public void init() {
		//避免重复创建加载
		if (this.teacherJpaSpecificationExecutorRepository == null) {
			this.teacherJpaSpecificationExecutorRepository = super.getBean(TeacherJpaSpecificationExecutorRepository.class);
		}
	}	
	
	@Test
	public void testFindOne() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllSpecificationOfT() {
		
		//匿名内部类,完成根据id降序排列
		Sort.Order orders = new Sort.Order(Sort.Direction.DESC,"id");
		Sort sort = new Sort(orders);
		
		//分页查询,第一页,显示三个
		Pageable pageable = new PageRequest(0, 3,sort);
		
		//排序规则
		Specification<Teacher> specification = new Specification<Teacher>() {

			@Override
			public Predicate toPredicate(Root<Teacher> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<Integer> path = root.get("age");	//泛型指定为字段类型
				return cb.gt(path, 20);				//年龄大于23
			}
		};
		
		
		Page<Teacher> page = teacherJpaSpecificationExecutorRepository.findAll(specification,pageable);
		System.out.println("总页数:" + page.getTotalPages());
		System.out.println("总记录数:" + page.getTotalElements());
		System.out.println("当前页数:" + page.getNumber());					//从0开始
		System.out.println("当前页面记录数:" + page.getNumberOfElements());
		System.out.println("当前页面的集合:" + page.getContent());	
	}

	@Test
	public void testFindAllSpecificationOfTPageable() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAllSpecificationOfTSort() {
		fail("Not yet implemented");
	}

	@Test
	public void testCount() {
		fail("Not yet implemented");
	}

}
