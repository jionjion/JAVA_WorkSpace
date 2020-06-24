package top.jionjion.bean;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import top.jionjion.enums.OrderStatusEnum;
import top.jionjion.enums.PayStatusEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *  @author Jion
 *      订单主表的实体类
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    /** 订单主表ID */
    @Id
    private String orderId;

    /** 顾客姓名 */
    @Column
    private String buyerName;

    /** 顾客电话 */
    @Column
    private String buyerPhone;

    /** 顾客地址 */
    @Column
    private String buyerAddress;

    /** 顾客openid */
    @Column
    private String buyerOpenid;

    /** 订单总金额 */
    @Column
    private BigDecimal buyerAmount;

    /** 订单状态,默认为0新建 */
    @Column
    private Integer buyerStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态,默认为0未支付 */
    @Column
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /** 创建时间 */
    @Column
    private Date createTime;

    /** 更新时间 */
    @Column
    private Date updateTime;
}
