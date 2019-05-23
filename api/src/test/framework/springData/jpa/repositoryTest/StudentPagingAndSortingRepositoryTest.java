package springData.jpa.repositoryTest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import springData.jpa.bean.Student;
import springData.jpa.repository.StudentPagingAndSortingRepository;

import static org.junit.Assert.*;

/**
 * @author Jion
 */
@Slf4j
public class StudentPagingAndSortingRepositoryTest extends SpringDataJpaBaseTest {

    @Autowired
    private StudentPagingAndSortingRepository studentPagingAndSortingRepository;

    /**
     * 	测试排序逻辑
     */
    @Test
    public void testFindAllSort() {

        //匿名内部类,完成根据id降序排列
        Sort.Order ordersId = new Sort.Order(Sort.Direction.DESC,"id");
        // age升序
        Sort.Order ordersAge = new Sort.Order(Sort.Direction.ASC,"age");
        // 多个排序规则
        Sort sort = Sort.by(ordersId, ordersAge);

        //分页查询,第一页,显示三个,并传入排序规则
        Pageable pageable = PageRequest.of(2, 2,sort);
        Page<Student> page =  studentPagingAndSortingRepository.findAll(pageable);
        assertNotNull(page);
        log.info("总页数:" + page.getTotalPages());
        log.info("总记录数:" + page.getTotalElements());
        log.info("当前页面总记录" + page.getSize());
        log.info("当前页面是否全部查询" + page.hasContent());
        log.info("当前页数:" + page.getNumber());					//从0开始
        log.info("当前页面记录数:" + page.getNumberOfElements());
        log.info("当前页面的集合:" + page.getContent());
        //当前页
        page.getPageable();
        // 下一页
        if(page.hasNext()){
            page.nextPageable();
        }
        // 上一页
        if(page.hasPrevious()){
            page.previousPageable();
        }
        log.info("是否第一页:" + page.isFirst());
        log.info("是否最后一页:" + page.isLast());
        log.info("是否空页:" + page.isEmpty());
    }

    /**
     * 	测试分页逻辑
     */
    @Test
    public void testFindAllPageable() {
        //分页查询,第一页,显示三个
        Pageable pageable = PageRequest.of(0, 2);
        Page<Student> page = studentPagingAndSortingRepository.findAll(pageable);
        assertNotNull(page);
        log.info("总页数:" + page.getTotalPages());
        log.info("总记录数:" + page.getTotalElements());
        log.info("当前页数:" + page.getNumber());					//从0开始
        log.info("当前页面记录数:" + page.getNumberOfElements());
        log.info("当前页面的集合:" + page.getContent());
    }
}