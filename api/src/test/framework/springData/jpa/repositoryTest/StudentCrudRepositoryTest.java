package springData.jpa.repositoryTest;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import springData.jpa.bean.Student;
import springData.jpa.repository.StudentCrudRepository;

import static org.junit.Assert.*;

/**
 * @author Jion
 */
public class StudentCrudRepositoryTest extends SpringDataJpaBaseTest {


    @Autowired
    private StudentCrudRepository StudentCrudRepository;

    /**
     * 	查询当前总记录数
     */
    @Test
    public void testCount() {
        Long count = StudentCrudRepository.count();
        System.out.println("总记录数:" + count);
    }

    /**
     * 	删除当前数据表
     */
    @Test
    public void testDeleteAll() {
        StudentCrudRepository.deleteAll();
    }

    /**
     * 	保存实体
     */
    @Test
    public void testSaveS() {
        Student Student = new Student();
        // ID通过数据库自增赋值
        Student.setId(null);
        Student.setName("Per");
        Student.setAge(20);
        Student.setAddress("开封");
        Student.setWorkday(new Date());
        StudentCrudRepository.save(Student);
    }

    /**
     * 	通过主键查询
     */
    @Test
    public void testFindOneInteger() {
        Student Student = StudentCrudRepository.findOne(1);
        System.out.println("查询第一个:" + Student);
    }

    /**
     * 	通过主键判断
     */
    @Test
    public void testExistsInteger() {
        Boolean exists = StudentCrudRepository.exists(1);
        System.out.println("第一个是否存在:" + exists);
    }

    /**
     * 	查询全部方法
     * 	输出一个实现了Iterable<T>接口的实体对象,这里直接调用其迭代器进行迭代输出
     */
    @Test
    public void testFindAll() {
        Iterator<Student> Students = StudentCrudRepository.findAll().iterator();
        while(Students.hasNext()) {
            System.out.println("迭代下一个:" + Students.next());
        }
    }


    /**
     * 	根据主键删除
     */
    @Test
    public void testDeleteInteger() {
        StudentCrudRepository.delete(7);
    }

    /**
     *	通过实体删除
     */
    @Test
    public void testDeleteStudent() {
        Student Student = StudentCrudRepository.findOne(8);
        StudentCrudRepository.delete(Student);
    }

    /**
     * 	通过迭代器删除全部
     */
    @Test
    public void testDeleteIterableOfQextendsStudent() {
        Iterable<Student> Students = StudentCrudRepository.findAll();
        StudentCrudRepository.delete(Students);
    }

}

}