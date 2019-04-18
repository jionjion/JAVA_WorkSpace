package top.jionjion.api.enums;

import lombok.Getter;

/**
 *  API调用结果枚举类
 *  @author Jion
 */
@Getter
public enum ApiResultStatus {

    /** 收到 */
    ACCEPT(100, "ACCEPT"),
    /** 成功 */
    SUCCESS(200, "SUCCESS"),
    /** 警告 */
    WARN(300, "WARNING"),
    /** 失败 */
    ERROR(400, "ERROR");

    /** 状态 */
    private Integer code;

    /** 错误信息 */
    private String status;

    ApiResultStatus(Integer code, String status){
        this.code = code;
        this.status = status;
    }

}