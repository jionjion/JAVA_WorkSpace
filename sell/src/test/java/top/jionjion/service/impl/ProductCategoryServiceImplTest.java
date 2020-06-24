package top.jionjion.service.impl;

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

import static org.junit.Assert.*;

/**
 *  @author Jion
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceImplTest {

    /** 接口实现 */
    @Autowired
    private ProductCategoryServiceImpl service;

    @Test
    public void findOne() throws Exception {
        ProductCategory productCategory = service.findOne(4);
        Assert.assertNotNull(productCategory);
    }

    @Test
    public void findAll() throws Exception {
        List<ProductCategory> productCategoryList = service.findAll();
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    public void findByCategoryTypeIn() throws Exception {
        List<Integer> categoryTypeList = new ArrayList<>();
        categoryTypeList.add(1);
        List<ProductCategory> productCategoryList = service.findByCategoryTypeIn(categoryTypeList);
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    @Transactional
    public void save() throws Exception {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryType(3);
        productCategory.setCategoryName("主食");
        productCategory = service.save(productCategory);
        Assert.assertNotNull(productCategory);
    }

}