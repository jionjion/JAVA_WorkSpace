package springData.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springData.jpa.bean.Student;

/**
 *	使用JPARepository访问数据库
 *	通过继承JpaRepository接口或者使用@RepositoryDefinition 
 *	标识该类使用Spring-Data
 */

public interface StudentJpaRepository extends JpaRepository<Student,Integer>{

	
}
