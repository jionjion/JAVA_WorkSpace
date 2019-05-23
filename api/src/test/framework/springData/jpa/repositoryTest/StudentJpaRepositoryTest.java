package springData.jpa.repositoryTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.annotation.Rollback;
import springData.jpa.bean.Student;
import springData.jpa.repository.StudentJpaRepository;

import java.util.*;

import static org.junit.Assert.assertNotNull;

/**
 * @author Jion
 */
@Rollback
@Slf4j
public class StudentJpaRepositoryTest extends SpringDataJpaBaseTest {

    @Autowired
    private StudentJpaRepository studentJpaRepository;

    /**
     * 查询全部
     */
    @Test
    public void testFindAll() {
        List<Student> result = studentJpaRepository.findAll();
        assertNotNull(result);
        log.info(result.toString());
    }

    /**
     * 查询全部
     * 排序规则:根据ID降序
     */
    @Test
    public void testFindAllSort() {
        Sort.Order orderId = new Sort.Order(Direction.DESC, "id");
        Sort sort = Sort.by(orderId);
        // 根据排序查询
        List<Student> result = studentJpaRepository.findAll(sort);
        assertNotNull(result);
        log.info(result.toString());
    }

    /**
     * 根据ID主键集合查询
     */
    @Test
    public void testFindAllById() {
        Set<Integer> ids = new HashSet<>();
        ids.add(1);
        ids.add(2);
        List<Student> result = studentJpaRepository.findAllById(ids);
        assertNotNull(result);
        log.info(result.toString());
    }

    /**
     * 保存Set中的全部
     */
    @Test
    public void testSaveAll() {
        Set<Student> students = new HashSet<>();
        Student jion = new Student();
        jion.setName("Jion");
        jion.setAge(10);
        jion.setAddress("河南");
        jion.setBirthday(new GregorianCalendar(2010, Calendar.JULY, 11).getTime());
        students.add(jion);
        List<Student> result = studentJpaRepository.saveAll(students);
        assertNotNull(result);
        log.info(result.toString());
    }

    /**
     * 提交事物,将所有的实体类变动提交数据库
     */
    @Test
    public void testFlush() {
        studentJpaRepository.flush();
    }

    /**
     * 保存单条对象,立刻并写入数据库
     */
    @Test
    public void testSaveAndFlush() {
        Student jion = new Student();
        jion.setName("Jion");
        jion.setAge(10);
        jion.setAddress("河南");
        jion.setBirthday(new GregorianCalendar(2010, Calendar.JULY, 11).getTime());
        //保存并写入数据库
        Student result = studentJpaRepository.saveAndFlush(jion);
        assertNotNull(result);
        log.info(result.toString());
    }

    /**
     * 批量删除,在一个SQL语句中完成
     */
    @Test
    public void testDeleteInBatch() {

        Set<Student> students = new HashSet<>();
        students.add(studentJpaRepository.getOne(1));
        students.add(studentJpaRepository.getOne(2));
        // 批量删除,在一个SQL语句中完成
        studentJpaRepository.deleteInBatch(students);
    }

    /**
     * 删除全部的方法
     */
    @Test
    public void testDeleteAllInBatch() {
        studentJpaRepository.deleteAll();
    }

    /**
     * 通过ID主键查询一个,为懒加载方式
     */
    @Test
    public void testGetOne() {
        Student result = studentJpaRepository.getOne(1);
        assertNotNull(result);
        log.info(result.toString());
    }
}