package top.jionjion.dto;

import lombok.Data;

/**
 *  @author Jion
 *      购物车对象
 */
@Data
public class CartDTO {

    /** 商品ID */
    private String productId;

    /** 数量 */
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public CartDTO() {
    }
}
