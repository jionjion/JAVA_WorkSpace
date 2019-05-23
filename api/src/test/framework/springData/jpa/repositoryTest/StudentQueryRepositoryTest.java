package springData.jpa.repositoryTest;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import springData.jpa.bean.Student;

/**
 *	自定义查询
 */
public class StudentQueryRepositoryTest extends SpringDataJpaBaseTest{
	
	
				/*******************************|
				 * 			HQL语句				|
				 *******************************/
	
	/**	查询最大值
	 * 	select t from Student t where id = (select max(id) from Student)
	 */
	public Student getStudentByMaxId();
	
	/**		
	 * 	select t from Student t where t.name = ?
	 */
	@Query("select t from Student t where t.name = ?1")
	public Student getStudentByParamName(String name);
	
	@Query("select t from Student t where t.address = :address")
	public Student getStudentByParamAddress(@Param("address") String address);
	
	
	/**	
	 * 	update Student t set t.age = :age where t.id = :id 
	 * 	添加@Modifying注解和事务开启
	 */
	@Modifying
	@Transactional
	@Query("update Student t set t.age = :age where t.id = :id")
	public void updateSetName(@Param("id") Integer id, @Param("age") Integer age);
	
	
				/*******************************|
				 * 			SQL语句				|
				 *******************************/	
	
	/**	使用原生的SQL进行查询.需要开启nativeQuery注解属性
	 * 	select count(*) from Student
	 */
	@Query(value = "select count(*) from Student" , nativeQuery = true)
	public Long getCount();
}
