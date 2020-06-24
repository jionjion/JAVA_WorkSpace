package top.jionjion.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 *  @author Jion
 *      订单详情表
 */
@Entity
@Data
public class OrderDetail {

    /**主键*/
    @Id
    private String detailId;

    /** 主表主键 */
    @Column
    private String orderId;

    /** 商品主键 */
    @Column
    private String productId;

    /** 商品名称 */
    @Column
    private String productName;

    /** 商品数量 */
    @Column
    private Integer productQuantity;

    /** 商品价格 */
    @Column
    private BigDecimal productPrice;

    /** Ico图片 */
    @Column
    private String productIcon;
}
