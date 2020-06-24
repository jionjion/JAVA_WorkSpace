package top.jionjion.enums;

import lombok.Getter;

/**
 *  @author Jion
 *      接口返回状态,各种异常及说明
 */
@Getter
public enum ResultStatusEnum {

    /** 成功状态 */
    SUCCESS(0,"成功"),
    /** 失败状态 */
    ERROR(1,"失败"),
    /** 商品不存在 */
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    /** 库存不足 */
    PRODUCT_STOCK_ERROR(11,"商品库存不正确")
    ;

    /** 状态Code */
    private Integer code;

    /** 状态信息 */
    private String mgs;

    ResultStatusEnum(Integer code, String mgs) {
        this.code = code;
        this.mgs = mgs;
    }
}
