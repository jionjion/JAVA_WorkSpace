package top.jionjion.jpa.bean;

import lombok.Data;

/**
 *  数据源B-订单表
 * @author Jion
 */
@Data
public class Order {

    /** 主键 */
    private Long id;

    /** 顾客ID */   
    private Long customerId;

    /** 商品 */
    private String title;

    /** 价格 */
    private Integer amount;

}
