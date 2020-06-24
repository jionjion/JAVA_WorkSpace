package top.jionjion.bean;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 *  @author Jion
 */
@Entity
@Data
public class ProductInfo {

    /** 主键 */
    @Id
    private String productId;

    /** 商品名称 */
    @Column
    private String productName;

    /** 单价 */
    @Column
    private BigDecimal productPrice;

    /** 库存 */
    @Column
    private Integer productStock;

    /** Ico图片 */
    private String productIcon;

    /** 商品状态 */
    private Integer productStatus;

    /** 描述 */
    @Column
    private String productDescription;

    /** 类目编号 */
    @Column
    private Integer categoryType;

    public ProductInfo() {
    }
}
