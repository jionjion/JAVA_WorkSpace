package top.jionjion.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.jionjion.bean.ProductCategory;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 *  测试商品目录
 *  使用JPA规范实现
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    /** 保存方法 */
    @Test
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("折扣商品");
        productCategory.setCategoryType(1);
        repository.save(productCategory);
        System.out.println(productCategory);
    }

    /**
     * 修改方法
     * 保持主键一致即可,修改指定属性
     * */
    @Test
    @Transactional //测试方法中添加,直接回滚数据
    public void updateTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("折扣打折啦");
        productCategory.setCategoryId(7);
        productCategory.setCategoryType(1);
        //保存方法
        repository.save(productCategory);
        System.out.println(productCategory);
    }

    /** 测试通过主键查询*/
    @Test
    public void  findByCategoryIdTest(){
        ProductCategory productCategory = repository.findByCategoryId(4);
        //断言不为空
        Assert.assertNotNull(productCategory);
    }

    /** 测试通过类型查询 */
    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> categoryTypeList = new ArrayList<Integer>();
        categoryTypeList.add(1);
        categoryTypeList.add(2);
        List<ProductCategory> resultList = repository.findByCategoryTypeIn(categoryTypeList);
        //断言,查询结果集长度不为0
        Assert.assertNotEquals(0,categoryTypeList.size());
        System.out.println(resultList);
    }


}