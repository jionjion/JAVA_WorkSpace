package top.jionjion.enums;

import lombok.Getter;

/**
 *  @author Jion
 *      商品状态的枚举类,只提供get方法
 */
@Getter
public enum ProductStatusEnum {

    /** 在热销 */
    UP(0 , "在热销"),
    /** 已下架 */
    DOWN(1 , "已下架")
    ;


    private Integer code;

    private String message;

    ProductStatusEnum(Integer code , String message){
        this.code = code;
        this.message = message;
    }
}
