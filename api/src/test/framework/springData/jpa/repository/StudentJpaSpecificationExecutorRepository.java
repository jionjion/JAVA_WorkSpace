package springData.jpa.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.RepositoryDefinition;
import springData.jpa.bean.Student;

/**
 *	使用JPARepository访问数据库
 *	通过继承PagingAndSortingRepository接口或者使用@RepositoryDefinition 
 *	标识该类使用Spring-Data
 */
@RepositoryDefinition(domainClass = Student.class, idClass = Integer.class)
public interface StudentJpaSpecificationExecutorRepository extends JpaSpecificationExecutor<Student>{

	
}
