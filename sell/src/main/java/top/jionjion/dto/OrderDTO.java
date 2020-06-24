package top.jionjion.dto;

import lombok.Data;
import top.jionjion.bean.OrderDetail;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *  @author Jion
 *      数据传输对象,便于在各个层次间进行传递
 */
@Data
public class OrderDTO {

    /** 订单主表ID */
    private String orderId;

    /** 顾客姓名 */
    private String buyerName;

    /** 顾客电话 */
    private String buyerPhone;

    /** 顾客地址 */
    private String buyerAddress;

    /** 顾客openid */
    private String buyerOpenid;

    /** 订单总金额 */
    private BigDecimal buyerAmount;

    /** 支付状态,默认为0未支付 */
    private Integer payStatus;

    /** 订单状态,默认为0新建 */
    private Integer buyerStatus;

    /** 更新时间 */
    private Date updateTime;

    /** 创建时间 */
    private Date createTime;

    /** 关联订单详情表 */
    List<OrderDetail> orderDetailList;
}
