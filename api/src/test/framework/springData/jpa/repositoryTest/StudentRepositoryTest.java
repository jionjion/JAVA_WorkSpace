package springData.jpa.repositoryTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import springData.jpa.bean.Student;
import springData.jpa.repository.StudentRepository;

import java.util.*;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Jion
 */
@Slf4j
public class StudentRepositoryTest extends SpringDataJpaBaseTest {

    @Autowired
    private StudentRepository studentRepository;


    /**
     *  根据姓名查询
     *	select * from student where name = ?
     */
    @Test
    public void testFindByName(){
        Student student = studentRepository.findByName("Jion");
        assertNotNull(student);
        log.info(student.toString());
    }

    /**
     *  姓氏开头,且年龄小于
     * 	select * from student where name like ?% and age < ?
     */
    @Test
    public void testFindByNameStartsWithAndAgeLessThan(){
        String name = "J";
        Integer age = 25;
        List<Student> list = studentRepository.findByNameStartsWithAndAgeLessThan(name,age);
        assertNotEquals(0,list.size());
        log.info(list.toString());
    }

    /**
     *  名字结尾,年龄大于等于
     * 	select * from student where name like ?% > and age >= ?
     */
    @Test
    public void testFindByNameEndingWithAndAgeGreaterThanEqual(){
        String name = "e";
        Integer age = 19;
        List<Student> list = studentRepository.findByNameEndingWithAndAgeGreaterThanEqual(name,age);
        assertNotEquals(0,list.size());
        log.info(list.toString());
    };

    /**
     *  在/不在某个枚举中
     * 	select * from student where name in ( ? , ? ) or address not in (? , ?)
     */
    @Test
    public void testFindByNameInOrAddressNotIn(){
        List<String> names = new ArrayList<>();
        names.add("Jion");
        names.add("Arise");
        List<String> addresses = new ArrayList<>();
        addresses.add("北京");
        addresses.add("上海");
        List<Student> list = studentRepository.findByNameInOrAddressNotIn(names, addresses);
        assertNotNull(list);
        log.info(list.toString());
    }

    /**
     *  日期范围
     * 	select * from student where workday between ? and ?
     */
    @Test
    public void findByBirthdayBetween(){
        Date start = new GregorianCalendar(2019,12,31).getTime();
        Date end = new GregorianCalendar(2017,01,01).getTime();
        List<Student> list = studentRepository.findByBirthdayBetween(start, end);
    }

    /**
     *  非空查询
     * 	select * from student where id is not null and name is null
     */
    @Test
    public void findByIdNotNullAndNameIsNull(){
        List<Student> list = studentRepository.findByIdNotNullAndNameIsNull();
    }
}