package top.jionjion.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import top.jionjion.bean.OrderDetail;
import top.jionjion.bean.OrderMaster;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.junit.Assert.*;

/**
 *  @author Jion
 * `    订单主表的测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    /** 测试保存方法 */
    @Test
    @Transactional
    public void save(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123");
        orderMaster.setBuyerName("Jion");
        orderMaster.setBuyerPhone("15516559772");
        orderMaster.setBuyerAddress("上海");
        orderMaster.setBuyerOpenid("155165");
        orderMaster.setBuyerAmount(new BigDecimal(12.5));
        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    /** 测试通过OpenId查询 */
    @Test
    public void findByBuyerOpenid() throws Exception {
        String openid = "155165";
        PageRequest pageRequest = PageRequest.of(0,5);
        Page<OrderMaster> page = repository.findByBuyerOpenid(openid,pageRequest);
        Assert.assertNotEquals(0,page.getTotalElements());
    }

    @Test
    public void findByOrderId() throws Exception {
        OrderMaster orderMaster = repository.findByOrderId("123");
        Assert.assertNotNull(orderMaster);
    }

}