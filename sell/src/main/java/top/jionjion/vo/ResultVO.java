package top.jionjion.vo;

import lombok.Data;

/**
 *  @author Jion
 *      请求响应
 */
@Data
public class ResultVO<T> {
    /** 返回代码 */
    private Integer code;

    /** 提示信息 */
    private String msg;

    /** 数据内容 */
    private T data;

}
