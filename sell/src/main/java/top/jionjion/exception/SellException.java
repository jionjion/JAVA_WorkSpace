package top.jionjion.exception;

import top.jionjion.enums.ResultStatusEnum;

/**
 *  @author Jion
 *      售卖异常
 */
public class SellException extends RuntimeException{

    /** 异常代码 */
    private Integer code;

    /** 异常的构造器 */
    public SellException(ResultStatusEnum resultStatusEnum){
        //获得返回结果的枚举信息,作为异常提示信息
        super(resultStatusEnum.getMgs());
        //异常Code
        this.code = resultStatusEnum.getCode();
    }
}
