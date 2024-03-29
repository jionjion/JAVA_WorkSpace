package springData.jpa.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import springData.jpa.bean.Student;

/**
 *	使用JPARepository访问数据库
 *	通过继承Repository接口或者使用@RepositoryDefinition 
 *	标识该类使用Spring-Data
 *	通过使用@Query注解,重写查询语句
 */

//@RepositoryDefinition(domainClass = Student.class, idClass = Integer.class)
public interface StudentQueryRepository extends Repository<Student,Integer> {
	
	
				/* ******************************|
				 * 			HQL语句				|
				 * *****************************/
	
	/**
     *  查询最大值
	 * 	select t from student t where id = (select max(id) from student)
	 */
	@Query("select t from Student t where id = (select max(id) from Student)")
	public Student getStudentByMaxId();
	
	/**		
	 * 	select t from student t where t.name = ?1
	 */
	@Query("select t from Student t where t.name = ?1")
	public Student getStudentByParamName(String name);

	/**
	 * 	select t from student t where t.address = :address
	 */
	@Query("select t from Student t where t.address = :address")
	public Student getStudentByParamAddress(@Param("address") String address);

	/**
	 * update student t set t.age = :age where t.id = :id
	 * 添加@Modifying注解和事务开启,其中@Modifying必须和@Query配合使用
	 */
	@Modifying
	@Query("update Student t set t.age = :age where t.id = :id")
	public void updateSetName(@Param("id") Integer id, @Param("age") Integer age);


				/* *****************************|
				 * 			SQL语句				|
				 * *****************************/
	
	/**
	 * 	使用原生的SQL进行查询.需要开启nativeQuery注解属性
	 * 	select count(*) from student
	 */
	@Query(value = "select count(*) from student" , nativeQuery = true)
	public Long getCount();
}
