package springData.JPARepository;

import org.springframework.data.repository.Repository;

import springData.bean.Teacher;

/**
 *	使用JPARepository访问数据库
 *	通过继承Repository接口或者使用@RepositoryDefinition 
 *	标识该类使用Spring-Data
 */

//@RepositoryDefinition(domainClass = Teacher.class, idClass = Integer.class)
public interface TeacherRepository extends Repository<Teacher,Integer>{
	
	//根据姓名查询
	public Teacher findByName(String name);
}
