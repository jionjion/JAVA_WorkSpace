package top.jionjion.api.dto;

import lombok.Getter;
import lombok.Setter;
import top.jionjion.api.enums.ApiResultStatus;

import java.io.Serializable;

/**
 * @author Jion
 *  {
 *      date:[{
 *        A:"jion",
 *        B:"12",
 *        C:"1994-04-12"
 *      }],
 *      auth:{
 *          username:"",
 *          token:""
 *      }
 *      status:Success
 *      code:200
 *  }
 */
@Getter
public class ApiResultDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 数据 */
    private DataDto dataDto;

    /** 认证 */
    private AuthDto authDto;

    /** 数据状态 */
    private String status;

    /** 数据 */
    private Integer code;

    /** 返回信息 */
    @Setter
    private String message;

    /** 构造方法 */
    public ApiResultDto(DataDto dataDto, ApiResultStatus apiResultStatus, AuthDto authDto){
        this.dataDto = dataDto;
        this.authDto = authDto;
        this.status = apiResultStatus.getStatus();
        this.code = apiResultStatus.getCode();
    }

    /** 构造方法,根据Session中的信息进行验证 */
    public ApiResultDto(DataDto dataDto, ApiResultStatus apiResultStatus){
        this.dataDto = dataDto;
        this.status = apiResultStatus.getStatus();
        this.code = apiResultStatus.getCode();
        //@TODO 从Sesion中获取认证信息,并替换来宾账号
        this.authDto = AuthDto.getNoAuthUser();
    }

    /** 私有构造方法 */
    private ApiResultDto(){}
}
