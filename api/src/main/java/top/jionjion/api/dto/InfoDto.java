package top.jionjion.api.dto;

import com.fasterxml.jackson.annotation.JsonMerge;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import top.jionjion.api.enums.ApiResultStatus;

/**
 * @author Jion
 */
@JsonRootName("_info")
@Data
public class InfoDto {

    /*
    _info: {
        "status":"WARNING",
                "code":300,
                "message":null
    }
    */

    private String status;

    private Integer code;

    private String message;

    /** 传入调用状态 */
    public InfoDto(ApiResultStatus apiResultStatus, String message){
        this.status = apiResultStatus.getStatus();
        this.code = apiResultStatus.getCode();
        this.message = message;
    }

    /** 返回一个接受类型的响应 */
    public static InfoDto getAcceptInfoDto(String message){

        return new InfoDto(ApiResultStatus.ACCEPT, message);
    }

    /** 返回一个成功类型的响应 */
    public static InfoDto getSuccessInfoDto(String message){

        return new InfoDto(ApiResultStatus.SUCCESS, message);
    }

    /** 返回一个警告类型的响应 */
    public static InfoDto getWarnInfoDto(String message){

        return new InfoDto(ApiResultStatus.WARN, message);
    }

    /** 返回一个失败类型的响应 */
    public static InfoDto getErrorInfoDto(String message){

        return new InfoDto(ApiResultStatus.ERROR, message);
    }
}
