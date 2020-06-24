package top.jionjion.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.jionjion.bean.OrderDetail;
import top.jionjion.dto.OrderDTO;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 *  @author Jion
 *      测试订单服务
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    /** 访问ID */
    private final String OPEN_ID = "155165";

    /** 保存订单方法 */
    @Test
    @Transactional
    public void create() throws Exception {
        //模拟前端订单传入对象
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("Jion");
        orderDTO.setBuyerAddress("上海");
        orderDTO.setBuyerPhone("15516559770");
        orderDTO.setBuyerOpenid(OPEN_ID);
        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123");
        orderDetail.setProductQuantity(3);
        orderDetailList.add(orderDetail);
        orderDTO.setOrderDetailList(orderDetailList);
        //创建
        OrderDTO result = orderService.create(orderDTO);
        //日志输出内容
        log.info("创建订单:{}",orderDTO);

        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() throws Exception {
    }

    @Test
    public void findList() throws Exception {
    }

    @Test
    public void cancel() throws Exception {
    }

    @Test
    public void finish() throws Exception {
    }

    @Test
    public void paid() throws Exception {
    }

}