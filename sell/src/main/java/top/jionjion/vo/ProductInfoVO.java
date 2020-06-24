package top.jionjion.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 *  @author Jion
 *      商品详情的前端显示
 */
@Data
public class ProductInfoVO {

    /** 主键 */
    @JsonProperty("id")
    private String productId;

    /** 商品名称 */
    @JsonProperty("name")
    private String productName;

    /** 单价 */
    @JsonProperty("price")
    private BigDecimal productPrice;

    /** 描述 */
    @JsonProperty("description")
    private String productDescription;

    /** Ico图片 */
    @JsonProperty("icon")
    private String productIcon;
}
