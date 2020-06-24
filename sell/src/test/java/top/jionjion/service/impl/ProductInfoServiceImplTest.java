package top.jionjion.service.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import top.jionjion.bean.ProductInfo;
import top.jionjion.enums.ProductStatusEnum;

import java.math.BigDecimal;
import java.util.List;


/**
 *  @author Jion
 *      测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl service;

    @Test
    public void findOne() throws Exception {
        ProductInfo productInfo = service.findOne("123");
        Assert.assertNotNull(productInfo);
    }

    @Test
    public void findUpAll() throws Exception {
        List<ProductInfo> productInfoList = service.findUpAll();
        Assert.assertNotEquals(1,productInfoList.size());
    }

    @Test
    public void findAll() throws Exception {
        //分页,第一页,每页五条
        Pageable pageable = PageRequest.of(0,5);
        Page<ProductInfo> page = service.findAll(pageable);
        Assert.assertNotEquals(0,page.getTotalElements());
    }

    @Test
    public void save() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("125");
        productInfo.setProductName("鸡腿");
        productInfo.setProductPrice(new BigDecimal(4.5));
        productInfo.setProductStock(50);
        productInfo.setProductStatus(0);
        productInfo.setProductDescription("这是一只烤鸡腿");
        productInfo.setProductIcon("/XXXXX.jpg");
        productInfo.setCategoryType(ProductStatusEnum.DOWN.getCode());
        productInfo = service.save(productInfo);
        Assert.assertNotNull(productInfo);
    }
}