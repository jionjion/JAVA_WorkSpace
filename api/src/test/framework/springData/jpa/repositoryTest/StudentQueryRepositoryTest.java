package springData.jpa.repositoryTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import springData.jpa.bean.Student;
import springData.jpa.repository.StudentQueryRepository;

import static org.junit.Assert.assertNotNull;

/**
 *	自定义查询
 */
@Slf4j
@Rollback
public class StudentQueryRepositoryTest extends SpringDataJpaBaseTest{

	@Autowired
	StudentQueryRepository studentQueryRepository;


	/**
	 * 	select t from student t where id = (select max(id) from Student)
	 */
	@Test
	public void testGetStudentByMaxId() {
		Student result =  studentQueryRepository.getStudentByMaxId();
		assertNotNull(result);
		log.info(result.toString());
	}

	/**
	 * 	select t from student t where t.name = ?1
	 */
	@Test
	public void testGetStudentByParamName(){
        Student result = studentQueryRepository.getStudentByParamName("Jion");
        assertNotNull(result);
        log.info(result.toString());
    }

    /**
     * 	select t from student t where t.address = :address
     */
    @Test
	public void testGetStudentByParamAddress(){
        Student result = studentQueryRepository.getStudentByParamAddress("上海");
        assertNotNull(result);
        log.info(result.toString());
    }

	/**
	 * 	update student t set t.age = :age where t.id = :id
	 */
	@Test
	public void testUpdateSetName(){
	    studentQueryRepository.updateSetName(1,10);
	}

	/**
     *  使用原生的SQL进行查询.需要开启nativeQuery注解属性
	 * 	select count(*) from student
	 */
	@Test
	public void testGetCount(){
	    Long result = studentQueryRepository.getCount();
	    assertNotNull(result);
	    log.info(result.toString());
    }
}
