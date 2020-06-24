package top.jionjion.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.jionjion.bean.OrderDetail;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

/**
 *  @author Jion
 *      订单详情的测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    /** 测试保存方法 */
    @Test
    @Transactional
    public void  saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1");
        orderDetail.setOrderId("123");
        orderDetail.setProductPrice(new BigDecimal(12.5));
        orderDetail.setProductId("1");
        orderDetail.setProductName("烤鸡腿");
        orderDetail.setProductQuantity(100);
        orderDetail.setProductIcon("A:x.jpg");
        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    /** 通过订单主表ID查询子表  */
    @Test
    public void findAllByOrderId() throws Exception {
        List<OrderDetail> orderDetailList = repository.findAllByOrderId("123");
        Assert.assertNotEquals(0,orderDetailList.size());
    }

    /** 通过主键查询 */
    @Test
    public void findByDetailId() throws Exception {
        OrderDetail orderDetail = repository.findByDetailId("1");
        Assert.assertNotNull(orderDetail);
    }
}