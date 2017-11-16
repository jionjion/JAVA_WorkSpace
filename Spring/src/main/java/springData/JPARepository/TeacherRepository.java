package springData.JPARepository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import springData.bean.Teacher;

/**
 *	使用JPARepository访问数据库
 *	通过继承Repository接口或者使用@RepositoryDefinition 
 *	标识该类使用Spring-Data
 */

//@RepositoryDefinition(domainClass = Teacher.class, idClass = Integer.class)
public interface TeacherRepository extends Repository<Teacher,Integer>{
	
	/**	根据姓名查询
	 *	select * from teacher where name = ?
	 */
	public Teacher findByName(String name);
	
	/**	姓氏开头,且年龄小于
	 * 	select * from teacher where name like ?% and age < ?
	 */
	public List<Teacher> findByNameStartsWithAndAgeLessThan(String name,Integer age); 
	
	/**	名字结尾,年龄大于等于
	 * 	select * from teacher where name like %> and age >= ?
	 */
	public List<Teacher> findByNameEndingWithAndAgeGreaterThanEqual(String name,Integer age); 
	
	
	/** 在/不在某个枚举中
	 * 	select * from teacher where name in ( ? , ? ) or address not in (? , ?)  
	 */
	public List<Teacher> findByNameInOrAddressNotIn(List<String> names , List<String> addresses);
	
	
	/**	使用注解自定义HQL,这里使用默认的HQL查询语句
	 * 	select t from Teacher t where id = (select max(id) from Teacher)
	 */
	@Query("select t from Teacher t where id = (select max(id) from Teacher)")
	public Teacher getTeacherByMaxId();
	
	/**	使用注解自定义HQL,这里使用默认的HQL查询语句,并启用占位符,数字表示占位符的序号
	 * 	select t from Teacher t where t.name = ?
	 */
	@Query("select t from Teacher t where t.name = ?1")
	public Teacher getTeacherByParamName(String name);
	
	@Query("select t from Teacher t where t.address = :address")
	public Teacher getTeacherByParamAddress(@Param("address") String address);
	
	/**	使用原生的SQL进行查询.需要开启nativeQuery注解属性
	 * 	select count(*) from teacher
	 */
	@Query(value = "select count(*) from teacher" , nativeQuery = true)
	public Long getCount();
	
	/**	更新语句 HQL语句
	 * 	update Teacher t set t.age = :age where t.id = :id 
	 * 	添加@Modifying注解和事务开启
	 */
	@Modifying
	@Transactional
	@Query("update Teacher t set t.age = :age where t.id = :id")
	public void updateSetName(@Param("id") Integer id , @Param("age") Integer age);
}
