package springData.jpa.repository;

import org.springframework.data.repository.Repository;
import springData.jpa.bean.Student;

import java.util.Date;
import java.util.List;

/**
 *	使用JPARepository访问数据库
 *	通过继承Repository接口或者使用@RepositoryDefinition
 *	标识该类使用Spring-Data
 *	通过方法签名,实现查询
 */
public interface StudentRepository extends Repository<Student,Integer> {
	
	/**
     *  根据姓名查询
	 *	select * from student where name = ?
	 */
	public Student findByName(String name);

	/**
     *  姓氏开头,且年龄小于
	 * 	select * from student where name like ?% and age < ?
	 */
	public List<Student> findByNameStartsWithAndAgeLessThan(String name, Integer age);
	
	/**
     *  名字结尾,年龄大于等于
	 * 	select * from student where name like ?%> and age >= ?
	 */
	public List<Student> findByNameEndingWithAndAgeGreaterThanEqual(String name, Integer age);

	/**
     *  在/不在某个枚举中
	 * 	select * from student where name in ( ? , ? ) or address not in (? , ?)
	 */
	public List<Student> findByNameInOrAddressNotIn(List<String> names, List<String> addresses);
	
	/**
     *  日期范围
	 * 	select * from student where workday between ? and ?
	 */
	public List<Student> findByBirthdayBetween(Date start, Date end);
	
	/**
     *  非空查询
	 * 	select * from student where id is not null and name is null
	 */
	public List<Student> findByIdNotNullAndNameIsNull();
}
