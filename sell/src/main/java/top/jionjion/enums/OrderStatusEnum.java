package top.jionjion.enums;

import lombok.Getter;

/**
 * @author Jion
 *      订单状态
 */
@Getter
public enum OrderStatusEnum {

    /** 新建订单 */
    NEW(0,"新订单"),
    /** 完结订单 */
    FINISHED(1,"完结"),
    /** 取消订单 */
    CANCEL(2,"取消")
    ;

    private Integer code;

    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
