package springData.jpa.repositoryTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import springData.jpa.bean.Student;
import springData.jpa.repository.StudentCrudRepository;

import java.util.*;

import static org.junit.Assert.*;

/**
 * @author Jion
 */
@Slf4j
@Rollback
public class StudentCrudRepositoryTest extends SpringDataJpaBaseTest {


    @Autowired
    private StudentCrudRepository studentCrudRepository;

    /**
     * 	保存实体
     */
    @Test
    public void testSaveS() {
        Student Student = new Student();
        // ID通过数据库自增赋值
        Student.setId(0);
        Student.setName("Per");
        Student.setAge(20);
        Student.setAddress("开封");
        Student.setBirthday(new Date());
        Student result = studentCrudRepository.save(Student);
        assertNotNull(result);
        log.info(result.toString());
    }

    /***
     *  保存全部
     */
    @Test
    public void testSaveAll(){
        List<Student> list = new ArrayList<>();
        Student jion = new Student();
        jion.setName("Jion");
        jion.setAge(10);
        jion.setAddress("河南");
        jion.setBirthday(new GregorianCalendar(2010, Calendar.JULY,11).getTime());
        list.add(jion);
        Student arise = new Student();
        jion.setName("Jion");
        jion.setAge(11);
        jion.setAddress("上海");
        jion.setBirthday(new GregorianCalendar(2011,Calendar.SEPTEMBER,1).getTime());
        list.add(arise);
        List result = (List) studentCrudRepository.saveAll(list);
        assertNotEquals(0,result.size());
        log.info(result.toString());
    }

    /**
     *  通过ID主键查询
     */
    @Test
    public void testFindById(){
        Optional<Student> optional = studentCrudRepository.findById(1);
        Student result = null;
        // 存在赋值
        if(optional.isPresent()){
            result = optional.get();
        }
        assertNotNull(result);
        log.info(result.toString());
    }

    /**
     *  判断ID主键是否存在
     */
    @Test
    public void testExistsById(){
        Boolean result = studentCrudRepository.existsById(1);
        assertEquals(true,result);
        log.info(result.toString());
    }

    /**
     *  返回一个实现了Iterable<T>接口的实体对象
     */
    @Test
    public void testFindAll(){
        Iterable<Student> iterable = studentCrudRepository.findAll();
        Iterator<Student> iterator = iterable.iterator();
        assertTrue(iterator.hasNext());
        while (iterator.hasNext()){
            Student result = iterator.next();
            log.info(result.toString());
        }
    }

    /**
     *  通过主键集合查询全部
     */
    @Test
    public void testFindAllById(){
        Set<Integer> ids = new HashSet<>();
        ids.add(1);
        ids.add(2);
        Iterable<Student> iterable = studentCrudRepository.findAllById(ids);
        Iterator<Student> iterator = iterable.iterator();
        assertTrue(iterator.hasNext());
        // 迭代全部
        while (iterator.hasNext()){
            Student result = iterator.next();
            log.info(result.toString());
        }
    }

    /**
     *  查询总数
     */
    @Test
    public void testCount() {
        Long result = studentCrudRepository.count();
        assertNotEquals(new Long(0), result);
        log.info(result.toString());
    }

    /**
     *  根据ID删除
     */
    @Test
    public void testDeleteById(){
        studentCrudRepository.deleteById(1);
    }

    /**
     *  根据对象删除
     */
    @Test
    public void testDelete(){
        Optional<Student> optional = studentCrudRepository.findById(1);
        Student student = null;
        if(optional.isPresent()){
            student = optional.get();
        }
        studentCrudRepository.delete(student);
    }

    /**
     * 	删除当前数据表
     */
    @Test
    public void testDeleteAll() {
        studentCrudRepository.deleteAll();
    }

    /**
     *  删除指定集合
     */
    @Test
    public void testDeleteAllBySet(){
        Set<Student> set = new HashSet<>();
        Optional<Student> optional1 = studentCrudRepository.findById(1);
        Student student1 = null;
        if(optional1.isPresent()){
            student1 = optional1.get();
        }
        Optional<Student> optional2 = studentCrudRepository.findById(1);
        Student student2 = null;
        if(optional2.isPresent()){
            student2 = optional2.get();
        }
        set.add(student1);
        set.add(student2);
        studentCrudRepository.deleteAll(set);
    }
}