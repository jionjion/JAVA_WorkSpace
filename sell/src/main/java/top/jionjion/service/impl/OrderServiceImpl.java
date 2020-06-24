package top.jionjion.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import top.jionjion.bean.OrderDetail;
import top.jionjion.bean.OrderMaster;
import top.jionjion.bean.ProductInfo;
import top.jionjion.dto.CartDTO;
import top.jionjion.dto.OrderDTO;
import top.jionjion.enums.OrderStatusEnum;
import top.jionjion.enums.PayStatusEnum;
import top.jionjion.enums.ResultStatusEnum;
import top.jionjion.exception.SellException;
import top.jionjion.repository.OrderDetailRepository;
import top.jionjion.repository.OrderMasterRepository;
import top.jionjion.service.OrderService;
import top.jionjion.service.ProductInfoService;
import utils.KeyUtil;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Jion
 */
@Service
@Transactional(rollbackOn = RuntimeException.class)
public class OrderServiceImpl implements OrderService{

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    /** 订单ID */
    String orderId = KeyUtil.getUniqueKey();

    /** 总价 */
    BigDecimal orderAmount = BigDecimal.ZERO;

    @Override
    public OrderDTO create(OrderDTO orderDTO) {
        //1.查询商品(数量,价格)
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            //根据前台传入的商品ID去后台数据库查询
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if(Objects.isNull(productInfo)){
                //商品不存在异常
                throw new SellException(ResultStatusEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算总价  总价 = 商品价格 * 订单数量 + 总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
                                .add(orderAmount);
            //3.写入数据库,保存订单详情信息
            //订单其他信息,将查询到的商品属性拷贝到订单属性中
            BeanUtils.copyProperties(productInfo,orderDetail);
            //订单明细主键
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            //关联主表主键
            orderDetail.setOrderId(orderId);
            //保存订单明细表
            orderDetailRepository.save(orderDetail);
        }
        //保存订单主表
        OrderMaster orderMaster = new OrderMaster();
        //主键
        orderDTO.setOrderId(orderId);
        //总价
        orderDTO.setBuyerAmount(orderAmount);
        //状态
        orderDTO.setBuyerStatus(OrderStatusEnum.NEW.getCode());
        orderDTO.setPayStatus(PayStatusEnum.WAIT.getCode());
        //从前端传入的订单对象获取订单主表信息
        BeanUtils.copyProperties(orderDTO,orderMaster);
        //保存
        orderMasterRepository.save(orderMaster);

        //4.下单成功处理,扣库存  从订单中获得商品ID和数量,使用表达式生成购物车对象列表
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map( e ->
                new CartDTO(e.getProductId(),e.getProductQuantity())
        ).collect(Collectors.toList());
        //购物车中信息,作为库存对象
        productInfoService.decreaseStock(cartDTOList);
        //结束
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        return null;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        return null;
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}
